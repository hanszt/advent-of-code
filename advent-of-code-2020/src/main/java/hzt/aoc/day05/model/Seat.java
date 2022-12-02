package hzt.aoc.day05.model;

public class Seat {

    private final String boardingPass;
    private final int row;
    private final int col;

    public Seat(final String boardingPass, final int row, final int col) {
        this.boardingPass = boardingPass;
        this.row = row;
        this.col = col;
    }

    public int getSeatID(final int numberOfColumns) {
        return numberOfColumns * row + col;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "boardingPass='" + boardingPass + '\'' +
                ", row=" + row +
                ", col=" + col +
                '}';
    }
}
