package aoc.snowrescuemission;

import aoc.utils.ChallengeDay;
import org.hzt.utils.collections.ListX;
import org.hzt.utils.io.FileX;
import org.hzt.utils.strings.StringX;

import java.util.List;

import static java.lang.Math.max;

public final class Day02 implements ChallengeDay {

    private final ListX<Game> games;

    public Day02(String fileName) {
        this.games = FileX.of(fileName).useLines(s -> s.map(Game::parse).toListX());
    }

    public Day02(ListX<String> lines) {
        this.games = lines.map(Game::parse);
    }

    @Override
    public Long part1() {
        return games
                .filter(Game::isPossible)
                .longSumOf(Game::id);
    }

    @Override
    public Long part2() {
        return games.longSumOf(Game::toPowerFewestCubes);
    }

    record Game(int id, List<String> rounds) {

        public int toPowerFewestCubes() {
            int redCount = 1;
            int blueCount = 1;
            int greenCount = 1;
            for (final var round : rounds) {
                final var actions = toGameActions(round);
                for (final var action : actions) {
                    final var count = Integer.parseInt(action.first());
                    final var color = action.last();
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
                final var actions = toGameActions(round);
                for (final var action : actions) {
                    final var count = Integer.parseInt(action.first());
                    final var color = action.last();
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

        private static ListX<ListX<String>> toGameActions(String round) {
            return StringX.of(round).split(", ")
                    .map(a -> StringX.of(a).split(" "));
        }

        private static Game parse(String line) {
            final var split = StringX.of(line).split(": ");
            final var id = Integer.parseInt(split.first().substring("Game ".length()));
            final var subSets = StringX.of(split.last()).split("; ").toList();

            return new Game(id, subSets);
        }
    }
}
