package aoc;

import org.hzt.utils.io.FileX;
import org.hzt.utils.sequences.Sequence;
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
        return Sequence.of(foods).maxOf(Day01CalorieCounting::sumCalories);
    }

    @Override
    public @NotNull Long part2() {
        return Sequence.of(foods)
                .mapToInt(Day01CalorieCounting::sumCalories)
                .sortedDescending()
                .take(3L)
                .sum();
    }

    private static int sumCalories(String calories) {
        return calories.lines()
                .mapToInt(Integer::parseInt)
                .sum();
    }
}
