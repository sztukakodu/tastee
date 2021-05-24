package pl.sztukakodu.tastee.recipes.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Recipe {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String steps;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "recipe_id")
    private Set<IngredientItem> ingredients;

    public Recipe(String title, String steps, Set<IngredientItem> ingredients) {
        this.title = title;
        this.steps = steps;
        this.ingredients = ingredients;
    }
}
