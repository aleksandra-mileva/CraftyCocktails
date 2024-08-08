package bg.example.craftyCocktails.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pictures")
public class PictureEntity extends BaseEntity {

  @Column(nullable = false)
  private String url;
  @ManyToOne(optional = false)
  private UserEntity author;
  @Column
  private String publicId;
  @Column
  private String title;
  @ManyToOne()
  private CocktailEntity cocktail;

  public CocktailEntity getCocktail() {
    return cocktail;
  }

  public PictureEntity setCocktail(CocktailEntity cocktail) {
    this.cocktail = cocktail;
    return this;
  }

  public PictureEntity() {
  }

  public String getPublicId() {
    return publicId;
  }

  public String getUrl() {
    return url;
  }

  public UserEntity getAuthor() {
    return author;
  }

  @Column
  public String getTitle() {
    return title;
  }

  public PictureEntity setTitle(String name) {
    this.title = name;
    return this;
  }

  public PictureEntity setUrl(String url) {
    this.url = url;
    return this;
  }

  public PictureEntity setAuthor(UserEntity author) {
    this.author = author;
    return this;
  }

  public PictureEntity setPublicId(String publicId) {
    this.publicId = publicId;
    return this;
  }
}
