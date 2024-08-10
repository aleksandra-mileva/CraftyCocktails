package bg.example.craftyCocktails.web;

import bg.example.craftyCocktails.model.entity.enums.SpiritNameEnum;
import bg.example.craftyCocktails.model.view.StatisticsViewDto;
import bg.example.craftyCocktails.service.CocktailService;
import bg.example.craftyCocktails.service.UserService;
import java.time.LocalDateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatisticsController {

  private final CocktailService cocktailService;
  private final UserService userService;

  public StatisticsController(CocktailService cocktailService, UserService userService) {
    this.cocktailService = cocktailService;
    this.userService = userService;
  }

  @GetMapping("/statistics")
  public String register(Model model) {

    StatisticsViewDto statisticsViewDto = new StatisticsViewDto()
        .setAllCocktails(this.cocktailService.findCountAll())
        .setGinCocktails(this.cocktailService.findCountBySpirit(SpiritNameEnum.GIN))
        .setTequilaCocktails(this.cocktailService.findCountBySpirit(SpiritNameEnum.TEQUILA))
        .setWhiskeyCocktails(this.cocktailService.findCountBySpirit(SpiritNameEnum.WHISKEY))
        .setRumCocktails(this.cocktailService.findCountBySpirit(SpiritNameEnum.RUM))
        .setVodkaCocktails(this.cocktailService.findCountBySpirit(SpiritNameEnum.VODKA))
        .setBrandyCocktails(this.cocktailService.findCountBySpirit(SpiritNameEnum.BRANDY))
        .setNonAlcoholicCocktails(this.cocktailService.findCountBySpirit(SpiritNameEnum.NONE))
        .setUsersCount(this.userService.getCountRegisteredUsers())
        .setLocalDateTime(LocalDateTime.now());

    model.addAttribute("statistics", statisticsViewDto);
    return "statistics";
  }
}
