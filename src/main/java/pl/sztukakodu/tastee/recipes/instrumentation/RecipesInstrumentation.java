package pl.sztukakodu.tastee.recipes.instrumentation;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import pl.sztukakodu.tastee.recipes.db.IngredientsRepository;
import pl.sztukakodu.tastee.recipes.db.RecipesRepository;

import javax.annotation.PostConstruct;
import java.util.function.ToDoubleFunction;

@Component
@AllArgsConstructor
class RecipesInstrumentation {
    private final MeterRegistry registry;
    private final RecipesRepository recipesRepository;
    private final IngredientsRepository ingredientsRepository;

    @PostConstruct
    public void init() {
        Gauge
            .builder("entities.size", recipesRepository, CrudRepository::count)
            .description("number of entities stored in the system")
            .tag("type", "recipe")
            .register(registry);
        Gauge
            .builder("entities.size", ingredientsRepository, CrudRepository::count)
            .description("number of entities stored in the system")
            .tag("type", "ingredient")
            .register(registry);
    }
}
