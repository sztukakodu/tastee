package pl.sztukakodu.tastee.recipes.app.port;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.sztukakodu.tastee.recipes.domain.Recipe;

import java.util.List;
import java.util.Optional;

public interface ReadRecipesPort {
    List<Recipe> search();

    Optional<Recipe> getRecipeById(Long id);

    Page<Recipe> getPage(Pageable pageable);
}
