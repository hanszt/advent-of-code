package hzt.aoc;

public interface GridPoint4D {

    int x();
    int y();
    int z();
    int w();

    static GridPoint4D of(final int x, final int y, final int z, final int w) {
        return gridPoint4D(x, y, z, w);
    }

    static GridPoint4D gridPoint4D(final int x, final int y, final int z, final int w) {
        return new GridPoint4DImpl(x, y, z, w);
    }

    default GridPoint4D add(final int x, final int y, final int z, final int w) {
        return GridPoint4D.of(x() + x, y() + y, z() + z, w() + w);
    }

    default GridPoint4D add(final GridPoint4D other) {
        return add(other.x(), other.y(), other.z(), other.w());
    }
}

record GridPoint4DImpl(int x, int y, int z, int w) implements GridPoint4D {
}
