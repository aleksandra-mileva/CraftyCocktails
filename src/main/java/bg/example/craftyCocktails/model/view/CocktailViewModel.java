package bg.example.craftyCocktails.model.view;

import bg.example.craftyCocktails.model.entity.enums.FlavourEnum;
import bg.example.craftyCocktails.model.entity.enums.SpiritNameEnum;

public class CocktailViewModel {

  private Long id;
  private String name;
  private FlavourEnum flavour;
  private SpiritNameEnum spirit;
  private String author;
  private String pictureUrl;
  private int percentAlcohol;
  private int servings;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public CocktailViewModel setName(String name) {
    this.name = name;
    return this;
  }

  public SpiritNameEnum getSpirit() {
    return spirit;
  }

  public void setSpirit(SpiritNameEnum spirit) {
    this.spirit = spirit;
  }

  public FlavourEnum getFlavour() {
    return flavour;
  }

  public CocktailViewModel setFlavour(FlavourEnum flavour) {
    this.flavour = flavour;
    return this;
  }

  public String getAuthor() {
    return author;
  }

  public CocktailViewModel setAuthor(String author) {
    this.author = author;
    return this;
  }

  public String getPictureUrl() {
    return pictureUrl;
  }

  public CocktailViewModel setPictureUrl(String pictureUrl) {
    this.pictureUrl = pictureUrl;
    return this;
  }

  public int getPercentAlcohol() {
    return percentAlcohol;
  }

  public CocktailViewModel setPercentAlcohol(int percentAlcohol) {
    this.percentAlcohol = percentAlcohol;
    return this;
  }

  public int getServings() {
    return servings;
  }

  public CocktailViewModel setServings(int servings) {
    this.servings = servings;
    return this;
  }
}
