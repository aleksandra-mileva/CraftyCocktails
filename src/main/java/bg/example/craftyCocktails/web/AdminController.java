package bg.example.craftyCocktails.web;

import bg.example.craftyCocktails.model.dto.AddFortuneDto;
import bg.example.craftyCocktails.model.entity.enums.SpiritNameEnum;
import bg.example.craftyCocktails.model.view.FortunelViewModel;
import bg.example.craftyCocktails.model.view.StatisticsViewDto;
import bg.example.craftyCocktails.service.CocktailService;
import bg.example.craftyCocktails.service.FortuneService;
import bg.example.craftyCocktails.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {

  private final CocktailService cocktailService;
  private final UserService userService;
  private final FortuneService fortuneService;

  public AdminController(
      CocktailService cocktailService,
      UserService userService,
      FortuneService fortuneService
  ) {
    this.cocktailService = cocktailService;
    this.userService = userService;
    this.fortuneService = fortuneService;
  }

  @ModelAttribute
  AddFortuneDto addFortuneDto() {
    return new AddFortuneDto();
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

  @GetMapping("/fortunes")
  public String allFortunes(Model model) {
    List<FortunelViewModel> allFortunes = fortuneService.getAllFortunes();
    model.addAttribute("fortunes", allFortunes);
    model.addAttribute("heading",
        String.format("All fortunes (%s)", allFortunes.size()));
    return "all-fortunes";
  }

  @DeleteMapping("/fortunes/{id}")
  public String deleteFortune(@PathVariable("id") Long fortuneId) {
    fortuneService.deleteFortuneById(fortuneId);

    return "redirect:/fortunes";
  }

  @GetMapping("/fortunes/add")
  public String addFortune() {
    return "add-fortune";
  }

  @PostMapping("/fortunes/add")
  public String addFortuneConfirm(
      @Valid AddFortuneDto addFortuneDto,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes
  ) {
    if (bindingResult.hasErrors()) {

      redirectAttributes.addFlashAttribute("addFortuneDto", addFortuneDto)
          .addFlashAttribute("org.springframework.validation.BindingResult.addFortuneDto",
              bindingResult);

      return "redirect:/fortunes/add";
    }

    fortuneService.addFortune(addFortuneDto);
    return "redirect:/fortunes/";
  }
}
