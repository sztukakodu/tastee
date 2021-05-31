package pl.sztukakodu.tastee.recipes.app;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sztukakodu.tastee.recipes.app.port.IngredientsPort;
import pl.sztukakodu.tastee.recipes.db.IngredientsRepository;
import pl.sztukakodu.tastee.recipes.domain.Ingredient;

import java.util.List;

@AllArgsConstructor
@Service
class IngredientsService implements IngredientsPort {
    private final IngredientsRepository repository;

    @Override
    public List<Ingredient> fetchAll() {
        return repository.findAll();
    }
}
