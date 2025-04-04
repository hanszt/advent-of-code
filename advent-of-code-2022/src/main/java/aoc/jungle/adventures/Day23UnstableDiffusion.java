package aoc.jungle.adventures;

import aoc.utils.ChallengeDay;
import aoc.utils.grid2d.GridPoint2D;
import org.hzt.utils.collections.ListX;
import org.hzt.utils.io.FileX;
import org.hzt.utils.sequences.Sequence;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static aoc.utils.grid2d.GridPoint2D.*;
import static aoc.utils.grid2d.GridPoint2DKt.GridPoint2D;

/**
 * Credits to Angelo
 *
 * @see <a href="https://adventofcode.com/2022/day/23">Day 23</a>
 */
public class Day23UnstableDiffusion implements ChallengeDay {

    private static final GridPoint2D[][] directions = {
            {north, northeast, northwest},
            {south, southeast, southwest},
            {west, northwest, southwest},
            {east, northeast, southeast}
    };

    private final ListX<String> lines;

    public Day23UnstableDiffusion(String fileName) {
        lines = FileX.of(fileName).readLines();
    }

    @NotNull
    @Override
    public Long part1() {
        final var positions = toInitElfPositions(lines);
        IntStream.range(0, 10).forEach(round -> simulateRound(positions, round));
        var minx = Integer.MAX_VALUE;
        var maxx = Integer.MIN_VALUE;
        var miny = Integer.MAX_VALUE;
        var maxy = Integer.MIN_VALUE;
        for (final var p : positions) {
            minx = Math.min(minx, p.getX());
            maxx = Math.max(maxx, p.getX());
            miny = Math.min(miny, p.getY());
            maxy = Math.max(maxy, p.getY());
        }
        return (maxx - minx + 1L) * (maxy - miny + 1L) - positions.size();
    }

    @NotNull
    @Override
    public Integer part2() {
        final var positions = toInitElfPositions(lines);
        var round = 0;
        while (simulateRound(positions, round)) {
            round++;
        }
        return round + 1;
    }

    private static Set<GridPoint2D> toInitElfPositions(ListX<String> lines) {
        final var positions = new HashSet<GridPoint2D>();
        for (var row = 0; row < lines.size(); row++) {
            final var line = lines.get(row);
            for (var col = 0; col < line.length(); col++) {
                if (line.charAt(col) == '#') {
                    positions.add(GridPoint2D(col, row));
                }
            }
        }
        return positions;
    }

    public boolean simulateRound(final Set<GridPoint2D> positions, final int round) {
        final var moveMap = new HashMap<GridPoint2D, List<GridPoint2D>>();
        for (var destination : positions) {
            var free = Sequence.of(kingDirs).map(destination::plus).none(positions::contains);
            // if free is still true, there were no elves in the 8 surrounding cells -> skip moving
            if (!free) {
                var goalGridPoint2D = destination;
                for (int i = 0; !free && i < 4; i++) {
                    final int index = (round + i) % 4;
                    goalGridPoint2D = destination.plus(directions[index][0]);
                    free = !positions.contains(goalGridPoint2D) &&
                           !positions.contains(destination.plus(directions[index][1])) &&
                           !positions.contains(destination.plus(directions[index][2]));
                }
                if (free) {
                    moveMap.computeIfAbsent(goalGridPoint2D, u -> new ArrayList<>()).add(destination);
                }
            }
        }
        // now the map has either a single value for a destination, or multiple, only move in the former case
        var moved = false;
        for (final var moves : moveMap.entrySet()) {
            final var position = moves.getKey();
            final var elfMoves = moves.getValue();
            if (elfMoves.size() == 1) { // execute move
                positions.remove(elfMoves.getFirst());
                positions.add(position);
                moved = true;
            }
        }
        return moved;
    }

}
