package pl.sztukakodu.tastee.recipes.app.port;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.sztukakodu.tastee.recipes.domain.Recipe;

import java.util.Set;

public interface SearchRecipesPort {
    Page<Recipe> search(Pageable pageable, Set<String> ingredients);
}
