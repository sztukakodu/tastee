package pl.sztukakodu.tastee.recipes.app;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.sztukakodu.tastee.recipes.app.port.ReadRecipesPort;
import pl.sztukakodu.tastee.recipes.app.port.WriteRecipesPort;
import pl.sztukakodu.tastee.recipes.db.RecipesRepository;
import pl.sztukakodu.tastee.recipes.domain.Ingredient;
import pl.sztukakodu.tastee.recipes.domain.Recipe;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class RecipesService implements ReadRecipesPort, WriteRecipesPort {

    private final RecipesRepository repository;

    @Override
    public List<Recipe> search() {
        return repository.findAll();
    }

    @Override
    public Optional<Recipe> getRecipeById(String id) {
        return repository.findById(id);
    }

    @Override
    public Page<Recipe> getPage(Pageable pageable) {
        return new PageImpl<>(repository.findAll());
    }

    @Override
    public String addRecipe(AddRecipeCommand command) {
        Recipe recipe = new Recipe(
            command.getTitle(),
            command.getSteps(),
            command.getIngredients()
                   .stream()
                   .map(Ingredient::new)
                   .collect(Collectors.toSet())
        );
        return repository.save(recipe);
    }
}
