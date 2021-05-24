package pl.sztukakodu.tastee.recipes.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class IngredientItem {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String quantity;
}
