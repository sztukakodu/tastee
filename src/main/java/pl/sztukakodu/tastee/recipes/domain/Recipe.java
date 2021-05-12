package pl.sztukakodu.tastee.recipes.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class Recipe {
    private String id;
    private String title;
    private String steps;
    private Set<Ingredient> ingredients;

    public Recipe(String title, String steps, Set<Ingredient> ingredients) {
        this.title = title;
        this.steps = steps;
        this.ingredients = ingredients;
    }
}
