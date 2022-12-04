package hzt.aoc.day12;

import hzt.aoc.GridPoint2D;

import java.util.List;

public class Part2RainRisk extends Day12Challenge {

    public Part2RainRisk() {
        super("part 2",
                "Almost all of the actions indicate how to move a waypoint which is relative to the ship's position: " +
                        "(See ChallengeDay12.md)" +
                        "What is the Manhattan distance between that location and the ship's starting position?");
    }

    @Override
    protected String solve(final List<String> inputList) {
        GridPoint2D wayPoint = new GridPoint2D(10, 1);
        GridPoint2D ship = new GridPoint2D(0, 0);
        for (final String line : inputList) {
            final char instruction = line.charAt(0);
            final int amount = Integer.parseInt(line.substring(1));
            wayPoint = rotateWayPointRoundShip(wayPoint, instruction, amount);
            wayPoint = wayPoint.add(getTranslationInWindDirection(instruction, amount));
            if (instruction == MOVE_FORWARD) {
                ship = ship.add(wayPoint.x() * amount, wayPoint.y() * amount);
            }
        }
        return getMessage(Math.abs(ship.x()) + Math.abs(ship.y()));
    }

    private static GridPoint2D rotateWayPointRoundShip(final GridPoint2D initWayPoint, final char instruction, final int angle) {
        GridPoint2D wayPoint = initWayPoint;
        if ((instruction == TURN_RIGHT || instruction == TURN_LEFT) && angle % NINETY_DEGREES == 0) {
            final GridPoint2D dir = instruction == TURN_RIGHT ? new GridPoint2D(1, -1) : new GridPoint2D(-1, 1);
            int counter = 0;
            while (angle / NINETY_DEGREES != counter) {
                wayPoint = new GridPoint2D(dir.x() * wayPoint.y(), dir.y() * wayPoint.x());
                counter++;
            }
        }
        return wayPoint;
    }

    @Override
    String getMessage(final int value) {
        return String.format("%d", value);
    }
}
