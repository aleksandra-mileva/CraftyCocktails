package bg.example.craftyCocktails.web;

import bg.example.craftyCocktails.model.dto.AddFortuneDto;
import bg.example.craftyCocktails.model.view.FortunelViewModel;
import bg.example.craftyCocktails.service.FortuneService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/fortunes")

public class FortuneController {

  private final FortuneService fortuneService;

  public FortuneController(FortuneService fortuneService) {
    this.fortuneService = fortuneService;
  }

  @ModelAttribute
  AddFortuneDto addFortuneDto() {
    return new AddFortuneDto();
  }

  @GetMapping
  public String allFortunes(Model model) {
    List<FortunelViewModel> allFortunes = fortuneService.getAllFortunes();
    model.addAttribute("fortunes", allFortunes);
    model.addAttribute("heading",
        String.format("All fortunes (%s)", allFortunes.size()));
    return "all-fortunes";
  }

  @DeleteMapping("/{id}")
  public String deleteFortune(@PathVariable("id") Long fortuneId) {
    fortuneService.deleteFortuneById(fortuneId);

    return "redirect:/fortunes";
  }

  @GetMapping("/add")
  public String addFortune() {
    return "add-fortune";
  }

  @PostMapping("/add")
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

  @GetMapping("/fortune")
  public String getFortune(Model model) {
    FortunelViewModel fortune = fortuneService.getRandomFortune();
    model.addAttribute("fortune", fortune);
    return "fortune";
  }
}
