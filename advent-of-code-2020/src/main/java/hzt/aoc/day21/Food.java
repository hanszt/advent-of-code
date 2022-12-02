package hzt.aoc.day21;

import java.util.Collections;
import java.util.Set;

public class Food {

    private final Set<String> ingredients;
    private final Set<String> allergens;

    public Food(final Set<String> ingredients, final Set<String> allergens) {
        this.ingredients = ingredients;
        this.allergens = allergens;
    }

    public Set<String> getIngredients() {
        return Collections.unmodifiableSet(ingredients);
    }

    public Set<String> getAllergens() {
        return Collections.unmodifiableSet(allergens);
    }

    @Override
    public String toString() {
        return "Food{" +
                "ingredients=" + ingredients +
                ", allergens=" + allergens +
                '}';
    }
}
