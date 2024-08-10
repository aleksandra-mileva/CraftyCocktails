package bg.example.craftyCocktails.service;

import bg.example.craftyCocktails.model.entity.TypeEntity;
import bg.example.craftyCocktails.model.entity.enums.TypeNameEnum;
import bg.example.craftyCocktails.repository.TypeRepository;
import bg.example.craftyCocktails.web.exception.ObjectNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class TypeService {

  private final TypeRepository typeRepository;

  public TypeService(TypeRepository typeRepository) {
    this.typeRepository = typeRepository;
  }

  public TypeEntity findByTypeName(TypeNameEnum typeNameEnum) {
    return this.typeRepository.findByName(typeNameEnum)
        .orElseThrow(() -> new ObjectNotFoundException(
            "Type with name " + typeNameEnum.name() + " not found!"));
  }

  public List<TypeNameEnum> getAllTypes() {
    return this.typeRepository.findAll().stream().map(TypeEntity::getName)
        .collect(Collectors.toList());
  }
}
