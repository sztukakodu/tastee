package pl.sztukakodu.tastee.recipes.app;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.binder.cache.CaffeineCacheMetrics;
import org.springframework.stereotype.Service;
import pl.sztukakodu.tastee.recipes.app.port.SearchRecipesPort;
import pl.sztukakodu.tastee.recipes.db.IngredientsRepository;
import pl.sztukakodu.tastee.recipes.db.RecipesRepository;
import pl.sztukakodu.tastee.recipes.domain.Ingredient;
import pl.sztukakodu.tastee.recipes.domain.Recipe;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
class SearchRecipesService implements SearchRecipesPort {
    private final Cache<Set<String>, List<Recipe>> cache;
    private final RecipesRepository repository;
    private final IngredientsRepository ingredientsRepository;
    private final Timer timer;

    public SearchRecipesService(MeterRegistry registry, RecipesRepository repository, IngredientsRepository ingredientsRepository) {
        this.repository = repository;
        this.ingredientsRepository = ingredientsRepository;
        // cache
        this.cache = Caffeine
            .newBuilder()
            .expireAfterAccess(1, TimeUnit.MINUTES)
            .maximumSize(10)
            .recordStats()
            .build();
        CaffeineCacheMetrics.monitor(registry, cache, "search");
        // timer
        timer = Timer.builder("search.fetching")
                     .description("time required to fetch recipes from database on search")
                     .register(registry);
    }

    @Override
    public List<Recipe> search(Set<String> values) {
        return cache.get(values, this::fetchFromDatabase);
    }

    // TODO-Darek: consider @Timed (https://micrometer.io/docs/concepts#_the_timed_annotation)
    private List<Recipe> fetchFromDatabase(Set<String> values) {
        return timer.record(() -> {
            // find all containing at least 1 ingredient
            Set<Ingredient> ingredients = values
                .stream()
                .map(ingredientsRepository::findByName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

            // filter only matching
            return repository.findAllByIngredientsIn(ingredients)
                             .stream()
                             .filter(recipe -> recipe.containsAllIngredients(ingredients))
                             .collect(Collectors.toList());
        });
    }
}
