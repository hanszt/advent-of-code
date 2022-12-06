package aoc;

import aoc.utils.FileUtils;
import org.hzt.utils.collections.MutableSetX;
import org.hzt.utils.sequences.Sequence;
import org.hzt.utils.strings.StringX;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

/**
 * @see <a href="https://adventofcode.com/2022/day/3">Day 3: Rucksack reorganisation</a>
 */
public class Day03RucksackReorganisation implements ChallengeDay {

    private final List<String> rucksacks;

    public Day03RucksackReorganisation(String inputFile) {
        this.rucksacks = FileUtils.useLines(Path.of(inputFile), Stream::toList);
    }

    @NotNull
    @Override
    public Integer part1() {
        return rucksacks.stream()
                .mapToInt(Day03RucksackReorganisation::findDuplicatedItem)
                .sum();
    }

    private static int findDuplicatedItem(String content) {
        final var half = content.length() / 2;
        final var sack1 = StringX.of(content.substring(0, half)).toMutableSet();
        final var sack2 = StringX.of(content.substring(half)).toMutableSet();
        sack1.retainAll(sack2);
        return toPriority(sack1.single());
    }

    static int toPriority(char c) {
        return Character.isUpperCase(c) ? (c - 38) : (c - 96);
    }

    @NotNull
    @Override
    public Long part2() {
        return Sequence.of(rucksacks)
                .map(StringX::of)
                .map(StringX::toMutableSet)
                .chunked(3)
                .map(chunk -> chunk.reduce(MutableSetX::intersect).orElseThrow().single())
                .mapToInt(Day03RucksackReorganisation::toPriority)
                .sum();
    }
}
