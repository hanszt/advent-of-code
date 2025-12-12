package hzt.aoc.day12;

import aoc.utils.grid2d.GridPoint2D;

import java.util.List;

public class Part1RainRisk extends Day12Challenge {

    public Part1RainRisk() {
        super("part 1",
                "What is the Manhattan distance between that location and the ship's starting position? See ChallengeDay12.md");
    }

    @Override
    protected String solve(final List<String> inputList) {
        var position = GridPoint2D.ZERO;
        var orientation = GridPoint2D.of(1, 0);
        for (final var line : inputList) {
            final var instruction = line.charAt(0);
            final var amount = Integer.parseInt(line.substring(1));
            orientation = rotateShip(orientation, instruction, amount);
            position = position.plus(getTranslationInWindDirection(instruction, amount));
            if (instruction == MOVE_FORWARD) {
                position = position.plus(orientation.times(amount));
            }
        }
        return getMessage(position.manhattanDistance(GridPoint2D.ZERO));
    }

    private static GridPoint2D rotateShip(final GridPoint2D initOrientation, final char instruction, final int angle) {
        var orientation = initOrientation;
        if ((instruction == TURN_RIGHT || instruction == TURN_LEFT) && angle % NINETY_DEGREES == 0) {
            final var dir = instruction == TURN_RIGHT ? 1 : -1;
            var counter = 0;
            while (angle / NINETY_DEGREES != counter) {
                orientation = getGridPoint2D(orientation, dir);
                counter++;
            }
        }
        return orientation;
    }

    private static GridPoint2D getGridPoint2D(final GridPoint2D orientation, final int dir) {
        if (GridPoint2D.right.equals(orientation)) {
            return GridPoint2D.of(0, -dir);
        } else if (GridPoint2D.down.equals(orientation)) {
            return GridPoint2D.of(dir, 0);
        } else if (GridPoint2D.left.equals(orientation)) {
            return GridPoint2D.of(0, dir);
        } else if (GridPoint2D.up.equals(orientation)) {
            return GridPoint2D.of(-dir, 0);
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    String getMessage(final int global) {
        return String.format("%d", global);
    }
}
