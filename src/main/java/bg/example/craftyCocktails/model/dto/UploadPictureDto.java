package bg.example.craftyCocktails.model.dto;

import org.springframework.web.multipart.MultipartFile;

public class UploadPictureDto {

  private Long cocktailId;
  private MultipartFile picture;

  public MultipartFile getPicture() {
    return picture;
  }

  public UploadPictureDto setPicture(MultipartFile picture) {
    this.picture = picture;
    return this;
  }

  public Long getCocktailId() {
    return cocktailId;
  }

  public UploadPictureDto setCocktailId(Long cocktailId) {
    this.cocktailId = cocktailId;
    return this;
  }
}


