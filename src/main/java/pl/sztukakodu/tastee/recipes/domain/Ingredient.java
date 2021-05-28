package pl.sztukakodu.tastee.recipes.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    public Ingredient(String name) {
        this.name = name;
    }
}
