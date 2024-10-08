package bg.example.craftyCocktails.repository;

import bg.example.craftyCocktails.model.entity.CocktailEntity;
import bg.example.craftyCocktails.model.entity.enums.SpiritNameEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CocktailRepository extends JpaRepository<CocktailEntity, Long>,
    JpaSpecificationExecutor<CocktailEntity> {

  Page<CocktailEntity> findAllBySpirit(SpiritNameEnum spiritName, Pageable pageable);

  Page<CocktailEntity> findAllByAuthor_Id(Long authorId, Pageable pageable);

  @Query("SELECT c "
      + " FROM CocktailEntity c "
      + " JOIN c.favoriteUsers u "
      + " WHERE u.id = :userId")
  Page<CocktailEntity> findAllFavoriteCocktails(@Param("userId") Long userId, Pageable pageable);

  long countCocktailEntitiesBySpirit(SpiritNameEnum spirit);
}

