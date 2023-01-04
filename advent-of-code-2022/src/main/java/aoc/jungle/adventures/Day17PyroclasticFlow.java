package aoc.jungle.adventures;

import aoc.utils.ChallengeDay;
import org.hzt.utils.io.FileX;
import org.hzt.utils.iterables.Collectable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Credits to Elizarov
 *
 * @see <a href="https://adventofcode.com/2022/day/17">Day 17</a>
 */
public class Day17PyroclasticFlow implements ChallengeDay {

    private final List<String> lines;

    public Day17PyroclasticFlow(String fileName) {
        lines = FileX.of(fileName).useLines(Collectable::toList);
    }

    @NotNull
    @Override
    public Long part1() {
        return Day17ElizarovKt.towerHeightElizarov(2022, lines);
    }

    @NotNull
    @Override
    public Long part2() {
        return Day17ElizarovKt.towerHeightElizarov(1_000_000_000_000L, lines);
    }
}
