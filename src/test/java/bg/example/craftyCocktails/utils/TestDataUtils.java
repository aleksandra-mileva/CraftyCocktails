package bg.example.craftyCocktails.utils;

import bg.example.craftyCocktails.model.entity.CocktailEntity;
import bg.example.craftyCocktails.model.entity.PictureEntity;
import bg.example.craftyCocktails.model.entity.RoleEntity;
import bg.example.craftyCocktails.model.entity.TypeEntity;
import bg.example.craftyCocktails.model.entity.UserEntity;
import bg.example.craftyCocktails.model.entity.enums.FlavourEnum;
import bg.example.craftyCocktails.model.entity.enums.RoleNameEnum;
import bg.example.craftyCocktails.model.entity.enums.SpiritNameEnum;
import bg.example.craftyCocktails.model.entity.enums.TypeNameEnum;
import bg.example.craftyCocktails.repository.CocktailRepository;
import bg.example.craftyCocktails.repository.PictureRepository;
import bg.example.craftyCocktails.repository.RoleRepository;
import bg.example.craftyCocktails.repository.TypeRepository;
import bg.example.craftyCocktails.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TestDataUtils {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final CocktailRepository cocktailRepository;
  private final TypeRepository typeRepository;
  private final PictureRepository pictureRepository;

  public TestDataUtils(
      UserRepository userRepository,
      RoleRepository roleRepository,
      CocktailRepository cocktailRepository,
      TypeRepository typeRepository,
      PictureRepository pictureRepository
  ) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.cocktailRepository = cocktailRepository;
    this.typeRepository = typeRepository;
    this.pictureRepository = pictureRepository;
  }

  private void initRoles() {
    if (roleRepository.count() == 0) {
      RoleEntity adminRole = new RoleEntity().setRole(RoleNameEnum.ADMIN);
      RoleEntity userRole = new RoleEntity().setRole(RoleNameEnum.USER);

      roleRepository.save(adminRole);
      roleRepository.save(userRole);
    }
  }

  public UserEntity createTestAdmin(String username) {
    initRoles();

    UserEntity admin = new UserEntity().
        setUsername(username).
        setEmail("admin@example.com").
        setFirstName("Admin").
        setLastName("Adminov").
        setRoles(roleRepository.findAll())
        .setPassword("12345");

    return userRepository.save(admin);
  }

  public UserEntity createTestUser(String username) {
    initRoles();

    UserEntity user = new UserEntity().
        setUsername(username).
        setEmail("user@example.com").
        setFirstName("User").
        setLastName("Userov").
        setRoles(roleRepository.
            findAll().stream().
            filter(r -> r.getRole() != RoleNameEnum.ADMIN).
            toList())
        .setPassword("12345");

    return userRepository.save(user);
  }

  public CocktailEntity createTestColcktail(UserEntity author, List<TypeEntity> types) {
    var testCocktail = new CocktailEntity()
        .setName("Mohito")
        .setIngredients("Gin 30ml.")
        .setPreparation("Shake until ready")
        .setFlavour(FlavourEnum.SWEET)
        .setAuthor(author)
        .setSpirit(SpiritNameEnum.GIN)
        .setTypes(types)
        .setPercentAlcohol(30)
        .setServings(4)
        .setVideoUrl("http//videoUrl")
        .setPictures(new ArrayList<>());

    testCocktail = cocktailRepository.save(testCocktail);
    testCocktail.getPictures().add(createTestPicture(author, testCocktail));
    return cocktailRepository.save(testCocktail);
  }

  public List<TypeEntity> createTestTypes() {
    var typeEntityFirst = new TypeEntity().
        setName(TypeNameEnum.ALCOHOLIC);

    var typeEntitySecond = new TypeEntity().
        setName(TypeNameEnum.NON_ALCOHOLIC);

    List<TypeEntity> types = new ArrayList<>();

    types.add(typeRepository.save(typeEntityFirst));
    types.add(typeRepository.save(typeEntitySecond));

    return types;
  }

  public PictureEntity createTestPicture(UserEntity author, CocktailEntity cocktail) {
    var pictureEntity = new PictureEntity().
        setAuthor(author).
        setCocktail(cocktail).
        setPublicId("testPublicId").
        setUrl("testUrl").
        setTitle("testTitle");

    return pictureRepository.save(pictureEntity);
  }

  public void cleanUpDatabase() {
    pictureRepository.deleteAll();
    cocktailRepository.deleteAll();
    userRepository.deleteAll();
    roleRepository.deleteAll();
    typeRepository.deleteAll();
  }

  public CocktailRepository getCocktailRepository() {
    return cocktailRepository;
  }

  public UserRepository getUserRepository() {
    return userRepository;
  }

  public PictureRepository getPictureRepository() {
    return pictureRepository;
  }
}
