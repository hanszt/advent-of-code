package aoc;

import aoc.utils.model.GridPoint2D;
import org.hzt.utils.io.FileX;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        final var magnitude = Integer.parseInt(s[1]);
        final var dir = s[0];
        return switch (dir) {
            case "R" -> GridPoint2D.of(magnitude, 0);
            case "L" -> GridPoint2D.of(-magnitude, 0);
            case "U" -> GridPoint2D.of(0, magnitude);
            case "D" -> GridPoint2D.of(0, -magnitude);
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
            final var curDir = move.toSignVector();
            final var magnitude = move.gridDistance(GridPoint2D.ZERO);
            for (int j = 0; j < magnitude; j++) {
                headPos = headPos.plus(curDir);
                final var headTailDistance = headPos.gridDistance(tailPos);
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
        GridPoint2D[] knotPos = new GridPoint2D[10];
        Arrays.fill(knotPos, GridPoint2D.ZERO);
        for (final var move : moveInstructions) {
            final var curDir = move.toSignVector();
            final var magnitude = move.gridDistance(GridPoint2D.ZERO);
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
            final var distance = posHead.gridDistance(posTail);
            final var case1 = distance == 2 && (posHead.getX() == posTail.getX() || posHead.getY() == posTail.getY());
            final var case2 = distance > 2 && posHead.getX() != posTail.getX() && posHead.getY() != posTail.getY();
            if (case1 || case2) {
                knotPos[i + 1] = posTail.plus(posHead.minus(posTail).toSignVector());
            }
        }
        return knotPos[knotPos.length - 1];
    }
}
