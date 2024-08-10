package bg.example.craftyCocktails.model.dto;

import bg.example.craftyCocktails.model.entity.enums.FlavourEnum;
import bg.example.craftyCocktails.model.entity.enums.SpiritNameEnum;
import bg.example.craftyCocktails.model.entity.enums.TypeNameEnum;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class SearchCocktailDto {

  @Size(max = 30)
  private String name;
  private FlavourEnum flavour;
  private List<TypeNameEnum> types;
  private SpiritNameEnum spirit;
  @Positive
  private Integer minPercentAlcohol;
  @Positive
  private Integer maxPercentAlcohol;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public FlavourEnum getFlavour() {
    return flavour;
  }

  public void setFlavour(FlavourEnum flavour) {
    this.flavour = flavour;
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

  public Integer getMinPercentAlcohol() {
    return minPercentAlcohol;
  }

  public void setMinPercentAlcohol(Integer minPercentAlcohol) {
    this.minPercentAlcohol = minPercentAlcohol;
  }

  public Integer getMaxPercentAlcohol() {
    return maxPercentAlcohol;
  }

  public void setMaxPercentAlcohol(Integer maxPercentAlcohol) {
    this.maxPercentAlcohol = maxPercentAlcohol;
  }

  public boolean isEmpty() {
    return (name == null || name.isEmpty()) &&
        maxPercentAlcohol == null &&
        minPercentAlcohol == null &&
        flavour == null &&
        spirit == null &&
        (types == null || types.isEmpty());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    if (name != null && !name.isEmpty()) {
      sb.append(String.format("Name: " + name + " "));
    }

    if (flavour != null) {
      sb.append(String.format("Flavour: " + flavour + " "));
    }

    if (types != null && !types.isEmpty()) {
      List<String> typeNames = types.stream()
          .map(TypeNameEnum::name)
          .collect(Collectors.toList());

      String result = String.join(", ", typeNames);
      sb.append(String.format("Types: " + result + " "));
    }

    if (spirit != null) {
      sb.append(String.format("Spirit: " + spirit + " "));
    }

    if (minPercentAlcohol != null) {
      sb.append(String.format("Min percent alcohol: " + minPercentAlcohol + "min "));
    }

    if (maxPercentAlcohol != null) {
      sb.append(String.format("Max percent alcohol: " + maxPercentAlcohol + "min "));
    }
    return sb.toString();
  }
}
