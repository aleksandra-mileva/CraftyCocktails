package bg.example.craftyCocktails.repository;

import bg.example.craftyCocktails.model.entity.SecureTokenEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecureTokenRepository extends JpaRepository<SecureTokenEntity, Long> {

  Optional<SecureTokenEntity> findByToken(String token);

  List<SecureTokenEntity> findAllByExpireAtBefore(LocalDateTime expireAt);
}
