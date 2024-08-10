package bg.example.craftyCocktails.service;

import bg.example.craftyCocktails.model.entity.PictureEntity;
import bg.example.craftyCocktails.model.entity.UserEntity;
import bg.example.craftyCocktails.model.entity.enums.RoleNameEnum;
import bg.example.craftyCocktails.model.entity.enums.TypeNameEnum;
import bg.example.craftyCocktails.model.view.PictureHomePageViewModel;
import bg.example.craftyCocktails.model.view.PictureViewModel;
import bg.example.craftyCocktails.repository.CocktailRepository;
import bg.example.craftyCocktails.repository.PictureRepository;
import bg.example.craftyCocktails.repository.TypeRepository;
import bg.example.craftyCocktails.repository.UserRepository;
import bg.example.craftyCocktails.web.exception.InvalidFileException;
import bg.example.craftyCocktails.web.exception.ObjectNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PictureService {

  private final PictureRepository pictureRepository;
  private final UserRepository userRepository;
  private final CocktailRepository cocktailRepository;
  private final CloudinaryService cloudinaryService;
  private final ModelMapper modelMapper;
  private final TypeRepository typeRepository;

  public PictureService(
      PictureRepository pictureRepository,
      UserRepository userRepository,
      CocktailRepository cocktailRepository,
      CloudinaryService cloudinaryService,
      ModelMapper modelMapper,
      TypeRepository typeRepository
  ) {
    this.pictureRepository = pictureRepository;
    this.userRepository = userRepository;
    this.cocktailRepository = cocktailRepository;
    this.cloudinaryService = cloudinaryService;
    this.modelMapper = modelMapper;
    this.typeRepository = typeRepository;
  }

  public Page<PictureViewModel> findAllPictureViewModelsByUsername(String principalName,
      Pageable pageable) {
    Page<PictureEntity> pictures = this.pictureRepository
        .findAllByAuthor_Username(principalName, pageable);

    return pictures.map(picture -> this.mapToPictureViewModel(picture, principalName));
  }

  public PictureEntity createAndSavePictureEntity(Long userId, MultipartFile file,
      Long cocktailId) {

    try {
      final CloudinaryImage upload = cloudinaryService
          .uploadImage(file);
      PictureEntity newPicture = new PictureEntity()
          .setAuthor(userRepository.findById(userId)
              .orElseThrow(
                  () -> new ObjectNotFoundException("User with id " + userId + " not found!")))
          .setUrl(upload.getUrl())
          .setPublicId(upload.getPublicId())
          .setTitle(file.getOriginalFilename())
          .setCocktail(cocktailRepository.findById(cocktailId).orElse(null));

      return pictureRepository.save(newPicture);
    } catch (RuntimeException | IOException e) {
      throw new InvalidFileException(
          "File with name " + file.getOriginalFilename() + " can not be uploaded.");
    }
  }

  @Transactional
  public void deletePicture(Long id) {
    Optional<PictureEntity> picture = pictureRepository.findById(id);

    if (picture.isEmpty()) {
      throw new ObjectNotFoundException("Picture with id " + id + " not found!");
    }

    String publicId = picture.get().getPublicId();
    if (publicId != null) {
      cloudinaryService.delete(publicId);
    }

    pictureRepository.deleteById(id);
  }


  public boolean isOwner(String userName, Long pictureId) {
    boolean isOwner = pictureRepository.
        findById(pictureId).
        filter(picture -> picture.getAuthor().getUsername().equals(userName)).
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

  public PictureViewModel mapToPictureViewModel(PictureEntity picture, String principalName) {
    PictureViewModel pictureViewModel = modelMapper.map(picture, PictureViewModel.class);
    pictureViewModel.setCocktailId(picture.getCocktail().getId())
        .setAuthorUsername(picture.getAuthor().getUsername())
        .setCanNotDelete(!isOwner(principalName, picture.getId()));
    return pictureViewModel;
  }

  public PictureHomePageViewModel mapToPictureHomePageViewModel(PictureEntity picture) {
    PictureHomePageViewModel pictureHomePageViewModel = modelMapper.map(picture,
        PictureHomePageViewModel.class);
    pictureHomePageViewModel.setCocktailId(picture.getCocktail().getId())
        .setAuthorFullName(
            picture.getAuthor().getFirstName() + " " + picture.getAuthor().getLastName());
    return pictureHomePageViewModel;
  }

  public List<PictureHomePageViewModel> getThreeRandomPicturesByCocktailType(
      TypeNameEnum typeNameEnum) {
    List<PictureEntity> allPictures = pictureRepository.findAllPicturesByCocktailType(
        this.typeRepository.findByName(typeNameEnum)
            .orElseThrow(() -> new ObjectNotFoundException(
                "TypeEntity with name " + typeNameEnum + " not found!")));

    List<PictureHomePageViewModel> resultPictures = new ArrayList<>();
    Set<Long> selectedCocktailIds = new HashSet<>();

    Collections.shuffle(allPictures);

    for (PictureEntity picture : allPictures) {
      if (!selectedCocktailIds.contains(picture.getCocktail().getId())) {
        resultPictures.add(this.mapToPictureHomePageViewModel(picture));
        selectedCocktailIds.add(picture.getCocktail().getId());
      }

      if (resultPictures.size() == 3) {
        break;
      }
    }
    return resultPictures;
  }
}




