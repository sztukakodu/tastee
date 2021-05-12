package pl.sztukakodu.tastee.recipes.app.port;

import lombok.Value;

import java.util.Set;

public interface WriteRecipesPort {

    String addRecipe(AddRecipeCommand command);

    @Value
    class AddRecipeCommand {
        String title;
        String steps;
        Set<String> ingredients;
    }
}
