package pl.sztukakodu.tastee.recipes.app;

import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.sztukakodu.tastee.recipes.app.port.SearchRecipesPort;
import pl.sztukakodu.tastee.recipes.db.IngredientsRepository;
import pl.sztukakodu.tastee.recipes.db.RecipesRepository;
import pl.sztukakodu.tastee.recipes.domain.Ingredient;
import pl.sztukakodu.tastee.recipes.domain.Recipe;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
class SearchRecipesService implements SearchRecipesPort {

    private final RecipesRepository repository;
    private final IngredientsRepository ingredientsRepository;

    @Timed("api_recipes_search")
    @Override
    public Page<Recipe> search(Pageable pageable, Set<String> values) {
        Set<Ingredient> ingredients = values
            .stream()
            .map(ingredientsRepository::findByName)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toSet());
        return repository.findAllByIngredientsIn(pageable, ingredients);
    }
}
