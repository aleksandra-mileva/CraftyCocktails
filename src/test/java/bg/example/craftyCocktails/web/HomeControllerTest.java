package bg.example.craftyCocktails.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import bg.example.craftyCocktails.model.dto.HomePageDto;
import bg.example.craftyCocktails.model.view.PictureHomePageViewModel;
import bg.example.craftyCocktails.service.HomePageService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

  @MockBean
  private HomePageService homePageService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testIndex() throws Exception {
    PictureHomePageViewModel picture = new PictureHomePageViewModel();
    HomePageDto homePageDTO = new HomePageDto();
    homePageDTO.setPictures(List.of(picture));
    homePageDTO.setMessage("Message");

    when(homePageService.initHomePageDto()).thenReturn(homePageDTO);
    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("index"))
        .andExpect(model().attributeExists("pictures"))
        .andExpect(model().attributeExists("message"));
  }
}