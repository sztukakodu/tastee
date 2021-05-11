package pl.sztukakodu.tastee.recipes.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.sztukakodu.tastee.recipes.app.port.ReadRecipesPort;
import pl.sztukakodu.tastee.recipes.domain.Recipe;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/recipes")
class RecipesController {

    private final ReadRecipesPort readRecipes;

    @PostMapping("/_search")
    public List<Recipe> search() {
        return readRecipes.search();
    }

    @GetMapping("/{id}")
    public void recipeById(@PathVariable String id) {

    }

    @PutMapping
    public void addRecipe() {

    }

}
