package bg.example.craftyCocktails.repository;

import bg.example.craftyCocktails.model.entity.UserEntity;
import bg.example.craftyCocktails.model.entity.enums.RoleNameEnum;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  Optional<UserEntity> findByUsername(String username);

  @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE r.role = :role")
  List<UserEntity> findByRole(@Param("role") RoleNameEnum role);

  Optional<UserEntity> findByEmail(String email);

  List<UserEntity> findAllByAccountVerifiedEqualsAndCreatedOnBefore(boolean accountVerified,
      Timestamp createdOn);
}
