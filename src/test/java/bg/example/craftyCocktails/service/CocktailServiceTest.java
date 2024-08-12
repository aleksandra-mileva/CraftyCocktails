package bg.example.craftyCocktails.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import bg.example.craftyCocktails.model.dto.SearchCocktailDto;
import bg.example.craftyCocktails.model.entity.CocktailEntity;
import bg.example.craftyCocktails.model.entity.TypeEntity;
import bg.example.craftyCocktails.model.entity.UserEntity;
import bg.example.craftyCocktails.model.view.CocktailViewModel;
import bg.example.craftyCocktails.repository.CocktailRepository;
import bg.example.craftyCocktails.repository.UserRepository;
import bg.example.craftyCocktails.utils.TestHelper;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
class CocktailServiceTest {

  @Autowired
  private CocktailRepository cocktailRepository;
  @Autowired
  private ModelMapper modelMapper;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private TypeService typeService;
  @Autowired
  private PictureService pictureService;
  @Autowired
  private TestHelper testHelper;
  @Autowired
  private CocktailService toTest;

  private CocktailEntity cocktail;

  @BeforeEach
  void setUp() {
    UserEntity user = testHelper.createUser("user");
    List<TypeEntity> testTypes = testHelper.createTypes();

    cocktail = testHelper.createCocktail(user, testTypes);
  }

  @AfterEach
  void tearDown() {
    testHelper.cleanUpDatabase();
  }

  @Test
  @Transactional
  void searchCocktailFounded() {
    SearchCocktailDto searchCocktailDto = testHelper.createSearchCocktailDto("Moh");
    CocktailViewModel cocktailViewModel = getCocktailViewModel();

    List<CocktailViewModel> expected = List.of(cocktailViewModel);

    List<CocktailViewModel> actual = toTest.searchCocktail(searchCocktailDto,
        PageRequest.of(0, 4)).getContent();

    assertEquals(expected.size(), actual.size());
    assertSearchedEquals(expected.get(0), actual.get(0));
  }

  @Test
  @Transactional
  void searchCocktailNotFounded() {
    SearchCocktailDto searchCocktailDto = testHelper.createSearchCocktailDto("Manhattan");

    List<CocktailViewModel> actual = toTest.searchCocktail(searchCocktailDto,
        PageRequest.of(0, 4)).getContent();

    assertEquals(0, actual.size());
  }

  private CocktailViewModel getCocktailViewModel() {
    CocktailViewModel cocktailViewModel = new CocktailViewModel();
    cocktailViewModel.setId(cocktail.getId());
    cocktailViewModel.setName(cocktail.getName());
    cocktailViewModel.setFlavour(cocktail.getFlavour());
    cocktailViewModel.setSpirit(cocktail.getSpirit());
    cocktailViewModel.setAuthor(
        cocktail.getAuthor().getFirstName() + " " + cocktail.getAuthor().getLastName());
    cocktailViewModel.setPictureUrl(cocktail.getPictures().get(0).getUrl());
    cocktailViewModel.setPercentAlcohol(cocktail.getPercentAlcohol());
    cocktailViewModel.setServings(cocktail.getServings());
    return cocktailViewModel;
  }

  private void assertSearchedEquals(CocktailViewModel expected, CocktailViewModel actual) {
    assertEquals(expected.getId(), actual.getId());
    assertEquals(expected.getName(), actual.getName());
    assertEquals(expected.getFlavour(), actual.getFlavour());
    assertEquals(expected.getSpirit(), actual.getSpirit());
    assertEquals(expected.getAuthor(), actual.getAuthor());
    assertEquals(expected.getPictureUrl(), actual.getPictureUrl());
    assertEquals(expected.getPercentAlcohol(), actual.getPercentAlcohol());
    assertEquals(expected.getServings(), actual.getServings());
  }


}