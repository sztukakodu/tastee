package pl.sztukakodu.tastee.recipes.app;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sztukakodu.tastee.recipes.app.port.GenerateRecipes;
import pl.sztukakodu.tastee.recipes.db.IngredientsRepository;
import pl.sztukakodu.tastee.recipes.db.RecipesRepository;
import pl.sztukakodu.tastee.recipes.domain.Ingredient;
import pl.sztukakodu.tastee.recipes.domain.Recipe;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
class GenerateRecipesService implements GenerateRecipes {

    private final RecipesRepository repository;
    private final IngredientsRepository ingredientsRepository;
    private final RecipesProvider provider;

    @Override
    @Transactional
    public Optional<Recipe> createNew() {
        Recipe recipe = provider.newRecipe();
        if (!repository.existsByTitle(recipe.getTitle())) {
            recipe.setIngredients(fetchIngredients(recipe.getIngredients()));
            return Optional.of(repository.save(recipe));
        }
        return Optional.empty();
    }

    private Set<Ingredient> fetchIngredients(Set<Ingredient> ingredients) {
        return ingredients
            .stream()
            .map(ingredient -> ingredientsRepository
                .findByName(ingredient.getName())
                .orElse(ingredient)
            )
            .collect(Collectors.toSet());
    }
}
