package hzt.aoc.day05;

import hzt.aoc.Challenge;
import hzt.aoc.day05.model.Seat;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Day05Challenge extends Challenge {

    static final int NUMBER_OF_ROWS = 128;
    static final int NUMBER_OF_COLUMNS = 8;
    static final char KEEP_LOWER_HALF_ROWS = 'F';
    static final char KEEP_UPPER_HALF_ROWS = 'B';
    static final char KEEP_LOWER_HALF_COLS = 'L';
    static final char KEEP_UPPER_HALF_COLS = 'R';
    static final int AMOUNT_SIGNS_FRONT_BACK = 7;

    protected Day05Challenge(final String challengeTitle, final String description) {
        super(challengeTitle, description, "20201205-input-day5.txt");
    }

    @Override
    protected String solve(final List<String> inputList) {
        final List<Seat> seats = inputList.stream().map(this::extractSeat).collect(Collectors.toList());
        final int result = calculateResult(seats);
        return String.valueOf(result);
    }

    protected abstract int calculateResult(List<Seat> seats);

    Seat extractSeat(final String string) {
        int lowerBoundRows = 0;
        int upperBoundRows = NUMBER_OF_ROWS;
        int lowerBoundCols = 0;
        int upperBoundCols = NUMBER_OF_COLUMNS;
        for (int i = 0; i < string.length(); i++) {
            if (i < AMOUNT_SIGNS_FRONT_BACK) {
                if (string.charAt(i) == KEEP_UPPER_HALF_ROWS) {
                    lowerBoundRows = newLowerBound(lowerBoundRows, upperBoundRows);
                } else if (string.charAt(i) == KEEP_LOWER_HALF_ROWS) {
                    upperBoundRows = newUpperBound(lowerBoundRows, upperBoundRows);
                }
            } else {
                if (string.charAt(i) == KEEP_UPPER_HALF_COLS) {
                    lowerBoundCols = newLowerBound(lowerBoundCols, upperBoundCols);
                } else if (string.charAt(i) == KEEP_LOWER_HALF_COLS) {
                    upperBoundCols = newUpperBound(lowerBoundCols, upperBoundCols);
                }
            }
        }
        return new Seat(string, lowerBoundRows, lowerBoundCols);
    }

    private int newLowerBound(final int lower, final int upper) {
        return lower + ((upper - lower) / 2);
    }

    private int newUpperBound(final int lower, final int upper) {
        return upper - ((upper - lower) / 2);
    }

    int findHighestSeatID(final List<Integer> boardingPassIds) {
        return boardingPassIds.stream().reduce(Integer::max).orElse(0);
    }

}
