package hzt.aoc.day21;

import hzt.aoc.Challenge;

import java.util.*;
import java.util.stream.Collectors;

// credits to Johan de Jong
public abstract class Day21Challenge extends Challenge {

    Day21Challenge(final String challengeTitle, final String description) {
        super(challengeTitle, description, "20201221-input-day21.txt");
    }

    @Override
    protected String solve(final List<String> inputList) {
        final List<Food> foods = inputList.stream().map(this::parseLine).collect(Collectors.toList());
        return calculateAnswer(foods);
    }

    protected abstract String calculateAnswer(List<Food> idsToIngredientsAndAllergens);

    private static final String ONE_OR_MORE_SPACES = "\\s+";

    private Food parseLine(final String line) {
        final String[] ingredientsToAllergens = line.split("contains");
        final String[] ingredientsAsArray = ingredientsToAllergens[0]
                .replace("(", "").strip().split(ONE_OR_MORE_SPACES);
        final String[] allergensAsArray = ingredientsToAllergens[1].replace(")", "")
                .replace(" ", "").strip().split(",");
        return new Food(Set.of(ingredientsAsArray), Set.of(allergensAsArray));
    }

    Result extractAllergens(final Set<String> allAllergens, final List<Food> foods) {
        final Set<String> potentialAllergenIngredients = new HashSet<>();
        final Map<String, List<String>> allergenToIngredientsMap = new HashMap<>();
        for (final String allergen : allAllergens) {
            final List<Food> foodsWithAllergen = extractFoodsWithAllergen(allergen, foods);
            final Set<String> allPossibleIngredients = allPossibleIngredientsContainingAllergen(foodsWithAllergen);
            for (final String ingredient : allPossibleIngredients) {
                final boolean inAllFoods = foodsWithAllergen.stream().allMatch(food -> food.getIngredients().contains(ingredient));
                if (inAllFoods) {
                    potentialAllergenIngredients.add(ingredient);
                    allergenToIngredientsMap.computeIfAbsent(allergen, key -> new ArrayList<>()).add(ingredient);
                }
            }
        }
        return new Result(potentialAllergenIngredients, allergenToIngredientsMap);
    }

    private List<Food> extractFoodsWithAllergen(final String allergen, final List<Food> foods) {
        return foods.stream()
                .filter(food -> food.getAllergens().contains(allergen))
                .collect(Collectors.toList());
    }

    private Set<String> allPossibleIngredientsContainingAllergen(final List<Food> foodsWithAllergen) {
        return foodsWithAllergen.stream()
                .flatMap(food -> food.getIngredients().stream())
                .collect(Collectors.toSet());
    }

    Set<String> extractAllAllergens(final List<Food> foods) {
        return foods.stream()
                .flatMap(line -> line.getAllergens().stream())
                .collect(Collectors.toSet());
    }

    static class Result {

        private final Set<String> potentialAllergenIngredients;
        private final Map<String, List<String>> allergenToIngredientsMap;

        public Result(final Set<String> potentialAllergenIngredients, final Map<String, List<String>> allergenToIngredientsMap) {
            this.potentialAllergenIngredients = potentialAllergenIngredients;
            this.allergenToIngredientsMap = allergenToIngredientsMap;
        }

        public Set<String> getPotentialAllergenIngredients() {
            return Collections.unmodifiableSet(potentialAllergenIngredients);
        }

        public Map<String, List<String>> getAllergenToIngredientsMap() {
            return Collections.unmodifiableMap(allergenToIngredientsMap);
        }
    }
}
