package bg.example.craftyCocktails.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import bg.example.craftyCocktails.model.entity.CocktailEntity;
import bg.example.craftyCocktails.model.entity.TypeEntity;
import bg.example.craftyCocktails.model.entity.UserEntity;
import bg.example.craftyCocktails.model.entity.enums.FlavourEnum;
import bg.example.craftyCocktails.model.entity.enums.SpiritNameEnum;
import bg.example.craftyCocktails.model.entity.enums.TypeNameEnum;
import bg.example.craftyCocktails.service.CloudinaryImage;
import bg.example.craftyCocktails.service.CloudinaryService;
import bg.example.craftyCocktails.utils.TestHelper;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CocktailControllerTest {


  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TestHelper testHelper;

  private UserEntity testUser, testAdmin;

  private CocktailEntity testUserCocktail, testAdminCocktail;

  @MockBean
  private CloudinaryService mockCloudinaryService;

  @BeforeEach
  void setUp() {
    testUser = testHelper.createUser("user");
    testAdmin = testHelper.createAdmin("admin");
    List<TypeEntity> testTypes = testHelper.createTypes();

    testUserCocktail = testHelper.createCocktail(testUser, testTypes);
    testAdminCocktail = testHelper.createCocktail(testAdmin, testTypes);
  }

  @AfterEach
  void tearDown() {
    testHelper.cleanUpDatabase();
  }

  @Test
  void testDeleteByAnonymousUserForbidden() throws Exception {
    mockMvc.perform(delete("/cocktails/delete/{id}", testUserCocktail.getId()).
            with(csrf())
        ).
        andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("http://localhost/users/login"));
  }

  @Test
  @WithMockUser(
      username = "admin",
      roles = {"ADMIN", "USER"})
  void testDeleteByAdmin() throws Exception {
    mockMvc.perform(delete("/cocktails/delete/{id}", testUserCocktail.getId()).
            with(csrf())
        ).
        andExpect(status().is3xxRedirection()).
        andExpect(view().name("redirect:/cocktails/all"));
  }

  @WithMockUser(
      username = "user",
      roles = "USER")
  @Test
  void testDeleteByOwner() throws Exception {
    mockMvc.perform(delete("/cocktails/delete/{id}", testUserCocktail.getId()).
            with(csrf())
        ).
        andExpect(status().is3xxRedirection()).
        andExpect(view().name("redirect:/cocktails/all"));
  }

  @WithMockUser(
      username = "user",
      roles = "USER")
  @Test
  public void testDeleteNotOwnedForbidden() throws Exception {
    mockMvc.perform(delete("/cocktails/delete/{id}", testAdminCocktail.getId()).
            with(csrf())
        ).
        andExpect(status().isForbidden());
  }

  @Test
  void testAddCocktailByAnonymousUserForbidden() throws Exception {
    mockMvc.perform(get("/cocktails/add").
            with(csrf())
        ).
        andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("http://localhost/users/login"));
  }

  @WithUserDetails(value = "user",
      userDetailsServiceBeanName = "testUserDataService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
  @Test
  void testAddCocktailByLoggedUserSuccess() throws Exception {

    when(mockCloudinaryService.uploadImage(any(MultipartFile.class)))
        .thenReturn(new CloudinaryImage().setUrl("mockUrl").setPublicId("mockPublicId"));

    long beforeCount = testHelper.getCocktailRepository().count();

    MockMultipartFile picture1 = new MockMultipartFile("pictureFiles", "file1.jpg", "image/jpeg",
        "fileContent1".getBytes());
    MockMultipartFile picture2 = new MockMultipartFile("pictureFiles", "file2.jpg", "image/jpeg",
        "fileContent2".getBytes());

    MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders.multipart("/cocktails/add")
                .file(picture1)
                .file(picture2)
                // Add other parameters
                .param("name", "Test cocktail")
                .param("ingredients", "Test ingredients")
                .param("preparation", "Test preparation")
                .param("flavour", "SWEET")
                .param("videoUrl", "video://test.mp4")
                .param("spirit", "GIN")
                .param("percentAlcohol", "30")
                .param("servings", "4")
                .param("types", "ALCOHOLIC", "NON_ALCOHOLIC")
                .with(csrf())
        )
        .andExpect(status().is3xxRedirection())
        .andReturn();

    long afterCount = testHelper.getCocktailRepository().count();
    Assertions.assertEquals(beforeCount + 1, afterCount);

    String redirectedUrl = result.getResponse().getRedirectedUrl();
    mockMvc.perform(get("http://localhost" + redirectedUrl))
        .andExpect(status().isOk());

    String cocktailId = redirectedUrl.split("/")[redirectedUrl.split("/").length - 1];
    Assertions.assertTrue(
        testHelper.getCocktailRepository().existsById(Long.parseLong(cocktailId)));

    mockMvc.perform(get("/cocktails/details/{cocktailId}", cocktailId))
        .andExpect(status().isOk());

    Assertions.assertEquals(redirectedUrl, "/cocktails/details/" + cocktailId);
  }

  @Test
  void testAddCocktailByLoggedUserFail() throws Exception {

    when(mockCloudinaryService.uploadImage(any(MultipartFile.class)))
        .thenReturn(new CloudinaryImage().setUrl("mockUrl").setPublicId("mockPublicId"));

    long beforeCount = testHelper.getCocktailRepository().count();

    MockMultipartFile picture1 = new MockMultipartFile("pictureFiles", "file1.jpg", "image/jpeg",
        "fileContent1".getBytes());
    MockMultipartFile picture2 = new MockMultipartFile("pictureFiles", "file2.jpg", "image/jpeg",
        "fileContent2".getBytes());

    MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders.multipart("/cocktails/add")
                .file(picture1)
                .file(picture2)
                // Add other parameters
                .param("name", " ")
                .param("ingredients", "Test ingredients")
                .param("preparation", "Test preparation")
                .param("flavour", "SWEET")
                .param("videoUrl", "video://test.mp4")
                .param("spirit", "GIN")
                .param("percentAlcohol", "30")
                .param("servings", "4")
                .param("types", "ALCOHOLIC", "NON_ALCOHOLIC")
                .with(csrf())
        )
        .andExpect(status().is3xxRedirection())
        .andReturn();

    long afterCount = testHelper.getCocktailRepository().count();
    Assertions.assertEquals(beforeCount, afterCount);

    String redirectedUrl = result.getResponse().getRedirectedUrl();
    Assertions.assertEquals(redirectedUrl, "/cocktails/add");
  }

  @Test
  void testUpdateByAnonymousUserForbidden() throws Exception {
    mockMvc.perform(get("/cocktails/edit/{id}", testUserCocktail.getId()).
            with(csrf())
        ).
        andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("http://localhost/users/login"));
  }

  @WithUserDetails(value = "admin",
      userDetailsServiceBeanName = "testUserDataService")
  @Test
  void testUpdateByAdmin() throws Exception {
    mockMvc.perform(get("/cocktails/edit/{id}", testUserCocktail.getId()).
            with(csrf())
        ).
        andExpect(status().isOk()).
        andExpect(view().name("cocktail-update"));
  }

  @WithUserDetails(value = "user",
      userDetailsServiceBeanName = "testUserDataService")
  @Test
  void testUpdateByOwner() throws Exception {
    mockMvc.perform(get("/cocktails/edit/{id}", testUserCocktail.getId()).
            with(csrf())
        ).
        andExpect(status().isOk()).
        andExpect(view().name("cocktail-update"));
  }

  @WithUserDetails(value = "user",
      userDetailsServiceBeanName = "testUserDataService")
  @Test
  @Transactional
  void testUpdateCocktailByOwnerSuccess() throws Exception {

    MvcResult result = mockMvc.perform(
            put("/cocktails/edit/{id}", testUserCocktail.getId())
                .param("name", "New cocktail name")
                .param("ingredients", "New ingredients")
                .param("preparation", "New preparation")
                .param("flavour", "SWEET")
                .param("videoUrl", "video://newVideo.mp4")
                .param("spirit", "GIN")
                .param("percentAlcohol", "15")
                .param("servings", "10")
                .param("types", "ALCOHOLIC")
                .with(csrf())
        )
        .andExpect(status().is3xxRedirection())
        .andReturn();

    String redirectedUrl = result.getResponse().getRedirectedUrl();
    Assertions.assertEquals(redirectedUrl, "/cocktails/details/" + testUserCocktail.getId());

    CocktailEntity updatedCocktail = testHelper.getCocktailRepository()
        .findById(testUserCocktail.getId()).orElse(null);
    Assertions.assertNotNull(updatedCocktail);
    Assertions.assertEquals("New cocktail name", updatedCocktail.getName());
    Assertions.assertEquals("New ingredients", updatedCocktail.getIngredients());
    Assertions.assertEquals("New preparation", updatedCocktail.getPreparation());
    Assertions.assertEquals(FlavourEnum.SWEET, updatedCocktail.getFlavour());
    Assertions.assertEquals("video://newVideo.mp4", updatedCocktail.getVideoUrl());
    Assertions.assertEquals(SpiritNameEnum.GIN, updatedCocktail.getSpirit());
    Assertions.assertEquals(15, updatedCocktail.getPercentAlcohol());
    Assertions.assertEquals(10, updatedCocktail.getServings());
    Assertions.assertEquals(1, updatedCocktail.getTypes().size());
    Assertions.assertEquals(TypeNameEnum.ALCOHOLIC, updatedCocktail.getTypes().get(0).getName());
  }

  @Test
  @Transactional
  void testUpdateCocktailByOwnerFail() throws Exception {

    MvcResult result = mockMvc.perform(
            put("/cocktails/edit/{id}", testUserCocktail.getId())
                .param("name", " ")
                .param("ingredients", "New ingredients")
                .param("preparation", "New preparation")
                .param("flavour", "SWEET")
                .param("videoUrl", "video://newVideo.mp4")
                .param("spirit", "GIN")
                .param("percentAlcohol", "15")
                .param("servings", "10")
                .param("types", "ALCOHOLIC")
                .with(csrf())
        )
        .andExpect(status().is3xxRedirection())
        .andReturn();

    String redirectedUrl = result.getResponse().getRedirectedUrl();
    Assertions.assertEquals(redirectedUrl, "/cocktails/edit/" + testUserCocktail.getId());

    CocktailEntity updatedCocktail = testHelper.getCocktailRepository()
        .findById(testUserCocktail.getId()).orElse(null);
    Assertions.assertNotNull(updatedCocktail);
    Assertions.assertEquals("Mohito", updatedCocktail.getName());
    Assertions.assertEquals("Gin 30ml.", updatedCocktail.getIngredients());
    Assertions.assertEquals("Shake until ready", updatedCocktail.getPreparation());
    Assertions.assertEquals(FlavourEnum.SWEET, updatedCocktail.getFlavour());
    Assertions.assertEquals("http//videoUrl", updatedCocktail.getVideoUrl());
    Assertions.assertEquals(SpiritNameEnum.GIN, updatedCocktail.getSpirit());
    Assertions.assertEquals(30, updatedCocktail.getPercentAlcohol());
    Assertions.assertEquals(4, updatedCocktail.getServings());
    Assertions.assertEquals(2, updatedCocktail.getTypes().size());
  }

  @Test
  void testViewAllCocktailsByAnonymousUserSuccess() throws Exception {
    mockMvc.perform(get("/cocktails/all").
            with(csrf())
        ).
        andExpect(status().isOk()).
        andExpect(view().name("all-cocktails"));
  }

  @Test
  void testViewRumCocktailsByAnonymousUserSuccess() throws Exception {
    mockMvc.perform(get("/cocktails/rum").
            with(csrf())
        ).
        andExpect(status().isOk()).
        andExpect(view().name("all-cocktails"));
  }

  @Test
  void testViewWhiskeyCocktailsByAnonymousUserSuccess() throws Exception {
    mockMvc.perform(get("/cocktails/whiskey").
            with(csrf())
        ).
        andExpect(status().isOk()).
        andExpect(view().name("all-cocktails"));
  }

  @Test
  void testViewGinCocktailsByAnonymousUserSuccess() throws Exception {
    mockMvc.perform(get("/cocktails/gin").
            with(csrf())
        ).
        andExpect(status().isOk()).
        andExpect(view().name("all-cocktails"));
  }

  @Test
  void testViewCocktailDetails() throws Exception {
    mockMvc.perform(get("/cocktails/details/{id}", testUserCocktail.getId()).
            with(csrf())
        ).
        andExpect(status().isOk()).
        andExpect(view().name("cocktail-details"));
  }
}