package pl.sztukakodu.tastee.recipes.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sztukakodu.tastee.recipes.app.port.IngredientsPort;
import pl.sztukakodu.tastee.recipes.domain.Ingredient;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/ingredients")
@Slf4j
class IngredientsController {
    private final IngredientsPort ingredientsPort;

    @GetMapping
    public List<String> getAll() {
        return ingredientsPort
            .fetchAll()
            .stream()
            .map(Ingredient::getName)
            .collect(Collectors.toList());
    }
}
