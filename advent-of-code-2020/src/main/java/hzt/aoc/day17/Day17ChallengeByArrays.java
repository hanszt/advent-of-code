package hzt.aoc.day17;

import hzt.aoc.Challenge;

import java.util.Arrays;
import java.util.List;

public abstract class Day17ChallengeByArrays extends Challenge {

    static final int NUMBER_OF_CYCLES = 6;
    static final char ACTIVE = '#';

    Day17ChallengeByArrays(final String challengeTitle, final String description) {
        super(challengeTitle, description, "20201217-input-day17.txt");
    }

    @Override
    protected String solve(final List<String> inputList) {
        return String.valueOf(solveByGrid(inputList));
    }

    protected abstract long solveByGrid(List<String> inputList);

    boolean[][][] inputListToInitGrid3D(final List<String> inputList) {
        final var grid2D = new boolean[inputList.size()][];
        for (var rowi = 0; rowi < inputList.size(); rowi++) {
            var line = inputList.get(rowi);
            final var length = line.length();
            final var row = new boolean[length];
            for (var coli = 0; coli < length; coli++) {
                row[coli] = line.charAt(coli) == ACTIVE;
            }
            grid2D[rowi] = row;
        }
        return new boolean[][][]{grid2D};
    }

    boolean[][][] deepCopyGrid3D(final boolean[][][] grid3d) {
        final var copy = Arrays.copyOf(grid3d, grid3d.length);
        for (var y = 0; y < grid3d.length; y++) {
            final var gridXY = grid3d[y];
            final var newGridXY = Arrays.copyOf(gridXY, gridXY.length);
            for (var x = 0; x < gridXY.length; x++) {
                final var rowX = gridXY[x];
                newGridXY[x] = Arrays.copyOf(rowX, rowX.length);
            }
            copy[y] = newGridXY;
        }
        return copy;
    }

    static boolean[][][] addInactiveOuterLayer3D(final boolean[][][] grid3d) {
        var newGrid3d = new boolean[grid3d.length + 2][][];
        System.arraycopy(grid3d, 0, newGrid3d, 1, grid3d.length);
        for (var y = 0; y < grid3d.length; y++) {
            var gridXY = grid3d[y];
            var newGridXY = addInactiveOuterLayer2D(gridXY);
            newGrid3d[y + 1] = newGridXY;
        }
        final var firstPlane = newGrid3d[1];
        final var newWidth = firstPlane[1].length;
        final var newHeight = firstPlane.length;

        newGrid3d[0] = createInActiveXYPlane(newWidth, newHeight);
        newGrid3d[newGrid3d.length - 1] = createInActiveXYPlane(newWidth, newHeight);
        return newGrid3d;
    }

    static boolean[][] addInactiveOuterLayer2D(boolean[][] gridXY) {
        var newGridXY = new boolean[gridXY.length + 2][];
        System.arraycopy(gridXY, 0, newGridXY, 1, gridXY.length);
        for (var x = 0; x < gridXY.length; x++) {
            var rowX = gridXY[x];
            var newRowX = new boolean[rowX.length + 2];
            System.arraycopy(rowX, 0, newRowX, 1, rowX.length);
            newGridXY[x + 1] = newRowX;
        }
        final var newGridLength = newGridXY[1].length;
        newGridXY[0] = new boolean[newGridLength];
        newGridXY[newGridXY.length - 1] = new boolean[newGridLength];
        return newGridXY;
    }

    static boolean[][] createInActiveXYPlane(final int width, final int height) {
        final var inActiveGridXY = new boolean[height][];
        Arrays.fill(inActiveGridXY, new boolean[width]);
        return inActiveGridXY;
    }

    static boolean[][][] createInActiveXYZGrid(final int width, final int height, final int depth) {
        final var inActiveGridXYZ = new boolean[depth][][];
        for (var z = 0; z < depth; z++) {
            inActiveGridXYZ[z] = createInActiveXYPlane(width, height);
        }
        return inActiveGridXYZ;
    }

    String grid3DAsString(final boolean[][][] grid3d) {
        final var sb = new StringBuilder();
        var z = -(grid3d.length - 1) / 2;
        for (final var gridXY : grid3d) {
            sb.append(String.format("%nSlice at z = %d", z));
            sb.append(booleanGrid2DAsString(gridXY));
            z++;
        }
        return sb.toString();
    }

    /*
     If a cube is active and exactly 2 or 3 of its neighbors are also active, the cube remains active. Otherwise, the cube becomes inactive.
     If a cube is inactive but exactly 3 of its neighbors are active, the cube becomes active. Otherwise, the cube remains inactive.
 */
    boolean isActive(final boolean active, final int activeNeighbors) {
        final var remainActive = active && (activeNeighbors == 2 || activeNeighbors == 3);
        final var becomeActive = !active && activeNeighbors == 3;
        return remainActive || becomeActive;
    }

    int upperBound(final int curVal, final int gridDimension) {
        return ((curVal + 1) < gridDimension) ? (curVal + 1) : curVal;
    }

    @Override
    protected String getMessage(final String global) {
        return String.format("%s cubes are left in the active state after %d cycles", global, NUMBER_OF_CYCLES);
    }
}
