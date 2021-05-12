package pl.sztukakodu.tastee.recipes.db;

import pl.sztukakodu.tastee.recipes.domain.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipesRepository {

    List<Recipe> findAll();

    Optional<Recipe> findById(String id);

    String save(Recipe recipe);
}
