package bg.example.craftyCocktails.web;

import bg.example.craftyCocktails.model.dto.AddCocktailDto;
import bg.example.craftyCocktails.model.dto.EditCocktailDto;
import bg.example.craftyCocktails.model.dto.SearchCocktailDto;
import bg.example.craftyCocktails.model.dto.UploadPictureDto;
import bg.example.craftyCocktails.model.entity.enums.SpiritNameEnum;
import bg.example.craftyCocktails.model.entity.enums.TypeNameEnum;
import bg.example.craftyCocktails.model.user.CustomUserDetails;
import bg.example.craftyCocktails.model.view.CocktailViewModel;
import bg.example.craftyCocktails.service.CocktailService;
import bg.example.craftyCocktails.service.PictureService;
import bg.example.craftyCocktails.service.TypeService;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cocktails")
public class CocktailController {

  private final CocktailService cocktailService;
  private final PictureService pictureService;
  private final TypeService typeService;

  public CocktailController(
      CocktailService cocktailService,
      PictureService pictureService,
      TypeService typeService
  ) {
    this.cocktailService = cocktailService;
    this.pictureService = pictureService;
    this.typeService = typeService;
  }

  @ModelAttribute
  AddCocktailDto addCocktailDto() {
    return new AddCocktailDto();
  }

  @ModelAttribute("uploadPictureDto")
  UploadPictureDto uploadPictureDto() {
    return new UploadPictureDto();
  }

  @ModelAttribute("allTypes")
  List<TypeNameEnum> allTypes() {
    return typeService.getAllTypes();
  }

  @GetMapping("/all")
  public String allCocktails(
      Model model,
      @PageableDefault(sort = "name", direction = Sort.Direction.ASC, page = 0, size = 9) Pageable pageable
  ) {
    Page<CocktailViewModel> pagesWithAllCocktails = cocktailService.findAllCocktailViewModels(
        pageable);
    model.addAttribute("cocktails", pagesWithAllCocktails);
    model.addAttribute("heading",
        String.format("All cocktails (%s)", pagesWithAllCocktails.getTotalElements()));
    model.addAttribute("url", "/cocktails/all");
    return "all-cocktails";
  }

  @GetMapping("/whiskey")
  public String whiskeyCocktails(
      Model model,
      @PageableDefault(sort = "name", direction = Sort.Direction.ASC, page = 0, size = 9) Pageable pageable
  ) {
    Page<CocktailViewModel> pagesWhiskeyCocktails = cocktailService.findAllFilteredCocktailViewModels(
        SpiritNameEnum.WHISKEY, pageable);
    model.addAttribute("cocktails", pagesWhiskeyCocktails);
    model.addAttribute("heading",
        String.format("Whiskey cocktails (%s)", pagesWhiskeyCocktails.getTotalElements()));
    model.addAttribute("url", "/cocktails/whiskey");
    return "all-cocktails";
  }

  @GetMapping("/tequila")
  public String tequilaCocktails(
      Model model,
      @PageableDefault(sort = "name", direction = Sort.Direction.ASC, page = 0, size = 9) Pageable pageable
  ) {
    Page<CocktailViewModel> pagesTequilaCocktails = cocktailService.findAllFilteredCocktailViewModels(
        SpiritNameEnum.TEQUILA, pageable);
    model.addAttribute("cocktails", pagesTequilaCocktails);
    model.addAttribute("heading",
        String.format("Tequila cocktails (%s)", pagesTequilaCocktails.getTotalElements()));
    model.addAttribute("url", "/cocktails/tequila");
    return "all-cocktails";
  }

  @GetMapping("/gin")
  public String ginCocktails(
      Model model,
      @PageableDefault(sort = "name", direction = Sort.Direction.ASC, page = 0, size = 9) Pageable pageable
  ) {
    Page<CocktailViewModel> pagesGinCocktails = cocktailService.findAllFilteredCocktailViewModels(
        SpiritNameEnum.GIN, pageable);
    model.addAttribute("cocktails", pagesGinCocktails);
    model.addAttribute("heading",
        String.format("Gin cocktails (%s)", pagesGinCocktails.getTotalElements()));
    model.addAttribute("url", "/cocktails/gin");
    return "all-cocktails";
  }

