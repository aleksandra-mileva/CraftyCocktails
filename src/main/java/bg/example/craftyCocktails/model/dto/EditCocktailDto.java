package bg.example.craftyCocktails.model.dto;

import bg.example.craftyCocktails.model.entity.enums.FlavourEnum;
import bg.example.craftyCocktails.model.entity.enums.SpiritNameEnum;
import bg.example.craftyCocktails.model.entity.enums.TypeNameEnum;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class EditCocktailDto {

  Long id;
  @NotEmpty
  @Size(min = 5, max = 30)
  private String name;
  @NotEmpty
  private String ingredients;
  @NotEmpty
  private String preparation;
  @NotNull
  private FlavourEnum flavour;
  private String videoUrl;
  private List<TypeNameEnum> types;
  @NotNull
  private SpiritNameEnum spirit;
  @NotNull
  @Positive
  private Integer percentAlcohol;
  @NotNull
  @Positive
  private Integer servings;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIngredients() {
    return ingredients;
  }

  public void setIngredients(String ingredients) {
    this.ingredients = ingredients;
  }

  public String getPreparation() {
    return preparation;
  }

  public void setPreparation(String preparation) {
    this.preparation = preparation;
  }

  public FlavourEnum getFlavour() {
    return flavour;
  }

  public void setFlavour(FlavourEnum flavour) {
    this.flavour = flavour;
  }

  public String getVideoUrl() {
    return videoUrl;
  }

  public void setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
  }

  public List<TypeNameEnum> getTypes() {
    return types;
  }

  public void setTypes(List<TypeNameEnum> types) {
    this.types = types;
  }

  public SpiritNameEnum getSpirit() {
    return spirit;
  }

  public void setSpirit(SpiritNameEnum spirit) {
    this.spirit = spirit;
  }

  public Integer getPercentAlcohol() {
    return percentAlcohol;
  }

  public void setPercentAlcohol(Integer percentAlcohol) {
    this.percentAlcohol = percentAlcohol;
  }

  public Integer getServings() {
    return servings;
  }

  public void setServings(Integer servings) {
    this.servings = servings;
  }
}
