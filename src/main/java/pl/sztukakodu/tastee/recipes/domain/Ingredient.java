package pl.sztukakodu.tastee.recipes.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "uuid")
public class Ingredient {

    @Id
    @GeneratedValue
    private Long id;

    private final String uuid = UUID.randomUUID().toString();

    @Column(unique = true)
    private String name;

    public Ingredient(String name) {
        this.name = name;
    }
}
