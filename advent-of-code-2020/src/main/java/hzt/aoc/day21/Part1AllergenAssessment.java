package hzt.aoc.day21;

import java.util.List;
import java.util.Set;

import static java.util.function.Predicate.not;

// credits to Johan de Jong
public class Part1AllergenAssessment extends Day21Challenge {

    public Part1AllergenAssessment() {
        super("part 1",
                "Determine which ingredients cannot possibly contain any of the allergenSet in your list. " +
                        "How many times do any of those ingredients appear?");
    }

    @Override
    protected String calculateAnswer(final List<Food> foods) {
        final var allAllergens = extractAllAllergens(foods);
        final var potentialAllergenIngredients = extractAllergens(allAllergens, foods)
                .potentialAllergenIngredients();

        return String.valueOf(countIngredientsWithoutAllergens(potentialAllergenIngredients, foods));
    }

    private static long countIngredientsWithoutAllergens(final Set<String> potentialAllergenIngredients, final List<Food> foods) {
        return foods.stream()
                .flatMap(Food::ingredients)
                .filter(not(potentialAllergenIngredients::contains))
                .count();
    }

    @Override
    protected String getMessage(final String global) {
        return String.format("%s", global);
    }

}
