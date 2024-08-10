package bg.example.craftyCocktails.model.dto;

public class CommentServiceModel {

  private Long cocktailId;
  private String message;
  private String creator;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public Long getCocktailId() {
    return cocktailId;
  }

  public void setCocktailId(Long cocktailId) {
    this.cocktailId = cocktailId;
  }
}
