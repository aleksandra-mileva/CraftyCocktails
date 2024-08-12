package bg.example.craftyCocktails.web;

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
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TestHelper testDataUtils;

  private UserEntity testUser, testAdmin;
  private CocktailEntity testUserCocktail;

  @MockBean
  private CloudinaryService mockCloudinaryService;

  @BeforeEach
  void setUp() {
    testUser = testDataUtils.createUser("user");
    List<TypeEntity> testTypes = testDataUtils.createTypes();
    testUserCocktail = testDataUtils.createCocktail(testUser, testTypes);
  }

  @AfterEach
  void tearDown() {
    testDataUtils.cleanUpDatabase();
  }

  @WithUserDetails(value = "user",
      userDetailsServiceBeanName = "testUserDataService")
  @Test
  void testViewProfileByOwnerUser_Success() throws Exception {
    mockMvc.perform(get("/users/profile/{id}", testUser.getId()).
            with(csrf())
        ).
        andExpect(status().isOk()).
        andExpect(view().name("profile"));
  }

  @Test
  void testViewProfileByAnonymousUser_Forbidden() throws Exception {
    mockMvc.perform(delete("/users/profile/{id}", testUser.getId()).
            with(csrf())
        ).
        andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("http://localhost/users/login"));
  }

  @WithUserDetails(value = "user",
      userDetailsServiceBeanName = "testUserDataService")
  @Test
  void testViewAddedCocktailsByOwnerUser_Success() throws Exception {
    mockMvc.perform(get("/users/profile/{id}/addedCocktails", testUser.getId()).
            with(csrf())
        ).
        andExpect(status().isOk()).
        andExpect(view().name("all-cocktails"));
  }

  @Test
  void testViewAddedCocktailsByByAnonymousUser_Forbidden() throws Exception {
    mockMvc.perform(get("/users/profile/{id}/addedCocktails", testUser.getId()).
            with(csrf())
        ).
        andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("http://localhost/users/login"));
  }

  @WithUserDetails(value = "user",
      userDetailsServiceBeanName = "testUserDataService")
  @Test
  void testViewFavouriteCocktailsByOwnerUser_Success() throws Exception {
    mockMvc.perform(get("/users/profile/{id}/favoriteCocktails", testUser.getId()).
            with(csrf())
        ).
        andExpect(status().isOk()).
        andExpect(view().name("all-cocktails"));
  }

  @Test
  void testViewFavoriteCocktailsByByAnonymousUser_Forbidden() throws Exception {
    mockMvc.perform(get("/users/profile/{id}/favoriteCocktails", testUser.getId()).
            with(csrf())
        ).
        andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("http://localhost/users/login"));
  }

  @WithUserDetails(value = "user",
      userDetailsServiceBeanName = "testUserDataService")
  @Test
  void testViewAddedPicturesByOwnerUser_Success() throws Exception {
    mockMvc.perform(get("/users/profile/{id}/addedPictures", testUser.getId()).
            with(csrf())
        ).
        andExpect(status().isOk()).
        andExpect(view().name("user-pictures"));
  }

  @Test
  void testViewAddedPictureByAnonymousUser_Forbidden() throws Exception {
    mockMvc.perform(get("/users/profile/{id}/addedPictures", testUser.getId()).
            with(csrf())
        ).
        andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("http://localhost/users/login"));
  }

  @WithUserDetails(value = "user",
      userDetailsServiceBeanName = "testUserDataService")
  @Test
  void testEditUserInformationByOwnerUser_Success() throws Exception {
    mockMvc.perform(get("/users/profile/{id}/editProfile", testUser.getId()).
            with(csrf())
        ).
        andExpect(status().isOk()).
        andExpect(view().name("profile-edit"));
  }

  @Test
  void testEditUserInformationByAnonymousUser_Forbidden() throws Exception {
    mockMvc.perform(get("/users/profile/{id}/editProfile", testUser.getId()).
            with(csrf())
        ).
        andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("http://localhost/users/login"));
  }

  @WithUserDetails(value = "user",
      userDetailsServiceBeanName = "testUserDataService")
  @Test
  void testUpdateUserInformationByOwner_Success() throws Exception {

    MvcResult result = mockMvc.perform(
            put("/users/profile/{id}/editProfile", testUser.getId())
                .param("firstName", "NewFirstName")
                .param("lastName", "NewLastName")
                .param("email", "newemail@user.com")
                .param("username", "newUsername")
                .with(csrf())
        )
        .andExpect(status().is3xxRedirection())
        .andReturn();

    String redirectedUrl = result.getResponse().getRedirectedUrl();
    Assertions.assertEquals(redirectedUrl, "/users/profile/" + testUser.getId());

    UserEntity updatedUser = testDataUtils.getUserRepository().findById(testUser.getId())
        .orElse(null);

    Assertions.assertNotNull(updatedUser);
    Assertions.assertEquals("NewFirstName", updatedUser.getFirstName());
    Assertions.assertEquals("NewLastName", updatedUser.getLastName());
    Assertions.assertEquals("newemail@user.com", updatedUser.getEmail());
    Assertions.assertEquals("newUsername", updatedUser.getUsername());
  }

  @WithUserDetails(value = "user",
      userDetailsServiceBeanName = "testUserDataService")
  @Test
  @Transactional
  void testDeletePictureByOwner() throws Exception {

    long countBefore = testDataUtils.getPictureRepository().count();

    mockMvc.perform(delete("/users/profile/{id}/deletePicture", testUser.getId()).
            param("pictureId", String.valueOf(testUserCocktail.getPictures().get(0).getId())).
            with(csrf())
        ).
        andExpect(status().is3xxRedirection()).
        andExpect(
            view().name("redirect:/users/profile/" + testUserCocktail.getId() + "/addedPictures"));

    long countAfter = testDataUtils.getPictureRepository().count();
    Assertions.assertEquals(countBefore - 1, countAfter);
  }

  @Test
  @Transactional
  void testDeletePictureByAnonymous_Forbidden() throws Exception {

    long countBefore = testDataUtils.getPictureRepository().count();

    mockMvc.perform(delete("/users/profile/{id}/deletePicture", testUser.getId()).
            param("pictureId", String.valueOf(testUserCocktail.getPictures().get(0).getId())).
            with(csrf())
        ).
        andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("http://localhost/users/login"));

    long countAfter = testDataUtils.getPictureRepository().count();
    Assertions.assertEquals(countBefore, countAfter);
  }
}