package pl.sztukakodu.tastee.recipes.app.port;

import pl.sztukakodu.tastee.recipes.domain.Recipe;

import java.util.List;

public interface ReadRecipesPort {
    List<Recipe> search();
}
