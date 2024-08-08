package bg.example.craftyCocktails.repository;

import bg.example.craftyCocktails.model.entity.RoleEntity;
import bg.example.craftyCocktails.model.entity.enums.RoleNameEnum;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

  Optional<RoleEntity> findByRole(RoleNameEnum role);
}
