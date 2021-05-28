package pl.sztukakodu.tastee.recipes.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sztukakodu.tastee.recipes.domain.Ingredient;
import pl.sztukakodu.tastee.recipes.domain.Recipe;

import java.util.List;
import java.util.Set;

public interface RecipesRepository extends JpaRepository<Recipe, Long> {
    boolean existsByTitle(String title);

    List<Recipe> findAllByIngredientsIn(Set<Ingredient> ingredients);
}
