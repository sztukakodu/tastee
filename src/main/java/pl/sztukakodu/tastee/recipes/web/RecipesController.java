package pl.sztukakodu.tastee.recipes.web;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.sztukakodu.tastee.recipes.app.port.GenerateRecipes;
import pl.sztukakodu.tastee.recipes.app.port.ReadRecipes;
import pl.sztukakodu.tastee.recipes.app.port.SearchRecipes;
import pl.sztukakodu.tastee.recipes.app.port.WriteRecipes;
import pl.sztukakodu.tastee.recipes.app.port.WriteRecipes.AddRecipeCommand;
import pl.sztukakodu.tastee.recipes.domain.Recipe;

import java.net.URI;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@RestController
@RequestMapping("/recipes")
@Slf4j
class RecipesController {

    private final GenerateRecipes generateRecipes;
    private final SearchRecipes searchRecipes;
    private final ReadRecipes readRecipes;
    private final WriteRecipes writeRecipes;
    private final SecureRandom random = new SecureRandom();

    @PostMapping("/_search")
    public Page<Recipe> search(Pageable pageable, @RequestParam Set<String> ingredients) {
        return searchRecipes.search(pageable, ingredients);
    }

    @PostMapping("/_generate")
    public void generate(@RequestParam(defaultValue = "10") int size) {
        generateRecipes(size);
    }

    @SneakyThrows
    @GetMapping
    public Page<Recipe> getAll(Pageable pageable, @RequestParam(defaultValue = "false") boolean slow) {
        if (slow) {
            sleep();
        }
        return readRecipes.getPage(pageable);
    }

    @SneakyThrows
    private void sleep() {
        TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
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

    private void generateRecipes(int size) {
        for (int i = 0; i < size; i++) {
            try {
                generateRecipes.createNew();
            } catch (DataIntegrityViolationException e) {
                log.warn("Failed to create a recipe: {}", e.getMessage());
            }
        }
    }
}