  @GetMapping("/rum")
  public String rumCocktails(
      Model model,
      @PageableDefault(sort = "name", direction = Sort.Direction.ASC, page = 0, size = 9) Pageable pageable
  ) {
    Page<CocktailViewModel> pagesRumCocktails = cocktailService.findAllFilteredCocktailViewModels(
        SpiritNameEnum.RUM, pageable);
    model.addAttribute("cocktails", pagesRumCocktails);
    model.addAttribute("heading",
        String.format("Rum cocktails (%s)", pagesRumCocktails.getTotalElements()));
    model.addAttribute("url", "/cocktails/rum");
    return "all-cocktails";
  }

  @GetMapping("/vodka")
  public String vodkaCocktails(
      Model model,
      @PageableDefault(sort = "name", direction = Sort.Direction.ASC, page = 0, size = 9) Pageable pageable
  ) {
    Page<CocktailViewModel> pagesVodkaCocktails = cocktailService.findAllFilteredCocktailViewModels(
        SpiritNameEnum.VODKA, pageable);
    model.addAttribute("cocktails", pagesVodkaCocktails);
    model.addAttribute("heading",
        String.format("Vodka cocktails (%s)", pagesVodkaCocktails.getTotalElements()));
    model.addAttribute("url", "/cocktails/vodka");
    return "all-cocktails";
  }

  @GetMapping("/brandy")
  public String brandyCocktails(
      Model model,
      @PageableDefault(sort = "name", direction = Sort.Direction.ASC, page = 0, size = 9) Pageable pageable
  ) {
    Page<CocktailViewModel> pagesBrandyCocktails = cocktailService.findAllFilteredCocktailViewModels(
        SpiritNameEnum.BRANDY, pageable);
    model.addAttribute("cocktails", pagesBrandyCocktails);
    model.addAttribute("heading",
        String.format("Brandy cocktails (%s)", pagesBrandyCocktails.getTotalElements()));
    model.addAttribute("url", "/cocktails/brandy");
    return "all-cocktails";
  }

