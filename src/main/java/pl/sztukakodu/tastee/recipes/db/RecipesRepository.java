package pl.sztukakodu.tastee.recipes.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.sztukakodu.tastee.recipes.domain.Recipe;

public interface RecipesRepository extends JpaRepository<Recipe, Long> {
    boolean existsByTitle(String title);

    @Query("SELECT AVG(r.ingredients.size) FROM Recipe r")
    boolean avgIngredientsPerRecipe();
}
