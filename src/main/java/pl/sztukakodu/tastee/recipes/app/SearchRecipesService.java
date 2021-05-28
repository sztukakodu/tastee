package pl.sztukakodu.tastee.recipes.app;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.cache.CaffeineCacheMetrics;
import org.springframework.stereotype.Service;
import pl.sztukakodu.tastee.recipes.app.port.SearchRecipesPort;
import pl.sztukakodu.tastee.recipes.db.RecipesRepository;
import pl.sztukakodu.tastee.recipes.domain.Recipe;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
class SearchRecipesService implements SearchRecipesPort {
    private final Cache<Set<String>, List<Recipe>> cache;
    private final RecipesRepository repository;

    public SearchRecipesService(RecipesRepository repository, MeterRegistry registry) {
        this.cache = Caffeine
            .newBuilder()
            .expireAfterAccess(1, TimeUnit.MINUTES)
            .maximumSize(10)
            .recordStats()
            .build();
        CaffeineCacheMetrics.monitor(registry, cache, "search");
        this.repository = repository;
    }

    @Override
    public List<Recipe> search(Set<String> values) {
        return cache.get(values, this::fetchFromDatabase);
    }

    private List<Recipe> fetchFromDatabase(Set<String> values) {
        // TODO-Darek: implement
        return Collections.emptyList();
    }
}
