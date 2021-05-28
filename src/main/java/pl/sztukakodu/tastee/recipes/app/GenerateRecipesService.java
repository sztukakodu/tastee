package pl.sztukakodu.tastee.recipes.app;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import pl.sztukakodu.tastee.recipes.app.port.GenerateRecipesPort;
import pl.sztukakodu.tastee.recipes.db.IngredientsRepository;
import pl.sztukakodu.tastee.recipes.db.RecipesRepository;
import pl.sztukakodu.tastee.recipes.domain.Ingredient;
import pl.sztukakodu.tastee.recipes.domain.Recipe;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
class GenerateRecipesService implements GenerateRecipesPort {
    private final RecipesRepository repository;
    private final IngredientsRepository ingredientsRepository;
    private final RecipesProvider provider;

    @Override
    @Transactional
    public void generate(int size) {
        for (int i = 0; i < size; i++) {
            Recipe recipe = provider.newRecipe();
            if (!repository.existsByTitle(recipe.getTitle())) {
                recipe.setIngredients(fetchIngredients(recipe.getIngredients()));
                repository.save(recipe);
            }
        }
    }

    @NotNull
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
