package hzt.aoc.day11;

import java.util.List;
import java.util.function.IntBinaryOperator;

// Credits to Johan de Jong
public class Part2SeatingSystem extends Day11Challenge {
    private static final int THRESHOLD_BECOMES_EMPTY = 5;

    private char[][] state;

    private int width;
    private int height;

    public Part2SeatingSystem() {
        super("part 2",
                "Now, instead of considering just the eight immediately adjacent seats, " +
                        "consider the first seat in each of those eight directions. " +

                        "For example, the empty seat below would see eight occupied seats. echter " +
                        "Given the new visibility method and the rule change for occupied seats becoming empty, " +
                        "once equilibrium is reached, how many seats end up occupied?",
                "20201211-input-day11.txt");
    }

    @Override
    protected String solve(final List<String> inputList) {
        width = inputList.get(0).length();
        height = inputList.size();
        state = new char[height][width];
        for (int y = 0; y < height; y++) {
            final String s = inputList.get(y);
            for (int x = 0; x < width; x++) {
                state[y][x] = s.charAt(x);
            }
        }
        return String.valueOf(iterate(this::adjacentOccupiedLine));
    }

    private int iterate(final IntBinaryOperator adjacentOccupiedFunction) {
        boolean updated = true;
        while (updated) {
            updated = performUpdate(adjacentOccupiedFunction);
        }
        return countOccupied();
    }

    private boolean performUpdate(final IntBinaryOperator adjacentOccupiedFunction) {
        boolean updated = false;
        final char[][] nextState = new char[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (state[row][col] == EMPTY_SEAT && adjacentOccupiedFunction.applyAsInt(col, row) == 0) {
                    nextState[row][col] = OCCUPIED_SEAT;
                    updated = true;
                } else if (state[row][col] == OCCUPIED_SEAT &&
                        adjacentOccupiedFunction.applyAsInt(col, row) >= THRESHOLD_BECOMES_EMPTY) {
                    nextState[row][col] = EMPTY_SEAT;
                    updated = true;
                } else {
                    nextState[row][col] = state[row][col];
                }
            }
        }
        state = nextState;
        return updated;
    }

    private int adjacentOccupiedLine(final int x, final int y) {
        int result = 0;
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if ((dx != 0 || dy != 0) && adjacentOccupiedLine(x, y, dx, dy)) {
                    result++;
                }
            }
        }
        return result;
    }

    private boolean adjacentOccupiedLine(int x, int y, final int dx, final int dy) {
        while (true) {
            x += dx;
            y += dy;
            final char c = get(x, y);
            if (c == OCCUPIED_SEAT) {
                return true;
            }
            if (c == EMPTY_SEAT || c == '\0') {
                return false;
            }
        }
    }

    private int countOccupied() {
        int result = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (state[y][x] == OCCUPIED_SEAT) {
                    result++;
                }
            }
        }
        return result;
    }

    private char get(final int x, final int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return '\0';
        }
        return state[y][x];
    }

    @Override
    protected String getMessage(final String value) {
        return String.format("The number of seats occupied after equilibrium: %s%n", value);
    }
}
