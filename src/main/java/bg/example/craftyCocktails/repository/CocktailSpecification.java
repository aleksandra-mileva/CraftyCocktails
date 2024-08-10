package bg.example.craftyCocktails.repository;

import bg.example.craftyCocktails.model.dto.SearchCocktailDto;
import bg.example.craftyCocktails.model.entity.CocktailEntity;
import bg.example.craftyCocktails.model.entity.enums.TypeNameEnum;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class CocktailSpecification implements Specification<CocktailEntity> {

  private final SearchCocktailDto searchCocktailDto;

  public CocktailSpecification(SearchCocktailDto searchCocktailDto) {
    this.searchCocktailDto = searchCocktailDto;
  }

  @Override
  public Predicate toPredicate(Root<CocktailEntity> root, CriteriaQuery<?> query,
      CriteriaBuilder cb) {

    Predicate p = cb.conjunction();

    if (searchCocktailDto.getName() != null && !searchCocktailDto.getName().isEmpty()) {
      String searchName = "%" + searchCocktailDto.getName().toLowerCase() + "%";
      p.getExpressions().add(
          cb.and(cb.like(cb.lower(root.get("name")), searchName)));
    }

    if (searchCocktailDto.getFlavour() != null) {
      p.getExpressions().add(
          cb.equal(root.get("flavour"), searchCocktailDto.getFlavour()));
    }

    if (searchCocktailDto.getSpirit() != null) {
      p.getExpressions().add(
          cb.equal(root.get("spirit"), searchCocktailDto.getSpirit()));
    }

    if (searchCocktailDto.getMinPercentAlcohol() != null) {
      p.getExpressions().add(
          cb.and(cb.greaterThanOrEqualTo(root.get("percentAlcohol"),
              searchCocktailDto.getMinPercentAlcohol())));
    }

    if (searchCocktailDto.getMaxPercentAlcohol() != null) {
      p.getExpressions().add(
          cb.and(cb.lessThanOrEqualTo(root.get("percentAlcohol"),
              searchCocktailDto.getMaxPercentAlcohol())));
    }

    if (searchCocktailDto.getTypes() != null && !searchCocktailDto.getTypes().isEmpty()) {
      for (TypeNameEnum type : searchCocktailDto.getTypes()) {
        p.getExpressions().add(
            cb.equal(root.join("types").get("name"), type));
      }
    }


    return p;
  }
}
