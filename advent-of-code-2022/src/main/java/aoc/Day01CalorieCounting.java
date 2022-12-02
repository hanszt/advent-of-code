package aoc;

import org.jetbrains.annotations.NotNull;
import utils.AocUtilsKt;
import utils.FileUtils;

import java.nio.file.Path;
import java.util.List;

/**
 * @see <a href="https://adventofcode.com/2021/day/1">Day 1: Sonar Sweep</a>
 */
public class Day01CalorieCounting implements ChallengeDay {

    private final List<String> foods;

    public Day01CalorieCounting(String fileName) {
        this.foods = AocUtilsKt.splitByBlankLine(FileUtils.readToString(Path.of(fileName)));
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
