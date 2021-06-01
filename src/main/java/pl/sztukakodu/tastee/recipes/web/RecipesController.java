package pl.sztukakodu.tastee.recipes.web;

import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sztukakodu.tastee.recipes.app.port.GenerateRecipesPort;
import pl.sztukakodu.tastee.recipes.app.port.ReadRecipesPort;
import pl.sztukakodu.tastee.recipes.app.port.SearchRecipesPort;
import pl.sztukakodu.tastee.recipes.app.port.WriteRecipesPort;
import pl.sztukakodu.tastee.recipes.app.port.WriteRecipesPort.AddRecipeCommand;
import pl.sztukakodu.tastee.recipes.domain.Recipe;

import java.net.URI;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/recipes")
@Slf4j
class RecipesController {

    private final GenerateRecipesPort generateRecipes;
    private final SearchRecipesPort searchRecipes;
    private final ReadRecipesPort readRecipes;
    private final WriteRecipesPort writeRecipes;
    private final MeterRegistry registry;
    private final SecureRandom random = new SecureRandom();

    @PostMapping("/_search")
    public Page<Recipe> search(Pageable pageable, @RequestParam Set<String> ingredients) {
        // number of ingredients
        DistributionSummary searchIngredients = DistributionSummary
            .builder("api_search_ingredients")
            .register(registry);
        searchIngredients.record(ingredients.size());
        // execute search
        Page<Recipe> search = searchRecipes.search(pageable, ingredients);

        // number of recipes
        DistributionSummary recipesTotalSize = DistributionSummary
            .builder("api_search_recipes_total_size")
            .publishPercentileHistogram(true)
            .minimumExpectedValue(1d)
            .maximumExpectedValue(1000d)
            .register(registry);
        recipesTotalSize.record(search.getTotalElements());
        return search;
    }

    @PostMapping("/_generate")
    public void generate(@RequestParam(defaultValue = "100") int size) {
        Timer.Sample sample = Timer.start(registry);
        int generated = generateRecipes.generate(size);
        Timer timer = Timer
            .builder("api_recipes_generate")
            .tag("count", String.valueOf(generated))
            .publishPercentileHistogram()
            .minimumExpectedValue(Duration.ofMillis(1))
            .maximumExpectedValue(Duration.ofSeconds(10))
            .register(registry);
        sample.stop(timer);
    }

    @SneakyThrows
    @GetMapping
    public Page<Recipe> getAll(Pageable pageable, @RequestParam(defaultValue = "false") boolean slow) {
        if (slow) {
            sleep();
        }
        return readRecipes.getPage(pageable);
    }

    private void sleep() throws InterruptedException {
        int sleepTime = random.nextInt(1000);
        if (sleepTime >= 750) {
            log.warn("I'm tired. Will answer in {} ms", sleepTime);
        } else {
            log.info("Will answer in {} ms", sleepTime);
        }
        Thread.sleep(sleepTime);
    }

    @GetMapping("/{id}")
    public Optional<Recipe> recipeById(@PathVariable Long id) {
        return readRecipes.getRecipeById(id)
                          .map(recipe -> {
                              Tag tag = Tag.of("success", "true");
                              registry.counter("api_recipe_get", List.of(tag)).increment();
                              return recipe;
                          }).or(() -> {
                registry.counter("api_recipe_get", "success", "false").increment();
                return Optional.empty();
            });

    }

    @PostMapping
    public ResponseEntity<Void> addRecipe(@RequestBody AddRecipeCommand command) {
        Long id = writeRecipes.addRecipe(command);
        return ResponseEntity
            .created(URI.create("/" + id))
            .build();
    }
}
