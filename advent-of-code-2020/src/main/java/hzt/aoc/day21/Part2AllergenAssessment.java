package hzt.aoc.day21;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

// credits to Johan de Jong
public class Part2AllergenAssessment extends Day21Challenge {

    public Part2AllergenAssessment() {
        super("part 2",
                "Time to stock your raft with supplies. What is your canonical dangerous ingredient list?");
    }


    @Override
    protected String calculateAnswer(final List<Food> foods) {
        final var allAllergens = extractAllAllergens(foods);
        final var allergenToIngredientsMap = extractAllergens(allAllergens, foods).getAllergenToIngredientsMap();
        return getDangerousIngredientsListAsString(allergenToIngredientsMap);
    }

    private static String getDangerousIngredientsListAsString(final Map<String, List<String>> allergenToIngredientsMap) {
        final Map<String, String> uniqueAllergenToIngredientMap = new TreeMap<>();
        while (uniqueAllergenToIngredientMap.size() < allergenToIngredientsMap.size()) {
            extracted(allergenToIngredientsMap, uniqueAllergenToIngredientMap);
        }
        return String.join(",", uniqueAllergenToIngredientMap.values());
    }

    private static void extracted(final Map<String, List<String>> allergenToIngredientsMap,
                                  final Map<String, String> uniqueAllergenToIngredientMap) {
        for (final var entry : allergenToIngredientsMap.entrySet()) {
            final var ingredientsList = entry.getValue();
            if (ingredientsList.size() == 1) {
                final String ingredient = ingredientsList.get(0);
                uniqueAllergenToIngredientMap.put(entry.getKey(), ingredient);
                for (final List<String> ingredients : allergenToIngredientsMap.values()) {
                    ingredients.remove(ingredient);
                }
                break;
            }
        }
    }

    @Override
    protected String getMessage(final String global) {
        return String.format("%s", global);
    }
}
