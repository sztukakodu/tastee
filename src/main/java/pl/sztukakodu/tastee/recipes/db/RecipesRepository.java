package pl.sztukakodu.tastee.recipes.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.sztukakodu.tastee.recipes.domain.Ingredient;
import pl.sztukakodu.tastee.recipes.domain.Recipe;

import java.util.Set;

public interface RecipesRepository extends JpaRepository<Recipe, Long> {
    boolean existsByTitle(String title);

    @Query("SELECT AVG(r.ingredients.size) FROM Recipe r")
    Double avgIngredientsPerRecipe();

    Page<Recipe> findAllByIngredientsIn(Pageable pageable, Set<Ingredient> ingredients);
}
