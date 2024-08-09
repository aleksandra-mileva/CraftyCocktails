package bg.example.craftyCocktails.model.dto;

import bg.example.craftyCocktails.model.view.PictureHomePageViewModel;
import java.util.List;

public class HomePageDto {

  private List<PictureHomePageViewModel> pictures;
  private String message;

  public List<PictureHomePageViewModel> getPictures() {
    return pictures;
  }

  public HomePageDto setPictures(List<PictureHomePageViewModel> pictures) {
    this.pictures = pictures;
    return this;
  }

  public String getMessage() {
    return message;
  }

  public HomePageDto setMessage(String message) {
    this.message = message;
    return this;
  }
}
