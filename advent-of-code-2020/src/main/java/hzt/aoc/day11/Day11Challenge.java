package hzt.aoc.day11;

import hzt.aoc.Challenge;

public abstract class Day11Challenge extends Challenge {

    static final char EMPTY_SEAT = 'L';
    static final char OCCUPIED_SEAT = '#';
    static final char FLOOR = '.';

    Day11Challenge(final String challengeTitle, final String description, final String inputFileName) {
        super(challengeTitle, description, inputFileName);
    }

    @Override
    protected String getMessage(final String value) {
        return String.format("The number of seats occupied after equilibrium: %s%n", value);
    }
}
