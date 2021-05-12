package pl.sztukakodu.tastee.recipes.db;

import org.springframework.stereotype.Repository;
import pl.sztukakodu.tastee.recipes.domain.Recipe;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
class MemoryRecipesRepository implements RecipesRepository {

    private final Map<String, Recipe> storage = new ConcurrentHashMap<>();

    @Override
    public List<Recipe> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Recipe> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public String save(Recipe recipe) {
        String id = UUID.randomUUID().toString();
        storage.put(id, recipe);
        return id;
    }
}
