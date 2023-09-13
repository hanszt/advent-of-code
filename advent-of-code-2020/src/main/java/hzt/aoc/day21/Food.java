package hzt.aoc.day21;

import java.util.Set;
import java.util.stream.Stream;

public record Food(Set<String> ingredientSet, Set<String> allergenSet) {

    public Set<String> ingredientSet() {
        return Set.copyOf(ingredientSet);
    }

    public Stream<String> ingredients() {
        return ingredientSet.stream();
    }

    public Set<String> allergenSet() {
        return Set.copyOf(allergenSet);
    }

    public Stream<String> allergens() {
        return allergenSet.stream();
    }
}
