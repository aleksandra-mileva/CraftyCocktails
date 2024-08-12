package bg.example.craftyCocktails.web;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bg.example.craftyCocktails.model.dto.AddCommentDto;
import bg.example.craftyCocktails.model.entity.CocktailEntity;
import bg.example.craftyCocktails.model.entity.CommentEntity;
import bg.example.craftyCocktails.model.entity.RoleEntity;
import bg.example.craftyCocktails.model.entity.UserEntity;
import bg.example.craftyCocktails.model.entity.enums.FlavourEnum;
import bg.example.craftyCocktails.model.entity.enums.RoleNameEnum;
import bg.example.craftyCocktails.model.entity.enums.SpiritNameEnum;
import bg.example.craftyCocktails.repository.CocktailRepository;
import bg.example.craftyCocktails.repository.RoleRepository;
import bg.example.craftyCocktails.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.hamcrest.text.MatchesPattern;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class CommentRestControllerTest {

  private static final String COMMENT_1 = "Comment1Comment1Comment1Comment1";
  private static final String COMMENT_2 = "Comment2Comment2Comment2Comment2";
  private static final String COMMENT_3 = "Comment3Comment3Comment3Comment3";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private CocktailRepository cocktailRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private UserRepository userRepository;

  private UserEntity user;
  private UserEntity admin;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {

    RoleEntity userRole = new RoleEntity();
    userRole.setRole(RoleNameEnum.USER);
    roleRepository.save(userRole);

    RoleEntity adminRole = new RoleEntity();
    adminRole.setRole(RoleNameEnum.ADMIN);
    roleRepository.save(adminRole);

    user = new UserEntity();
    user.setPassword("12345");
    user.setUsername("alexim");
    user.setFirstName("Aleksandra");
    user.setLastName("Mileva");
    user.setEmail("alexim@gmail.com");
    user.setRoles(List.of(userRole));

    user = userRepository.save(user);

    admin = new UserEntity();
    admin.setPassword("12345");
    admin.setUsername("admin");
    admin.setFirstName("Admin");
    admin.setLastName("Adminov");
    admin.setEmail("admin@gmail.com");
    admin.setRoles(List.of(adminRole));

    admin = userRepository.save(admin);
  }

  @AfterEach
  void tearDown() {
    cocktailRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  void testGetComments() throws Exception {
    CocktailEntity testCocktail = initComments(initCocktail());

    mockMvc.perform(get("/api/" + testCocktail.getId() + "/comments"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$.[0].message", is(COMMENT_1)))
        .andExpect(jsonPath("$.[0].user", is("Aleksandra Mileva")))
        .andExpect(jsonPath("$.[1].message", is(COMMENT_2)))
        .andExpect(jsonPath("$.[1].user", is("Aleksandra Mileva")))
        .andExpect(jsonPath("$.[2].message", is(COMMENT_3)))
        .andExpect(jsonPath("$.[2].user", is("Admin Adminov")));
  }

  private CocktailEntity initCocktail() {
    CocktailEntity testCocktail = new CocktailEntity();
    testCocktail.setName("Testing cocktail")
        .setIngredients("Testing products")
        .setPreparation("Testing description")
        .setFlavour(FlavourEnum.SWEET)
        .setAuthor(userRepository.findByUsername("alexim").get())
        .setSpirit(SpiritNameEnum.VODKA)
        .setPercentAlcohol(30)
        .setServings(4);

    return testCocktail = cocktailRepository.save(testCocktail);
  }

  private CocktailEntity initComments(CocktailEntity testCocktail) {
    CommentEntity comment1 = new CommentEntity();
    comment1.setAuthor(user);
    comment1.setCreated(LocalDateTime.now());
    comment1.setText(COMMENT_1);
    comment1.setCocktail(testCocktail);

    CommentEntity comment2 = new CommentEntity();
    comment2.setAuthor(user);
    comment2.setCreated(LocalDateTime.now());
    comment2.setText(COMMENT_2);
    comment2.setCocktail(testCocktail);

    CommentEntity comment3 = new CommentEntity();
    comment3.setAuthor(admin);
    comment3.setCreated(LocalDateTime.now());
    comment3.setText(COMMENT_3);
    comment3.setCocktail(testCocktail);

    testCocktail.setComments(List.of(comment1, comment2, comment3));

    return cocktailRepository.save(testCocktail);
  }

  @Test
  @WithMockUser("alexim")
  void testCreateCommentsByLoggedUser() throws Exception {

    AddCommentDto testComment = new AddCommentDto();
    testComment.setMessage(COMMENT_1);

    CocktailEntity emptyCocktail = initCocktail();

    mockMvc.perform(post("/api/" + emptyCocktail.getId() + "/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(testComment))
            .accept(MediaType.APPLICATION_JSON)
            .with(csrf()))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(header().string("Location",
            MatchesPattern.matchesPattern("/api/" + emptyCocktail.getId() + "/comments/" + "\\d+")))
        .andExpect(jsonPath("$.message").value(is(COMMENT_1)))
        .andExpect(jsonPath("$.user", is("Aleksandra Mileva")));
  }

  @Test
  void testCreateCommentsByAnonymousUser() throws Exception {

    AddCommentDto testComment = new AddCommentDto();
    testComment.setMessage(COMMENT_1);

    CocktailEntity emptyCocktail = initCocktail();

    mockMvc.perform(post("/api/" + emptyCocktail.getId() + "/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(testComment))
            .accept(MediaType.APPLICATION_JSON)
            .with(csrf()))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithAnonymousUser
  void deleteCommentByAnonymousUser() throws Exception {

    CocktailEntity testCocktail = initComments(initCocktail());

    mockMvc.perform(MockMvcRequestBuilders.delete(
            "/api/" + testCocktail.getId() + "/comments/" + testCocktail.getComments().get(0).getId()))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser("alexim")
  void deleteCommentByAuthor() throws Exception {

    CocktailEntity testCocktail = initComments(initCocktail());

    mockMvc.perform(MockMvcRequestBuilders.delete(
                "/api/" + testCocktail.getId() + "/comments/" + testCocktail.getComments().get(0).getId())
            .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message", is(COMMENT_1)))
        .andExpect(jsonPath("$.user", is("Aleksandra Mileva")));
  }

  @Test
  @WithMockUser("alexim")
  void deleteCommentByNotAuthor() throws Exception {

    CocktailEntity testCocktail = initComments(initCocktail());

    mockMvc.perform(MockMvcRequestBuilders.delete(
            "/api/" + testCocktail.getId() + "/comments/" + testCocktail.getComments().get(0).getId()))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser("admin")
  void deleteCommentByAdmin() throws Exception {

    CocktailEntity testCocktail = initComments(initCocktail());

    mockMvc.perform(MockMvcRequestBuilders.delete(
                "/api/" + testCocktail.getId() + "/comments/" + testCocktail.getComments().get(0).getId())
            .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message", is(COMMENT_1)))
        .andExpect(jsonPath("$.user", is("Aleksandra Mileva")));
  }
}