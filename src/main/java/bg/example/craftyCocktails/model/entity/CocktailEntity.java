package bg.example.craftyCocktails.model.entity;

import bg.example.craftyCocktails.model.entity.enums.FlavourEnum;
import bg.example.craftyCocktails.model.entity.enums.SpiritNameEnum;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cocktails")
public class CocktailEntity extends BaseEntity {

  @Column(nullable = false)
  private String name;
  @Column(nullable = false, length = 2000)
  private String ingredients;
  @Column(nullable = false, length = 2000)
  private String preparation;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private FlavourEnum flavour;
  @ManyToOne(optional = false)
  private UserEntity author;
  @Column
  private String videoUrl;
  @OneToMany(mappedBy = "cocktail")
  private List<PictureEntity> pictures;
  @ManyToMany(fetch = FetchType.LAZY)
  private List<TypeEntity> types;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private SpiritNameEnum spirit;
  @OneToMany(mappedBy = "cocktail", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<CommentEntity> comments;
  @Column(nullable = false)
  private Integer percentAlcohol;
  @Column(nullable = false)
  private Integer servings;
  @ManyToMany(mappedBy = "favorites")
  private List<UserEntity> favoriteUsers;

  public CocktailEntity() {
  }

  public String getName() {
    return name;
  }

  public CocktailEntity setName(String name) {
    this.name = name;
    return this;
  }

  public String getIngredients() {
    return ingredients;
  }

  public CocktailEntity setIngredients(String ingredients) {
    this.ingredients = ingredients;
    return this;
  }

  public String getPreparation() {
    return preparation;
  }

  public CocktailEntity setPreparation(String preparation) {
    this.preparation = preparation;
    return this;
  }

  public FlavourEnum getFlavour() {
    return flavour;
  }

  public CocktailEntity setFlavour(FlavourEnum flavour) {
    this.flavour = flavour;
    return this;
  }

  public UserEntity getAuthor() {
    return author;
  }

  public CocktailEntity setAuthor(UserEntity author) {
    this.author = author;
    return this;
  }

  public String getVideoUrl() {
    return videoUrl;
  }

  public CocktailEntity setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
    return this;
  }

  public List<PictureEntity> getPictures() {
    return pictures;
  }

  public CocktailEntity setPictures(List<PictureEntity> pictures) {
    this.pictures = pictures;
    return this;
  }

  public List<TypeEntity> getTypes() {
    return types;
  }

  public CocktailEntity setTypes(List<TypeEntity> types) {
    this.types = types;
    return this;
  }

  public SpiritNameEnum getSpirit() {
    return spirit;
  }

  public CocktailEntity setSpirit(SpiritNameEnum spirit) {
    this.spirit = spirit;
    return this;
  }

  public List<CommentEntity> getComments() {
    return comments;
  }

  public CocktailEntity setComments(List<CommentEntity> comments) {
    this.comments = comments;
    return this;
  }

  public Integer getPercentAlcohol() {
    return percentAlcohol;
  }

  public CocktailEntity setPercentAlcohol(Integer percentAlcohol) {
    this.percentAlcohol = percentAlcohol;
    return this;
  }

  public Integer getServings() {
    return servings;
  }

  public CocktailEntity setServings(Integer servings) {
    this.servings = servings;
    return this;
  }

  public List<UserEntity> getFavoriteUsers() {
    return favoriteUsers;
  }

  public CocktailEntity setFavoriteUsers(List<UserEntity> favoriteUsers) {
    this.favoriteUsers = favoriteUsers;
    return this;
  }
}
