package bg.example.craftyCocktails.model.view;

public class FortunelViewModel {

  private Long id;
  private String content;
  private String author;

  public Long getId() {
    return id;
  }

  public FortunelViewModel setId(Long id) {
    this.id = id;
    return this;
  }

  public String getContent() {
    return content;
  }

  public FortunelViewModel setContent(String content) {
    this.content = content;
    return this;
  }

  public String getAuthor() {
    return author;
  }

  public FortunelViewModel setAuthor(String author) {
    this.author = author;
    return this;
  }
}
