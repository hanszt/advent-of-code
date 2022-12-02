package hzt.aoc.day21;

import java.util.List;
import java.util.Set;

// credits to Johan de Jong
public class Part1AllergenAssessment extends Day21Challenge {

    public Part1AllergenAssessment() {
        super("part 1",
                "Determine which ingredients cannot possibly contain any of the allergens in your list. " +
                        "How many times do any of those ingredients appear?");
    }


    @Override
    protected String calculateAnswer(final List<Food> foods) {
        final Set<String> allAllergens = extractAllAllergens(foods);
        final Set<String> potentialAllergenIngredients = extractAllergens(allAllergens, foods)
                .getPotentialAllergenIngredients();

        return String.valueOf(countIngredientsWithoutAllergens(potentialAllergenIngredients, foods));
    }

    private static long countIngredientsWithoutAllergens(final Set<String> potentialAllergenIngredients, final List<Food> foods) {
        long count = 0;
        for (final Food food : foods) {
            for (final String ingredient : food.getIngredients()) {
                if (!potentialAllergenIngredients.contains(ingredient)) {
                    count++;
                }
            }
        }
        return count;
    }


    @Override
    protected String getMessage(final String global) {
        return String.format("%s", global);
    }

}
