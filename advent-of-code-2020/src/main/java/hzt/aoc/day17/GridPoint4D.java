package hzt.aoc.day17;

public record GridPoint4D(int x, int y, int z, int w) {
    public boolean samePoint(int x, int y, int z, int w) {
        return this.x == x && this.y == y && this.z == z && this.w == w;
    }
}
