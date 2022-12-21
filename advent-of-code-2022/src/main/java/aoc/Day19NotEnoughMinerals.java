package aoc;

import org.hzt.utils.io.FileX;
import org.hzt.utils.sequences.Sequence;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Credits to Elizarov
 *
 * @see <a href="https://adventofcode.com/2022/day/19">Day 19</a>
 */
public class Day19NotEnoughMinerals implements ChallengeDay {

    private final List<String> lines;

    public Day19NotEnoughMinerals(String fileName) {
        lines = FileX.of(fileName).useLines(Sequence::toList);
    }

    @NotNull
    @Override
    public Integer part1() {
        return Day19ElizarovKt.day19Part(lines, 1);
    }

    @NotNull
    @Override
    public Integer part2() {
        return Day19ElizarovKt.day19Part(lines, 2);
    }
}
