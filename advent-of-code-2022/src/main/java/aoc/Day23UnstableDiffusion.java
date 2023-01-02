package aoc;

import aoc.utils.model.GridPoint2D;
import org.hzt.utils.io.FileX;
import org.hzt.utils.sequences.Sequence;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static aoc.utils.model.GridPoint2D.*;
import static aoc.utils.model.GridPoint2D.kingDirs;
import static aoc.utils.model.GridPoint2DKt.gridPoint2D;

/**
 * Credits to Angelo
 *
 * @see <a href="https://adventofcode.com/2022/day/23">Day 23</a>
 */
public class Day23UnstableDiffusion implements ChallengeDay {

    private static final GridPoint2D[][] MOVES = {
            {north, northeast, northwest},
            {south, southeast, southwest},
            {west, northwest, southwest},
            {east, northeast, southeast}
    };

    private final List<String> lines;

    public Day23UnstableDiffusion(String fileName) {
        lines = FileX.of(fileName).useLines(Sequence::toList);
    }

    @NotNull
    @Override
    public Long part1() {
        final var elves = processInput(lines);
        for (int round = 0; round < 10; round++) {
            simulateRound(elves, round);
        }
        int minx = Integer.MAX_VALUE;
        int maxx = Integer.MIN_VALUE;
        int miny = Integer.MAX_VALUE;
        int maxy = Integer.MIN_VALUE;
        for (GridPoint2D p : elves) {
            minx = Math.min(minx, p.getX());
            maxx = Math.max(maxx, p.getX());
            miny = Math.min(miny, p.getY());
            maxy = Math.max(maxy, p.getY());
        }
        return (maxx - minx + 1L) * (maxy - miny + 1L) - elves.size();
    }

    @NotNull
    @Override
    public Integer part2() {
        final var elves = processInput(lines);
        int round = 0;
        while (simulateRound(elves, round) > 0) {
            round++;
        }
        return round + 1;
    }

    private static Set<GridPoint2D> processInput(List<String> lines) {
        Set<GridPoint2D> elves = new HashSet<>();
        for (int row = 0; row < lines.size(); row++) {
            String line = lines.get(row);
            for (int col = 0; col < line.length(); col++) {
                if (line.charAt(col) == '#') {
                    elves.add(gridPoint2D(col, row));
                }
            }
        }
        return elves;
    }

    public int simulateRound(Set<GridPoint2D> elves, int round) {
        Map<GridPoint2D, List<GridPoint2D[]>> moveMap = new HashMap<>();
        for (var elf : elves) {
            boolean free = Sequence.of(kingDirs).map(elf::plus).none(elves::contains);
            // if free is still true, there were no elves in the 8 surrounding cells -> skip moving
            if (!free) {
                GridPoint2D goalGridPoint2D = elf;
                for (int i = 0; !free && i < 4; i++) {
                    final int index = (round + i) % 4;
                    goalGridPoint2D = elf.plus(MOVES[index][0]);
                    GridPoint2D nb1 = elf.plus(MOVES[index][1]);
                    GridPoint2D nb2 = elf.plus(MOVES[index][2]);
                    free = !(elves.contains(goalGridPoint2D) || elves.contains(nb1) || elves.contains(nb2));
                }
                if (free) {
                    List<GridPoint2D[]> moveList = moveMap.computeIfAbsent(goalGridPoint2D, p -> new ArrayList<>());
                    moveList.add(new GridPoint2D[]{elf, goalGridPoint2D});
                }
            }
        }
        // now the map has either a single value for a destination, or multiple, only move in the former case
        int moved = 0;
        for (final var moves : moveMap.entrySet()) {
            final var position = moves.getKey();
            List<GridPoint2D[]> elfMoves = moves.getValue();
            if (elfMoves.size() == 1) { // execute move
                elves.remove(elfMoves.get(0)[0]);
                elves.add(position);
                moved++;
            }
        }
        return moved;
    }

}
