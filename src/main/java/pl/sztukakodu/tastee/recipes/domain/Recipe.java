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

    @Column(unique = true)
    private String title;

    private String steps;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "recipe_ingredient")
    private Set<Ingredient> ingredients;

    public Recipe(String title, String steps, Set<Ingredient> ingredients) {
        this.title = title;
        this.steps = steps;
        this.ingredients = ingredients;
    }
}
