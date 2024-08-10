package bg.example.craftyCocktails.model.view;

public class PictureViewModel {

  private Long id;
  private String url;
  private Long cocktailId;
  String authorUsername;
  private boolean canNotDelete;

  public Long getId() {
    return id;
  }

  public PictureViewModel setId(Long id) {
    this.id = id;
    return this;
  }

  public String getUrl() {
    return url;
  }

  public PictureViewModel setUrl(String url) {
    this.url = url;
    return this;
  }

  public Long getCocktailId() {
    return cocktailId;
  }

  public PictureViewModel setCocktailId(Long cocktailId) {
    this.cocktailId = cocktailId;
    return this;
  }

  public boolean isCanNotDelete() {
    return canNotDelete;
  }

  public PictureViewModel setCanNotDelete(boolean canNotDelete) {
    this.canNotDelete = canNotDelete;
    return this;
  }

  public String getAuthorUsername() {
    return authorUsername;
  }

  public PictureViewModel setAuthorUsername(String authorUsername) {
    this.authorUsername = authorUsername;
    return this;
  }
}
