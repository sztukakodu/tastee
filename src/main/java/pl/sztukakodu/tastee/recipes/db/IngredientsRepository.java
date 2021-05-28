package pl.sztukakodu.tastee.recipes.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sztukakodu.tastee.recipes.domain.Ingredient;

import java.util.Optional;

public interface IngredientsRepository extends JpaRepository<Ingredient, Long> {
    Optional<Ingredient> findByName(String name);
}
