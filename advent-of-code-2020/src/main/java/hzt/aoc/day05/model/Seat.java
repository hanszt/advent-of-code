package hzt.aoc.day05.model;

public record Seat(String boardingPass, int row, int col) {

    public int getSeatID(final int numberOfColumns) {
        return numberOfColumns * row + col;
    }
}
