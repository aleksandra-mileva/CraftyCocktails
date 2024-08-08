package bg.example.craftyCocktails.model.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comments")
public class CommentEntity extends BaseEntity {

  @Column(nullable = false, length = 3000)
  private String text;
  @Column(name = "created", nullable = false)
  private LocalDateTime created;
  @ManyToOne(optional = false)
  private UserEntity author;
  @ManyToOne(optional = false)
  private CocktailEntity cocktail;

  public CommentEntity() {
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public void setCreated(LocalDateTime created) {
    this.created = created;
  }

  public UserEntity getAuthor() {
    return author;
  }

  public void setAuthor(UserEntity author) {
    this.author = author;
  }

  public CocktailEntity getCocktail() {
    return cocktail;
  }

  public void setCocktail(CocktailEntity cocktail) {
    this.cocktail = cocktail;
  }
}
