package bg.example.craftyCocktails.web;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import bg.example.craftyCocktails.model.view.FortunelViewModel;
import bg.example.craftyCocktails.repository.UserRepository;
import bg.example.craftyCocktails.service.FortuneService;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class FortuneControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private FortuneService fortuneService;
  @Autowired
  private UserRepository userRepository;

  @Test
  @WithUserDetails(value = "admin", userDetailsServiceBeanName = "testUserDataService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
  public void testAllFortunes() throws Exception {
    FortunelViewModel fortune1 = new FortunelViewModel().setId(1L).setContent("Fortune 1")
        .setAuthor("Author 1");
    FortunelViewModel fortune2 = new FortunelViewModel().setId(2L).setContent("Fortune 2")
        .setAuthor("Author 2");

    when(fortuneService.getAllFortunes()).thenReturn(Arrays.asList(fortune1, fortune2));

    mockMvc.perform(MockMvcRequestBuilders.get("/fortunes"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().attributeExists("fortunes"))
        .andExpect(MockMvcResultMatchers.model().attribute("heading", "All fortunes (2)"))
        .andExpect(MockMvcResultMatchers.view().name("all-fortunes"));
  }

  @Test
  @WithUserDetails(value = "admin", userDetailsServiceBeanName = "testUserDataService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
  public void testGetRandomFortune() throws Exception {
    FortunelViewModel fortune = new FortunelViewModel().setId(1L).setContent("Random Fortune")
        .setAuthor("Random Author");

    when(fortuneService.getRandomFortune()).thenReturn(fortune);

    mockMvc.perform(MockMvcRequestBuilders.get("/fortunes/fortune"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().attributeExists("fortune"))
        .andExpect(MockMvcResultMatchers.view().name("fortune"));

    verify(fortuneService, times(1)).getRandomFortune();
  }
}