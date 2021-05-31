package pl.sztukakodu.tastee.recipes.app.port;

import pl.sztukakodu.tastee.recipes.domain.Ingredient;

import java.util.List;

public interface IngredientsPort {
    List<Ingredient> fetchAll();
}
