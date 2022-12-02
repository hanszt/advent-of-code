package hzt.aoc.day17;

public record GridPoint3D(int x, int y, int z) {

    public boolean samePoint(int x, int y, int z) {
        return this.x == x && this.y == y && this.z == z;
    }
}
