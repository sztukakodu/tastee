package pl.sztukakodu.tastee.recipes.web;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sztukakodu.tastee.recipes.app.port.GenerateRecipesPort;
import pl.sztukakodu.tastee.recipes.app.port.ReadRecipesPort;
import pl.sztukakodu.tastee.recipes.app.port.WriteRecipesPort;
import pl.sztukakodu.tastee.recipes.app.port.WriteRecipesPort.AddRecipeCommand;
import pl.sztukakodu.tastee.recipes.domain.Recipe;

import java.net.URI;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/recipes")
@Slf4j
class RecipesController {

    private final GenerateRecipesPort generateRecipes;
    private final ReadRecipesPort readRecipes;
    private final WriteRecipesPort writeRecipes;
    private final SecureRandom random = new SecureRandom();

    @PostMapping("/_generate")
    public void generate(@RequestParam(defaultValue = "100") int size) {
        generateRecipes.generate(size);
    }

    @PostMapping("/_search")
    public List<Recipe> search() {
        return readRecipes.search();
    }

    @SneakyThrows
    @GetMapping
    public Page<Recipe> getAll(Pageable pageable, @RequestParam(defaultValue = "false") boolean slow) {
        if(slow) {
            int sleepTime = random.nextInt(1000);
            if(sleepTime >= 750) {
                log.warn("I'm tired. Will answer in {} ms", sleepTime);
            } else {
                log.info("Will answer in {} ms", sleepTime);
            }
            Thread.sleep(sleepTime);
        }
        return readRecipes.getPage(pageable);
    }

    @GetMapping("/{id}")
    public Optional<Recipe> recipeById(@PathVariable Long id) {
        return readRecipes.getRecipeById(id);
    }

    @PostMapping
    public ResponseEntity<Void> addRecipe(@RequestBody AddRecipeCommand command) {
        Long id = writeRecipes.addRecipe(command);
        return ResponseEntity
            .created(URI.create("/" + id))
            .build();
    }
}
