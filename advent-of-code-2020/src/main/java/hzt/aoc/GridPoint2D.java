package hzt.aoc;

public record GridPoint2D(int x, int y) {

    public GridPoint2D add(int x, int y) {
        return new GridPoint2D(x() + x, y() + y);
    }

    public GridPoint2D add(GridPoint2D other) {
        return add(other.x(), other.y());
    }
}
