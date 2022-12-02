package hzt.aoc.day11;

import java.util.ArrayList;
import java.util.List;

public class Part1SeatingSystem extends Day11Challenge {

    public Part1SeatingSystem() {
        super("part 1",
                "Simulate your seating area by applying the seating rules repeatedly until no seats change state. " +
                        "How many seats end up occupied?",
                "20201211-input-day11.txt");
    }

    @Override
    protected String solve(final List<String> inputList) {
        int occupied = 0;
        int prevOccupied = -1;
        while (occupied != prevOccupied) {
            prevOccupied = occupied;
            occupied = checkOccupiedAndUpdateList(inputList);
        }
        return String.valueOf(occupied);
    }

    private int checkOccupiedAndUpdateList(final List<String> inputList) {
        int occupied = 0;
        final List<String> newList = new ArrayList<>();
        for (int row = 0; row < inputList.size(); row++) {
            final String curRow = inputList.get(row);
            final String upperRow = row > 0 ? inputList.get(row - 1) : null;
            final String lowerRow = row < inputList.size() - 1 ? inputList.get(row + 1) : null;
            occupied += checkAndUpdateRow(upperRow, curRow, lowerRow, newList);
        }
        inputList.clear();
        inputList.addAll(newList);
        return occupied;
    }

    private static final int THRESHOLD_BECOMES_EMPTY = 4;


    private int checkAndUpdateRow(final String upperRow, final String curRow, final String lowerRow, final List<String> newList) {
        int occupied = 0;
        final char[] charsNewRow = curRow.toCharArray();
        for (int col = 0; col < charsNewRow.length; col++) {
            final String neighbours = extractNeighBours(upperRow, curRow, lowerRow, col);
            int occupiedNeighbours = 0;
            for (final char c : neighbours.toCharArray()) {
                if (c == OCCUPIED_SEAT) {
                    occupiedNeighbours++;
                }
            }
            if (charsNewRow[col] == OCCUPIED_SEAT && occupiedNeighbours >= THRESHOLD_BECOMES_EMPTY) {
                charsNewRow[col] = EMPTY_SEAT;
            }
            if (charsNewRow[col] == EMPTY_SEAT && !neighbours.contains(String.valueOf(OCCUPIED_SEAT))) {
                charsNewRow[col] = OCCUPIED_SEAT;
            }
            if (charsNewRow[col] == OCCUPIED_SEAT) {
                occupied++;
            }
        }
        newList.add(String.copyValueOf(charsNewRow));
        return occupied;
    }

    private String extractNeighBours(final String upperRow, final String curRow, final String lowerRow, final int col) {
        final String upperNeighBours = extractNeighboursByRow(upperRow, col);
        final String lowerNeighBours = extractNeighboursByRow(lowerRow, col);
        final String leftNeighBour = curRow.substring(col > 0 ? col - 1 : col, col);
        final String rightNeighBour = curRow.substring(col < curRow.length() - 1 ? col + 1 : col, col < curRow.length() - 1 ? col + 2 : col);
        return upperNeighBours.concat(lowerNeighBours).concat(leftNeighBour).concat(rightNeighBour);
    }

    private String extractNeighboursByRow(final String row, final int col) {
        final String neighbours;
        if (row != null) {
            neighbours = row.substring(col > 0 ? col - 1 : col, col < row.length() - 1 ? col + 2 : col + 1);
        } else {
            neighbours = "";
        }
        return neighbours;
    }

}
