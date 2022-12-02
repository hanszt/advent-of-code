package hzt.aoc.day12;

import hzt.aoc.Point2D;

import java.util.List;

public class Part1RainRisk extends Day12Challenge {

    public Part1RainRisk() {
        super("part 1",
                "What is the Manhattan distance between that location and the ship's starting position? See ChallengeDAy12.md");
    }

    @Override
    protected String solve(final List<String> inputList) {
        Point2D position = new Point2D(0, 0);
        Point2D orientation = new Point2D(1, 0);
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

    private static Point2D rotateShip(final Point2D initOrientation, final char instruction, final int angle) {
        Point2D orientation = initOrientation;
        if ((instruction == TURN_RIGHT || instruction == TURN_LEFT) && angle % NINETY_DEGREES == 0) {
            final int dir = instruction == TURN_RIGHT ? 1 : -1;
            int counter = 0;
            while (angle / NINETY_DEGREES != counter) {
                if (orientation.equals(new Point2D(1, 0))) {
                    orientation = new Point2D(0, -dir);
                } else if (orientation.equals(new Point2D(0, 1))) {
                    orientation = new Point2D(dir, 0);
                } else if (orientation.equals(new Point2D(-1, 0))) {
                    orientation = new Point2D(0, dir);
                } else if (orientation.equals(new Point2D(0, -1))) {
                    orientation = new Point2D(-dir, 0);
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
