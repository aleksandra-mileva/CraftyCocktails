package bg.example.craftyCocktails.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import bg.example.craftyCocktails.repository.UserRepository;
import bg.example.craftyCocktails.service.CocktailService;
import bg.example.craftyCocktails.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class StatisticsControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private CocktailService cocktailService;
  @Autowired
  private UserService userService;
  @Autowired
  private UserRepository userRepository;

  @Test
  @WithUserDetails(value = "admin", userDetailsServiceBeanName = "testUserDataService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
  public void testStatisticsEndpoint() throws Exception {
    mockMvc.perform(get("/statistics"))
        .andExpect(status().isOk())
        .andExpect(view().name("statistics"))
        .andExpect(model().attributeExists("statistics"))
        .andExpect(model().attribute("statistics",
            org.hamcrest.Matchers.hasProperty("allCocktails", org.hamcrest.Matchers.is(0L))))
        .andExpect(model().attribute("statistics",
            org.hamcrest.Matchers.hasProperty("ginCocktails", org.hamcrest.Matchers.is(0L))))
        .andExpect(model().attribute("statistics",
            org.hamcrest.Matchers.hasProperty("tequilaCocktails", org.hamcrest.Matchers.is(0L))))
        .andExpect(model().attribute("statistics",
            org.hamcrest.Matchers.hasProperty("whiskeyCocktails", org.hamcrest.Matchers.is(0L))))
        .andExpect(model().attribute("statistics",
            org.hamcrest.Matchers.hasProperty("rumCocktails", org.hamcrest.Matchers.is(0L))))
        .andExpect(model().attribute("statistics",
            org.hamcrest.Matchers.hasProperty("vodkaCocktails", org.hamcrest.Matchers.is(0L))))
        .andExpect(model().attribute("statistics",
            org.hamcrest.Matchers.hasProperty("brandyCocktails", org.hamcrest.Matchers.is(0L))))
        .andExpect(model().attribute("statistics",
            org.hamcrest.Matchers.hasProperty("nonAlcoholicCocktails",
                org.hamcrest.Matchers.is(0L))))
        .andExpect(model().attribute("statistics",
            org.hamcrest.Matchers.hasProperty("usersCount", org.hamcrest.Matchers.is(0L))))
        .andExpect(model().attribute("statistics",
            org.hamcrest.Matchers.hasProperty("localDateTime",
                org.hamcrest.Matchers.notNullValue())));
  }

}