package pl.sztukakodu.tastee.recipes.instrumentation;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.sztukakodu.tastee.recipes.db.IngredientsRepository;
import pl.sztukakodu.tastee.recipes.db.RecipesRepository;

import javax.annotation.PostConstruct;

@Component
@AllArgsConstructor
class RecipesInstrumentation {
    private final RecipesRepository recipesRepository;
    private final IngredientsRepository ingredientsRepository;
    private final MeterRegistry registry;

    @PostConstruct
    public void init() {
        Gauge
            .builder("entities.size", recipesRepository::count)
            .description("number of entities stored in the system")
            .tag("type", "recipe")
            .register(registry);
        Gauge
            .builder("entities.size", ingredientsRepository::count)
            .description("number of entities stored in the system")
            .tag("type", "ingredient")
            .register(registry);
        Gauge
            .builder("entities.aggregations", recipesRepository::avgIngredientsPerRecipe)
            .description("entities aggregations")
            .tag("type", "avgIngredientsPerRecipe")
            .register(registry);
    }
}