  @GetMapping("/non-alcoholic")
  public String nonAlcoholicCocktails(
      Model model,
      @PageableDefault(sort = "name", direction = Sort.Direction.ASC, page = 0, size = 9) Pageable pageable
  ) {
    Page<CocktailViewModel> pagesNonAlcoholicCocktails = cocktailService.findAllFilteredCocktailViewModels(
        SpiritNameEnum.NONE, pageable);
    model.addAttribute("cocktails", pagesNonAlcoholicCocktails);
    model.addAttribute("heading",
        String.format("Non-alcoholic cocktails (%s)", pagesNonAlcoholicCocktails.getTotalElements()));
    model.addAttribute("url", "/cocktails/non-alcoholic");
    return "all-cocktails";
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/add")
  public String addCocktail() {
    return "add-cocktail";
  }

  @PostMapping("/add")
  public String addCocktailConfirm(
      @Valid AddCocktailDto addCocktailDto,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {
    if (bindingResult.hasErrors()) {

      redirectAttributes.addFlashAttribute("addCocktailDto", addCocktailDto)
          .addFlashAttribute("org.springframework.validation.BindingResult.addCocktailDto"
              , bindingResult);

      return "redirect:/cocktails/add";
    }
    Long cocktailId = cocktailService.addCocktail(addCocktailDto, userDetails);
    return "redirect:/cocktails/details/" + cocktailId;
  }

  @GetMapping("/details/{id}")
  public String details(@PathVariable Long id, Model model, Principal principal) {

    model.addAttribute(
        "cocktail", cocktailService.findCocktailDetailsViewModelById(id,
            principal != null ? principal.getName() : ""));
    return "cocktail-details";
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/details/{id}/picture/add")
  public String addPicture(
      UploadPictureDto uploadPictureDto,
      @PathVariable Long id,
      @AuthenticationPrincipal CustomUserDetails principal
  ) {
    pictureService.createAndSavePictureEntity(principal.getId(), uploadPictureDto.getPicture(), id);
    return "redirect:/cocktails/details/" + id;
  }

  @PreAuthorize("isAuthenticated() && @pictureService.isOwner(#principal.name, #pictureId)")
  @DeleteMapping("/details/{cocktailId}/picture/delete")
  public String deletePicture(@PathVariable("cocktailId") Long cocktailId,
      @RequestParam("pictureId") Long pictureId,
      Principal principal) {
    pictureService.deletePicture(pictureId);
    return "redirect:/cocktails/details/" + cocktailId;
  }

  @PreAuthorize("isAuthenticated() && @cocktailService.isOwner(#principal.name, #cocktailId)")
  @GetMapping("/edit/{id}")
  public String editCocktail(
      Principal principal,
      @PathVariable("id") Long cocktailId,
      Model model) {

    if (!model.containsAttribute("editCocktailDto")) {
      EditCocktailDto editCocktailDto = cocktailService.getCocktailEditDetails(cocktailId);
      model.addAttribute("editCocktailDto", editCocktailDto);
    }
    return "cocktail-update";
  }

  @PutMapping("/edit/{id}")
  public String update(
      @PathVariable("id") Long id,
      @Valid EditCocktailDto editCocktailDto,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes,
      @AuthenticationPrincipal UserDetails userDetails
  ) {
    if (bindingResult.hasErrors()) {
      redirectAttributes.addFlashAttribute("editCocktailDto", editCocktailDto);
      redirectAttributes.addFlashAttribute(
          "org.springframework.validation.BindingResult.editCocktailDto", bindingResult);
      return "redirect:/cocktails/edit/" + id;
    }

    cocktailService.updateCocktailById(editCocktailDto, id, userDetails);
    return "redirect:/cocktails/details/" + id;
  }

  @PreAuthorize("isAuthenticated() && @cocktailService.isOwner(#principal.name, #cocktailId)")
  @DeleteMapping("/delete/{id}")
  public String deleteCocktail(
      Principal principal,
      @PathVariable("id") Long cocktailId
  ) {
    cocktailService.deleteCocktailById(cocktailId);
    return "redirect:/cocktails/all";
  }

  @GetMapping("/search")
  public String searchQuery(
      @RequestParam Map<String, String> queryParams,
      HttpServletRequest request,
      @Valid SearchCocktailDto searchCocktailDto,
      BindingResult bindingResult,
      Model model,
      @PageableDefault(sort = "name", direction = Sort.Direction.ASC, page = 0, size = 9) Pageable pageable
  ) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("searchCocktailDto", searchCocktailDto);
      model.addAttribute(
          "org.springframework.validation.BindingResult.searchCocktailDto",
          bindingResult);
      return "cocktail-search";
    }

    if (!model.containsAttribute("searchCocktailDto")) {
      model.addAttribute("searchCocktailDto", searchCocktailDto);
      model.addAttribute("result", searchCocktailDto.toString());
    }

    if (!searchCocktailDto.isEmpty()) {
      Page<CocktailViewModel> cocktails = cocktailService.searchCocktail(searchCocktailDto,
          pageable);

      model.addAttribute(
          "cocktails", cocktailService.searchCocktail(searchCocktailDto, pageable));
      model.addAttribute("result", searchCocktailDto.toString());

      String queryString = queryParams.entrySet().stream()
          .filter(entry -> !entry.getKey().equals("page") && !entry.getKey().equals("size"))
          .map(entry -> entry.getKey() + "=" + entry.getValue())
          .collect(Collectors.joining("&"));

      String url =
          request.getRequestURL().toString() + (queryString.isEmpty() ? "" : "?" + queryString);
      model.addAttribute("url", url);
    }
    return "cocktail-search";
  }
}
