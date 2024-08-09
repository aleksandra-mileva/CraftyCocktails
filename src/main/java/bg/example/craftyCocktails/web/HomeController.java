package bg.example.craftyCocktails.web;

import bg.example.craftyCocktails.model.dto.HomePageDto;
import bg.example.craftyCocktails.service.HomePageService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  private final HomePageService homePageService;
  private HomePageDto homePageDto;

  public HomeController(HomePageService homePageService) {
    this.homePageService = homePageService;
  }

  @GetMapping("/")
  public String index(Model model) {

    if (homePageDto == null) {
      homePageDto = homePageService.initHomePageDto();
    }

    model.addAttribute("pictures", homePageDto.getPictures());
    model.addAttribute("message", homePageDto.getMessage());

    return "index";
  }

  @Scheduled(cron = "0 0 0,16 * * ?") // Execute at 00:00, 16:00 every day
  public void scheduleInitHomePageDto() {
    homePageDto = homePageService.initHomePageDto();
  }
}



