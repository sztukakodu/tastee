package pl.sztukakodu.tastee.recipes.app;

import org.springframework.stereotype.Service;
import pl.sztukakodu.tastee.recipes.app.port.ReadRecipesPort;
import pl.sztukakodu.tastee.recipes.app.port.WriteRecipesPort;
import pl.sztukakodu.tastee.recipes.domain.Ingredient;
import pl.sztukakodu.tastee.recipes.domain.Recipe;

import java.util.List;

@Service
class RecipesService implements ReadRecipesPort, WriteRecipesPort {

    @Override
    public List<Recipe> search() {
        return List.of(
            new Recipe(
                "42",
                "Szakszuka",
                "Umyj wszystko",
                List.of(new Ingredient("jajka"))
            )
        );
    }

}
