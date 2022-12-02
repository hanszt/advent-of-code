package hzt.aoc;

public record Point2D(int x, int y) {

    public Point2D subtract(Point2D other) {
        return subtract(other.x(), other.y());
    }

    public Point2D subtract(int x, int y) {
        return new Point2D(x() - x, y() - y);
    }

    public Point2D add(int x, int y) {
        return new Point2D(x() + x, y() + y);
    }

    public Point2D add(Point2D other) {
        return add(other.x(), other.y());
    }
}
