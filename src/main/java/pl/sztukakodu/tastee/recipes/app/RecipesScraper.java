package pl.sztukakodu.tastee.recipes.app;

import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.sztukakodu.tastee.recipes.app.port.GenerateRecipesPort;

@Component
@AllArgsConstructor
@Slf4j
class RecipesScraper {
    private final GenerateRecipesPort generateRecipes;

    @SneakyThrows
    @Scheduled(fixedDelay = 60_000)
    @Timed(
        value = "internal_jobs",
        extraTags = {"job", "recipes_scraping"},
        longTask = true
    )
    public void scrapeRecipes() {
        log.info("Scraping recipes start...");
        Thread.sleep(60_000);
        for (int i = 0; i < 10; i++) {
            int generated = generateRecipes.generate(1);
            if(generated > 0) {
                log.info("Generated a recipe");
                break;
            } else {
                log.info("Failed to generate a recipe ({})", i);
                Thread.sleep(10_000);
            }
        }
        log.info("Scraping recipes finished.");
    }

}
