package hzt.aoc.day12;

import aoc.utils.grid2d.GridPoint2D;

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
        var wayPoint = GridPoint2D.of(10, 1);
        var ship = GridPoint2D.ZERO;
        for (final var line : inputList) {
            final var instruction = line.charAt(0);
            final var amount = Integer.parseInt(line.substring(1));
            wayPoint = rotateWayPointRoundShip(wayPoint, instruction, amount);
            wayPoint = wayPoint.plus(getTranslationInWindDirection(instruction, amount));
            if (instruction == MOVE_FORWARD) {
                ship = ship.plus(wayPoint.times(amount));
            }
        }
        return getMessage(Math.abs(ship.getX()) + Math.abs(ship.getY()));
    }

    private static GridPoint2D rotateWayPointRoundShip(final GridPoint2D initWayPoint, final char instruction, final int angle) {
        var wayPoint = initWayPoint;
        if ((instruction == TURN_RIGHT || instruction == TURN_LEFT) && angle % NINETY_DEGREES == 0) {
            final var dir = instruction == TURN_RIGHT ? GridPoint2D.of(1, -1) : GridPoint2D.of(-1, 1);
            var counter = 0;
            while (angle / NINETY_DEGREES != counter) {
                wayPoint = GridPoint2D.of(dir.getX() * wayPoint.getY(), dir.getY() * wayPoint.getX());
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
