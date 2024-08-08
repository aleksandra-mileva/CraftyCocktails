package bg.example.craftyCocktails.repository;

import bg.example.craftyCocktails.model.entity.PictureEntity;
import bg.example.craftyCocktails.model.entity.TypeEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<PictureEntity, Long> {

  Page<PictureEntity> findAllByAuthor_Username(String username, Pageable pageable);

  @Query("SELECT p "
      + " FROM PictureEntity p "
      + " JOIN p.cocktail.types t "
      + " WHERE :typeEntity MEMBER OF p.cocktail.types")
  List<PictureEntity> findAllPicturesByCocktailType(TypeEntity typeEntity);

  void deleteAllById(Long id);
}
