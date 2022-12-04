package hzt.aoc.day12;

import hzt.aoc.Challenge;

import hzt.aoc.GridPoint2D;

public abstract class Day12Challenge extends Challenge {

    static final char EAST = 'E';
    static final char WEST = 'W';
    static final char NORTH = 'N';
    static final char SOUTH = 'S';
    static final char TURN_LEFT = 'L';
    static final char TURN_RIGHT = 'R';
    static final char MOVE_FORWARD = 'F';
    static final int NINETY_DEGREES = 90;

    Day12Challenge(final String challengeTitle, final String description) {
        super(challengeTitle, description, "20201212-input-day12.txt");
    }

    GridPoint2D getTranslationInWindDirection(final char direction, final int amount) {
        return switch (direction) {
            case EAST -> new GridPoint2D(amount, 0);
            case WEST -> new GridPoint2D(-amount, 0);
            case NORTH -> new GridPoint2D(0, amount);
            case SOUTH -> new GridPoint2D(0, -amount);
            default -> new GridPoint2D(0, 0);
        };
    }


    abstract String getMessage(int value);

}
