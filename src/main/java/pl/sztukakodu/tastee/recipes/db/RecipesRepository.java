package pl.sztukakodu.tastee.recipes.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sztukakodu.tastee.recipes.domain.Recipe;

public interface RecipesRepository extends JpaRepository<Recipe, Long> {
    boolean existsByTitle(String title);
}
