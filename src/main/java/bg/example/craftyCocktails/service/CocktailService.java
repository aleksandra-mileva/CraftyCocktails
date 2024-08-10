package bg.example.craftyCocktails.service;

import bg.example.craftyCocktails.model.dto.AddCocktailDto;
import bg.example.craftyCocktails.model.dto.EditCocktailDto;
import bg.example.craftyCocktails.model.dto.SearchCocktailDto;
import bg.example.craftyCocktails.model.entity.CocktailEntity;
import bg.example.craftyCocktails.model.entity.PictureEntity;
import bg.example.craftyCocktails.model.entity.TypeEntity;
import bg.example.craftyCocktails.model.entity.UserEntity;
import bg.example.craftyCocktails.model.entity.enums.RoleNameEnum;
import bg.example.craftyCocktails.model.entity.enums.SpiritNameEnum;
import bg.example.craftyCocktails.model.user.CustomUserDetails;
import bg.example.craftyCocktails.model.view.CocktailDetailsViewModel;
import bg.example.craftyCocktails.model.view.CocktailViewModel;
import bg.example.craftyCocktails.repository.CocktailRepository;
import bg.example.craftyCocktails.repository.CocktailSpecification;
import bg.example.craftyCocktails.repository.UserRepository;
import bg.example.craftyCocktails.web.exception.ObjectNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CocktailService {

  private final CocktailRepository cocktailRepository;
  private final ModelMapper modelMapper;
  private final UserRepository userRepository;
  private final TypeService typeService;
  private final PictureService pictureService;

  public CocktailService(
      CocktailRepository cocktailRepository,
      ModelMapper modelMapper,
      UserRepository userRepository,
      TypeService typeService,
      PictureService pictureService
  ) {
    this.cocktailRepository = cocktailRepository;
    this.modelMapper = modelMapper;
    this.userRepository = userRepository;
    this.typeService = typeService;
    this.pictureService = pictureService;
  }

  @Transactional
  public Page<CocktailViewModel> findAllCocktailViewModels(Pageable pageable) {
    return this.cocktailRepository.
        findAll(pageable)
        .map(this::mapToCocktailViewModel);
  }

  @Transactional
  public Page<CocktailViewModel> findAllFilteredCocktailViewModels(SpiritNameEnum spirit,
      Pageable pageable) {
    return this.cocktailRepository.
        findAllBySpirit(spirit, pageable)
        .map(this::mapToCocktailViewModel);
  }

  public CocktailViewModel mapToCocktailViewModel(CocktailEntity cocktail) {
    CocktailViewModel cocktailViewModel = modelMapper.map(cocktail, CocktailViewModel.class);
    cocktailViewModel.setAuthor(
        cocktail.getAuthor().getFirstName() + " " + cocktail.getAuthor().getLastName());
    cocktailViewModel.setPictureUrl(
        !cocktail.getPictures().isEmpty()
            ? cocktail.getPictures()
            .stream()
            .sorted(Comparator.comparingLong(PictureEntity::getId))
            .collect(Collectors.toList())
            .get(0)
            .getUrl()
            : "/static/images/register.jpg");
    return cocktailViewModel;
  }

  @Transactional
  public Long addCocktail(AddCocktailDto addCocktailDto, CustomUserDetails userDetails) {

    CocktailEntity newCocktail = modelMapper.map(addCocktailDto, CocktailEntity.class);

    newCocktail.setAuthor(userRepository.findById(userDetails.getId()).orElseThrow());
    newCocktail.setTypes(addCocktailDto.getTypes()
        .stream()
        .map(typeService::findByTypeName)
        .collect(Collectors.toList()));

    newCocktail.setPictures(new ArrayList<>());
    newCocktail = this.cocktailRepository.save(newCocktail);
    for (MultipartFile file : addCocktailDto.getPictureFiles()) {
      PictureEntity picture = pictureService.createAndSavePictureEntity(userDetails.getId(), file,
          newCocktail.getId());
      newCocktail.getPictures().add(picture);
    }
    return cocktailRepository.save(newCocktail).getId();
  }

  @Transactional
  public CocktailDetailsViewModel findCocktailDetailsViewModelById(Long id, String principalName) {

    CocktailEntity cocktailEntity = this.cocktailRepository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("Cocktail with ID " + id + " not found!"));

    CocktailDetailsViewModel cocktailDetailsViewModel = modelMapper
        .map(cocktailEntity, CocktailDetailsViewModel.class);

    cocktailDetailsViewModel.setPictures(cocktailEntity.getPictures()
        .stream()
        .map(p -> pictureService.mapToPictureViewModel(p, principalName))
        .collect(Collectors.toList()));

    cocktailDetailsViewModel.setIngredients(
        Arrays.stream(cocktailEntity.getIngredients().split("[\r\n]+"))
            .collect(Collectors.toList()));
    cocktailDetailsViewModel.setAuthor(
        cocktailEntity.getAuthor().getFirstName() + " " + cocktailEntity.getAuthor().getLastName());
    cocktailDetailsViewModel.setCanDelete(isOwner(principalName, id));
    cocktailDetailsViewModel.setIsFavorite(
        isCocktailFavorite(principalName, cocktailEntity.getId()));
    cocktailDetailsViewModel.setVideoId(extractVideoId(cocktailEntity.getVideoUrl()));

    return cocktailDetailsViewModel;
  }

  public boolean isOwner(String userName, Long cocktailId) {
    boolean isOwner = cocktailRepository.
        findById(cocktailId).
        filter(r -> r.getAuthor().getUsername().equals(userName)).
        isPresent();

    if (isOwner) {
      return true;
    }

    return userRepository
        .findByUsername(userName)
        .filter(this::isAdmin)
        .isPresent();
  }

  private boolean isAdmin(UserEntity user) {
    return user.getRoles().
        stream().
        anyMatch(r -> r.getRole() == RoleNameEnum.ADMIN);
  }

  public boolean isCocktailFavorite(String username, Long cocktailId) {
    UserEntity user = this.userRepository.findByUsername(username).orElse(null);

    if (user == null) {
      return false;
    }

    return user.getFavorites().stream().map(CocktailEntity::getId)
        .anyMatch(id -> id.equals(cocktailId));
  }

  @Transactional
  public EditCocktailDto getCocktailEditDetails(Long cocktailId) {

    CocktailEntity cocktailEntity = cocktailRepository.findById(cocktailId)
        .orElseThrow(
            () -> new ObjectNotFoundException("Cocktail with ID " + cocktailId + " not found"));

    EditCocktailDto editCocktailDto = modelMapper.map(cocktailEntity, EditCocktailDto.class);
    editCocktailDto.setTypes(cocktailEntity.getTypes()
        .stream()
        .map(TypeEntity::getName)
        .collect(Collectors.toList()));

    return editCocktailDto;
  }


  public void updateCocktailById(EditCocktailDto editCocktailDto, Long id,
      UserDetails userDetails) {
    CocktailEntity updateCocktail = this.cocktailRepository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("Cocktail with id: " + id + " not found!"));

    updateCocktail.setAuthor(userRepository.findByUsername(userDetails.getUsername())
        .orElseThrow(() -> new ObjectNotFoundException(
            "User with username " + userDetails.getUsername() + " not found!")));

    updateCocktail.setTypes(editCocktailDto.getTypes()
        .stream()
        .map(typeService::findByTypeName)
        .collect(Collectors.toList()));

    updateCocktail.setName(editCocktailDto.getName())
        .setFlavour(editCocktailDto.getFlavour())
        .setSpirit(editCocktailDto.getSpirit())
        .setServings(editCocktailDto.getServings())
        .setPercentAlcohol(editCocktailDto.getPercentAlcohol())
        .setPreparation(editCocktailDto.getPreparation())
        .setVideoUrl(editCocktailDto.getVideoUrl())
        .setIngredients(editCocktailDto.getIngredients());

    cocktailRepository.save(updateCocktail);
  }

  public static String extractVideoId(String videoUrl) {
    String pattern = "(?<=v=|\\/videos\\/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|%2Fvideos%2F|%2Fvi%2F|v=|%2Fv%2F)([a-zA-Z0-9_-]{11})";
    Pattern compiledPattern = Pattern.compile(pattern);
    Matcher matcher = compiledPattern.matcher(videoUrl);

    if (matcher.find()) {
      return matcher.group();
    }

    return null;
  }

  @Transactional
  public void deleteCocktailById(Long cocktailId) {
    CocktailEntity cocktail = cocktailRepository.findById(cocktailId)
        .orElseThrow(
            () -> new ObjectNotFoundException("Cocktail with ID " + cocktailId + " not found!"));

    cocktail.getPictures().forEach(picture -> pictureService.deletePicture(picture.getId()));
    cocktail.getFavoriteUsers().forEach(user -> {
      user.getFavorites().remove(cocktail);
      userRepository.save(user);
    });
    cocktailRepository.deleteById(cocktailId);
  }

  @Transactional
  public Page<CocktailViewModel> searchCocktail(
      SearchCocktailDto searchCocktailDto,
      Pageable pageable
  ) {
    return this.cocktailRepository
        .findAll(new CocktailSpecification(searchCocktailDto), pageable)
        .map(this::mapToCocktailViewModel);
  }

  @Transactional
  public Page<CocktailViewModel> findAllCocktailsUploadedByUserId(Long id, Pageable pageable) {
    return this.cocktailRepository.
        findAllByAuthor_Id(id, pageable)
        .map(this::mapToCocktailViewModel);
  }

  @Transactional
  public Page<CocktailViewModel> findAllFavoriteCocktailsForUserId(Long userId, Pageable pageable) {
    return this.cocktailRepository.
        findAllFavoriteCocktails(userId, pageable)
        .map(this::mapToCocktailViewModel);
  }

  public long findCountBySpirit(SpiritNameEnum spiritNameEnum) {
    return this.cocktailRepository.countCocktailEntitiesBySpirit(spiritNameEnum);
  }

  public long findCountAll() {
    return this.cocktailRepository.count();
  }
}
