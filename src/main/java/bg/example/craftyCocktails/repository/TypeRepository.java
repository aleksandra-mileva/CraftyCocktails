package bg.example.craftyCocktails.repository;

import bg.example.craftyCocktails.model.entity.TypeEntity;
import bg.example.craftyCocktails.model.entity.enums.TypeNameEnum;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<TypeEntity, Long> {

  Optional<TypeEntity> findByName(TypeNameEnum name);
}
