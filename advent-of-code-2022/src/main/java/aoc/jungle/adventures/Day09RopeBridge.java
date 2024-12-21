package aoc.jungle.adventures;

import aoc.utils.ChallengeDay;
import aoc.utils.grid2d.GridPoint2D;
import org.hzt.utils.arrays.ArraysX;
import org.hzt.utils.io.FileX;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static aoc.utils.grid2d.GridPoint2DKt.gridPoint2D;

/**
 * @see <a href="https://adventofcode.com/2022/day/9">Day 9: Rope bridge</a>
 */
public class Day09RopeBridge implements ChallengeDay {

    private final List<GridPoint2D> moveInstructions;

    public Day09RopeBridge(String fileName) {
        moveInstructions = FileX.of(fileName).useLines(s -> s.map(Day09RopeBridge::toInstruction).toList());
    }

    @NotNull
    private static GridPoint2D toInstruction(String line) {
        final var s = line.split(" ");
        final var dir = s[0];
        final var magnitude = Integer.parseInt(s[1]);
        return switch (dir) {
            case "R" -> gridPoint2D(magnitude, 0);
            case "L" -> gridPoint2D(-magnitude, 0);
            case "U" -> gridPoint2D(0, magnitude);
            case "D" -> gridPoint2D(0, -magnitude);
            default -> throw new IllegalStateException("No valid direction " + dir);
        };
    }

    @NotNull
    @Override
    public Integer part1() {
        Set<GridPoint2D> tailPositions = new HashSet<>();
        var headPos = GridPoint2D.ZERO;
        var tailPos = GridPoint2D.ZERO;
        for (final var move : moveInstructions) {
            final var curDir = move.getSign();
            final var magnitude = move.manhattanDistance(GridPoint2D.ZERO);
            for (int j = 0; j < magnitude; j++) {
                headPos = headPos.plus(curDir);
                final var headTailDistance = headPos.manhattanDistance(tailPos);
                if (headTailDistance == 2 && (headPos.getX() == tailPos.getX() || headPos.getY() == tailPos.getY())) {
                    tailPos = tailPos.plus(curDir);
                }
                if (headTailDistance > 2 && headPos.getX() != tailPos.getX() && headPos.getY() != tailPos.getY()) {
                    tailPos = headPos.minus(curDir);
                }
                tailPositions.add(tailPos);
            }
        }
        return tailPositions.size();
    }

    @NotNull
    @Override
    public Integer part2() {
        Set<GridPoint2D> tailPositions = new HashSet<>();
        GridPoint2D[] knotPos = ArraysX.generateArray(10, i -> GridPoint2D.ZERO, GridPoint2D[]::new);
        for (final var move : moveInstructions) {
            final var curDir = move.getSign();
            final var magnitude = move.manhattanDistance(GridPoint2D.ZERO);
            for (int j = 0; j < magnitude; j++) {
                knotPos[0] = knotPos[0].plus(curDir);
                tailPositions.add(nextTailPosition(knotPos));
            }
        }
        return tailPositions.size();
    }

    private static GridPoint2D nextTailPosition(GridPoint2D[] knotPos) {
        for (int i = 0; i < knotPos.length - 1; i++) {
            final var posHead = knotPos[i];
            final var posTail = knotPos[i + 1];
            final var distance = posHead.manhattanDistance(posTail);
            final var case1 = distance == 2 && (posHead.getX() == posTail.getX() || posHead.getY() == posTail.getY());
            final var case2 = distance > 2 && posHead.getX() != posTail.getX() && posHead.getY() != posTail.getY();
            if (case1 || case2) {
                knotPos[i + 1] = posTail.plus(posHead.minus(posTail).getSign());
            }
        }
        return knotPos[knotPos.length - 1];
    }
}
