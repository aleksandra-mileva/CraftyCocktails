package bg.example.craftyCocktails.service;

import bg.example.craftyCocktails.model.dto.HomePageDto;
import bg.example.craftyCocktails.model.entity.enums.TypeNameEnum;
import bg.example.craftyCocktails.model.view.PictureHomePageViewModel;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class HomePageService {

  private final PictureService pictureService;

  public HomePageService(PictureService pictureService) {
    this.pictureService = pictureService;
  }

  public HomePageDto initHomePageDto() {
    List<PictureHomePageViewModel> pictures;
    String message = "";

    LocalTime now = LocalTime.now();
    if (now.getHour() < 16) {
      pictures = this.pictureService
          .getThreeRandomPicturesByCocktailType(TypeNameEnum.NON_ALCOHOLIC);
      message = "Need a non-alcoholic cocktail? This are our today's suggestions for you!";
    } else {
      pictures = this.pictureService
          .getThreeRandomPicturesByCocktailType(TypeNameEnum.ALCOHOLIC);
      message = "It's party time! This are our today's alcoholic suggestions for you!";
    }

    return new HomePageDto().setMessage(message).setPictures(pictures);
  }
}

