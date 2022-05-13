package pl.sztukakodu.tastee.recipes.app.port;

import pl.sztukakodu.tastee.recipes.domain.Recipe;

import java.util.Optional;

public interface GenerateRecipes {
    Optional<Recipe> createNew();
}
