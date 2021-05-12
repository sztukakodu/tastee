package pl.sztukakodu.tastee.recipes.web;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sztukakodu.tastee.recipes.app.port.ReadRecipesPort;
import pl.sztukakodu.tastee.recipes.app.port.WriteRecipesPort;
import pl.sztukakodu.tastee.recipes.app.port.WriteRecipesPort.AddRecipeCommand;
import pl.sztukakodu.tastee.recipes.domain.Recipe;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/recipes")
class RecipesController {

    private final ReadRecipesPort readRecipes;
    private final WriteRecipesPort writeRecipes;

    @PostMapping("/_search")
    public List<Recipe> search() {
        return readRecipes.search();
    }

    @GetMapping
    public Page<Recipe> getAll(Pageable pageable) {
        return readRecipes.getPage(pageable);
    }

    @GetMapping("/{id}")
    public Optional<Recipe> recipeById(@PathVariable String id) {
        return readRecipes.getRecipeById(id);
    }

    @PostMapping
    public ResponseEntity<Void> addRecipe(@RequestBody AddRecipeCommand command) {
        String id = writeRecipes.addRecipe(command);
        return ResponseEntity
            .created(URI.create("/" + id))
            .build();
    }
}
