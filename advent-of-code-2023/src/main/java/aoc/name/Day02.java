package aoc.name;

import aoc.utils.ChallengeDay;
import org.hzt.utils.collections.ListX;
import org.hzt.utils.io.FileX;
import org.hzt.utils.strings.StringX;

import java.util.List;

import static java.lang.Math.max;

public final class Day02 implements ChallengeDay {

    private final ListX<String> lines;

    public Day02(String fileName) {
        this(FileX.of(fileName).readLines());
    }

    public Day02(ListX<String> lines) {
        this.lines = lines;
    }

    record Game(int id, List<String> rounds) {

        public int toPowerFewestCubes() {
            int redCount = 1;
            int blueCount = 1;
            int greenCount = 1;
            for (final var round : rounds) {
                final var actions = StringX.of(round).split(", ")
                        .map(a -> StringX.of(a).split(" "));
                for (final var action : actions) {
                    final var color = action.last();
                    final var count = Integer.parseInt(action.first());
                    switch (color) {
                        case "red" -> redCount = max(count, redCount);
                        case "green" -> greenCount = max(count, greenCount);
                        case "blue" -> blueCount = max(count, blueCount);
                    }
                }
            }
            return redCount * blueCount * greenCount;
        }

        public boolean isPossible() {
            for (final var round : rounds) {
                int redCount = 0;
                int blueCount = 0;
                int greenCount = 0;
                final var actions = StringX.of(round).split(", ")
                        .map(a -> StringX.of(a).split(" "));
                for (final var action : actions) {
                    final var color = action.last();
                    final var count = Integer.parseInt(action.first());
                    switch (color) {
                        case "red" -> redCount += count;
                        case "green" -> greenCount += count;
                        case "blue" -> blueCount += count;
                    }
                    if (redCount > 12 || greenCount > 13 || blueCount > 14) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    @Override
    public Long part1() {
        return lines
                .map(Day02::toGame)
                .filter(Game::isPossible)
                .intSumOf(Game::id);
    }

    private static Game toGame(String line) {
        final var split = StringX.of(line).split(": ");
        final var id = Integer.parseInt(split.first().substring("Game ".length()));
        final var subSets = StringX.of(split.last()).split("; ").toList();

        return new Game(id, subSets);
    }

    public Long part2() {
        return lines
                .map(Day02::toGame)
                .longSumOf(Game::toPowerFewestCubes);
    }
}
