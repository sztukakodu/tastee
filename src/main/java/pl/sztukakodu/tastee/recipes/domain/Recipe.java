package pl.sztukakodu.tastee.recipes.domain;

import lombok.Value;

import java.util.List;

@Value
public class Recipe {
    String id;
    String title;
    String steps;
    List<Ingredient> ingredients;
}
