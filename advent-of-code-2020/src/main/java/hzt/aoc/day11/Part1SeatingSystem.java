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
        var occupied = 0;
        var prevOccupied = -1;
        while (occupied != prevOccupied) {
            prevOccupied = occupied;
            occupied = checkOccupiedAndUpdateList(inputList);
        }
        return String.valueOf(occupied);
    }

    private int checkOccupiedAndUpdateList(final List<String> inputList) {
        var occupied = 0;
        final List<String> newList = new ArrayList<>();
        for (var row = 0; row < inputList.size(); row++) {
            final var curRow = inputList.get(row);
            final var upperRow = row > 0 ? inputList.get(row - 1) : null;
            final var lowerRow = row < inputList.size() - 1 ? inputList.get(row + 1) : null;
            occupied += checkAndUpdateRow(upperRow, curRow, lowerRow, newList);
        }
        inputList.clear();
        inputList.addAll(newList);
        return occupied;
    }

    private static final int THRESHOLD_BECOMES_EMPTY = 4;


    private int checkAndUpdateRow(final String upperRow, final String curRow, final String lowerRow, final List<String> newList) {
        var occupied = 0;
        final var charsNewRow = curRow.toCharArray();
        for (var col = 0; col < charsNewRow.length; col++) {
            final var neighbours = extractNeighBours(upperRow, curRow, lowerRow, col);
            var occupiedNeighbours = 0;
            for (final var c : neighbours.toCharArray()) {
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
        final var upperNeighBours = extractNeighboursByRow(upperRow, col);
        final var lowerNeighBours = extractNeighboursByRow(lowerRow, col);
        final var leftNeighBour = curRow.substring(col > 0 ? col - 1 : col, col);
        final var rightNeighBour = curRow.substring(col < curRow.length() - 1 ? col + 1 : col, col < curRow.length() - 1 ? col + 2 : col);
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
