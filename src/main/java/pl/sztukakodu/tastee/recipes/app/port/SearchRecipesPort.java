package pl.sztukakodu.tastee.recipes.app.port;

import pl.sztukakodu.tastee.recipes.domain.Recipe;

import java.util.List;
import java.util.Set;

public interface SearchRecipesPort {
    List<Recipe> search(Set<String> values);
}
