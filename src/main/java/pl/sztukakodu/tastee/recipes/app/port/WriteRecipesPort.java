package pl.sztukakodu.tastee.recipes.app.port;

import lombok.Value;
import pl.sztukakodu.tastee.recipes.domain.Ingredient;

import java.util.Set;

public interface WriteRecipesPort {

    Long addRecipe(AddRecipeCommand command);

    @Value
    class AddRecipeCommand {
        String title;
        String steps;
        Set<Ingredient> ingredients;
    }
}
