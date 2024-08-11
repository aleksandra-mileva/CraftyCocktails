package bg.example.craftyCocktails.model.dto;

import javax.validation.constraints.NotEmpty;

public class AddFortuneDto {

  @NotEmpty
  private String content;
  @NotEmpty
  private String author;

  public String getContent() {
    return content;
  }

  public AddFortuneDto setContent(String content) {
    this.content = content;
    return this;
  }

  public String getAuthor() {
    return author;
  }

  public AddFortuneDto setAuthor(String author) {
    this.author = author;
    return this;
  }
}
