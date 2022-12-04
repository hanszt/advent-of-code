package hzt.aoc.day12;

import hzt.aoc.GridPoint2D;

import java.util.List;

public class Part1RainRisk extends Day12Challenge {

    public Part1RainRisk() {
        super("part 1",
                "What is the Manhattan distance between that location and the ship's starting position? See ChallengeDAy12.md");
    }

    @Override
    protected String solve(final List<String> inputList) {
        GridPoint2D position = new GridPoint2D(0, 0);
        GridPoint2D orientation = new GridPoint2D(1, 0);
        for (final String line : inputList) {
            final char instruction = line.charAt(0);
            final int amount = Integer.parseInt(line.substring(1));
            orientation = rotateShip(orientation, instruction, amount);
            position = position.add(getTranslationInWindDirection(instruction, amount));
            if (instruction == MOVE_FORWARD) {
                position = position.add(orientation.x() * amount, orientation.y() * amount);
            }
        }
        return getMessage(Math.abs(position.x()) + Math.abs(position.y()));
    }

    private static GridPoint2D rotateShip(final GridPoint2D initOrientation, final char instruction, final int angle) {
        GridPoint2D orientation = initOrientation;
        if ((instruction == TURN_RIGHT || instruction == TURN_LEFT) && angle % NINETY_DEGREES == 0) {
            final int dir = instruction == TURN_RIGHT ? 1 : -1;
            int counter = 0;
            while (angle / NINETY_DEGREES != counter) {
                if (orientation.equals(new GridPoint2D(1, 0))) {
                    orientation = new GridPoint2D(0, -dir);
                } else if (orientation.equals(new GridPoint2D(0, 1))) {
                    orientation = new GridPoint2D(dir, 0);
                } else if (orientation.equals(new GridPoint2D(-1, 0))) {
                    orientation = new GridPoint2D(0, dir);
                } else if (orientation.equals(new GridPoint2D(0, -1))) {
                    orientation = new GridPoint2D(-dir, 0);
                } else {
                    throw new IllegalStateException();
                }
                counter++;
            }
        }
        return orientation;
    }

    @Override
    String getMessage(final int global) {
        return String.format("%d", global);
    }
}
