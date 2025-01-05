package aoc.snowrescuemission;

import aoc.utils.grid2d.GridPoint2D;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

import static aoc.utils.grid2d.Grid2DUtilsKt.firstPoint;
import static aoc.utils.grid2d.Grid2DUtilsKt.getOrNull;
import static aoc.utils.grid2d.GridPoint2DExtensionsKt.orthoNeighbors;

/**
 * Source: <a href="https://github.com/zebalu/advent-of-code-2023.git">Zebalu aoc repo</a>
 * <p>
 * Refactored
 * <p>
 * Based on the formula from: <a href="https://github.com/abnew123/aoc2023/blob/main/src/solutions/Day21.java">Solution day 21</a>
 */
public final class Day21Zebalu {

    private final List<String> maze;
    private final GridPoint2D start;

    public Day21Zebalu(final List<String> maze) {
        this.maze = maze;
        this.start = firstPoint(maze, p -> p == 'S');
    }

    public long part1(final int maxSteps) {
        return withDeltaTracking(maxSteps, p -> getOrNull(maze, p) instanceof final Character ch && ch != '#');
    }

    public long part2(final int maxSteps) {
        return withDeltaTracking(maxSteps, p -> extractInfinite(p) != '#');
    }

    private long withDeltaTracking(final int maxSteps, final Predicate<GridPoint2D> isPath) {
        final var getOutCount = stepsToGetOut(start);
        final var reached = new HashSet<>();
        final var totals = new ArrayList<Long>();
        final var deltas = new ArrayList<Long>();
        final var deltaDeltas = new ArrayList<Long>();
        reached.add(start);
        var todo = List.of(start);
        var filled = false;
        var totalReached = maxSteps % 2 == 0 ? 1L : 0L;
        var index = 0;
        while (index < maxSteps && !filled) {
            ++index;
            todo = todo.stream().flatMap(c -> orthoNeighbors(c).stream())
                    .filter(c -> isPath.test(c) && !reached.contains(c))
                    .peek(reached::add)
                    .toList();
            if (index % 2 == maxSteps % 2) {
                totalReached += todo.size();
                if ((index % maze.size()) == getOutCount) {
                    totals.add(totalReached);
                    if (totals.size() > 1) {
                        deltas.add(totals.getLast() - totals.get(totals.size() - 2));
                    }
                    if (deltas.size() > 1) {
                        deltaDeltas.add(deltas.getLast() - deltas.get(deltas.size() - 2));
                    }
                    if (deltaDeltas.size() > 1) {
                        filled = true;
                    }
                }
            }
        }
        if (filled) {
            final var neededLoopCount = maxSteps / (maze.size() * 2L) - 1;
            final var currentLoopCount = index / (maze.size() * 2L) - 1;
            final var deltaLoopCount = neededLoopCount - currentLoopCount;
            final var deltaLoopCountTriangular = (neededLoopCount * (neededLoopCount + 1)) / 2
                    - (currentLoopCount * (currentLoopCount + 1)) / 2;
            final long deltaDelta = deltaDeltas.getLast();
            final long initialDelta = deltas.getFirst();
            return deltaDelta * deltaLoopCountTriangular + initialDelta * deltaLoopCount + totalReached;
        }
        return totalReached;
    }

    private long stepsToGetOut(final GridPoint2D start) {
        var count = 0L;
        var available = new HashSet<GridPoint2D>();
        var nextAvailable = new HashSet<GridPoint2D>();
        available.add(start);
        while (available.stream().noneMatch(p -> p.getY() == 0)) {
            for (final var n : available) {
                for (final var gridPoint2D : orthoNeighbors(n)) {
                    if (getOrNull(maze, gridPoint2D) != null) {
                        nextAvailable.add(gridPoint2D);
                    }
                }
            }
            available = nextAvailable;
            nextAvailable = new HashSet<>();
            ++count;
        }
        return count;
    }

    private char extractInfinite(final GridPoint2D p) {
        final var row = maze.getFirst();
        final var height = maze.size();
        final var width = row.length();
        final var fy = ((p.getY() % height) + height) % height;
        final var fx = ((p.getX() % width) + width) % width;
        return maze.get(fy).charAt(fx);
    }
}
