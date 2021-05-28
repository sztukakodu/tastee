package pl.sztukakodu.tastee.recipes.app;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import org.springframework.stereotype.Component;
import pl.sztukakodu.tastee.recipes.domain.Ingredient;
import pl.sztukakodu.tastee.recipes.domain.Recipe;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
class RecipesProvider {

    private final SecureRandom random = new SecureRandom();
    private final Locale locale = Locale.forLanguageTag("pl");
    private final Faker faker = new Faker(locale);
    private final FakeValuesService fakeValuesService;

    public RecipesProvider() {
        fakeValuesService = new FakeValuesService(locale, faker.random());
    }

    public Recipe newRecipe() {
        return new Recipe(
            title(),
            description(),
            ingredients()
        );
    }

    private String description() {
        return fakeValuesService.resolve("food.descriptions", null, faker);
    }

    private String title() {
        return faker.food().dish();
    }


    private Set<Ingredient> ingredients() {
        int size = random.nextInt(5) + 5;
        return IntStream.range(0, size)
                        .mapToObj(x -> new Ingredient(faker.food().ingredient()))
                        .collect(Collectors.toSet());
    }
}
