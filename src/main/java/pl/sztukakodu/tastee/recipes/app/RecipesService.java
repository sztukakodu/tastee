package pl.sztukakodu.tastee.recipes.app;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.sztukakodu.tastee.recipes.app.port.ReadRecipesPort;
import pl.sztukakodu.tastee.recipes.app.port.WriteRecipesPort;
import pl.sztukakodu.tastee.recipes.db.RecipesRepository;
import pl.sztukakodu.tastee.recipes.domain.Recipe;

import java.util.List;
import java.util.Optional;

@CacheConfig(cacheNames = "recipe")
@Service
@AllArgsConstructor
class RecipesService implements ReadRecipesPort, WriteRecipesPort {

    private final RecipesRepository repository;

    @Override
    public List<Recipe> search() {
        return repository.findAll();
    }

    @Cacheable
    @Override
    public Optional<Recipe> getRecipeById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Page<Recipe> getPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Long addRecipe(AddRecipeCommand command) {
        Recipe newRecipe = new Recipe(
            command.getTitle(),
            command.getSteps(),
            command.getIngredients()
        );
        Recipe saved = repository.save(newRecipe);
        return saved.getId();
    }
}
