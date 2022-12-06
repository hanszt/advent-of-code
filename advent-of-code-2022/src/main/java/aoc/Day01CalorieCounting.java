package aoc;

import org.hzt.utils.io.FileX;
import org.jetbrains.annotations.NotNull;
import aoc.utils.AocUtilsKt;

import java.util.List;

/**
 * @see <a href="https://adventofcode.com/2022/day/1">Day 1: Calorie counting</a>
 */
public class Day01CalorieCounting implements ChallengeDay {

    private final List<String> foods;

    public Day01CalorieCounting(String fileName) {
        this.foods = AocUtilsKt.splitByBlankLine(FileX.of(fileName).readText());
    }

    @Override
    public @NotNull Integer part1() {
        return foods.stream()
                .mapToInt(Day01CalorieCounting::sumCalories)
                .max()
                .orElseThrow();
    }

    @Override
    public @NotNull Integer part2() {
        return foods.stream()
                .mapToInt(Day01CalorieCounting::sumCalories)
                .sorted()
                .skip(foods.size() - 3L)
                .sum();
    }

    private static int sumCalories(String calories) {
        return calories.lines()
                .mapToInt(Integer::parseInt)
                .sum();
    }
}
