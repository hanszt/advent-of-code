package hzt.aoc.day17;

import java.util.Arrays;
import java.util.List;

public class Part2ConwayCubesByArrays extends Part1ConwayCubesByArrays {

    public Part2ConwayCubesByArrays() {
        super("part 2",
                "Starting with your given initial configuration, simulate six cycles in a 4-dimensional space. " +
                        "How many cubes are left in the active state after the sixth cycle?");
    }

    @Override
    protected long solveByGrid(final List<String> inputList) {
        var grid4d = new boolean[][][][]{inputListToInitGrid3D(inputList)};
        for (var i = 0; i < NUMBER_OF_CYCLES; i++) {
            grid4d = addInactiveOuterLayer4D(grid4d);
            grid4d = applyRules4D(grid4d);
        }
        return countActive4D(grid4d);
    }

    private long countActive4D(final boolean[][][][] grid4d) {
        return Arrays.stream(grid4d)
                .mapToLong(this::countActive3D)
                .sum();
    }

    static boolean[][][][] addInactiveOuterLayer4D(final boolean[][][][] grid4d) {
        var newGrid4d = new boolean[grid4d.length + 2][][][];
        System.arraycopy(grid4d, 0, newGrid4d, 1, grid4d.length);
        for (var z = 0; z < grid4d.length; z++) {
            final var grid3D = grid4d[z];
            final var newGrid3D = addInactiveOuterLayer3D(grid3D);
            newGrid4d[z + 1] = newGrid3D;
        }
        final var firstGrid3D = newGrid4d[1];
        final var newWidth = firstGrid3D[1][1].length;
        final var newHeight = firstGrid3D[1].length;
        final var newDepth = firstGrid3D.length;

        newGrid4d[0] = createInActiveXYZGrid(newWidth, newHeight, newDepth);
        newGrid4d[newGrid4d.length - 1] = createInActiveXYZGrid(newWidth, newHeight, newDepth);
        return newGrid4d;
    }

    @SuppressWarnings("squid:S134")
    private boolean[][][][] applyRules4D(final boolean[][][][] grid4d) {
        final var newGrid4d = deepCopyGrid4D(grid4d);
        for (var w = 0; w < grid4d.length; w++) {
            final var grid3d = grid4d[w];
            for (var z = 0; z < grid3d.length; z++) {
                final var grid2d = grid3d[z];
                for (var y = 0; y < grid2d.length; y++) {
                    final var row = grid2d[y];
                    for (var x = 0; x < row.length; x++) {
                        final var activeNeighbors = countActiveNeighbors(new GridPoint4D(x, y, z, w), grid4d);
                        newGrid4d[w][z][y][x] = applyRules(row[x], activeNeighbors);
                    }
                }
            }
        }
        addInactiveOuterLayer4D(newGrid4d);
        return newGrid4d;
    }

    private boolean[][][][] deepCopyGrid4D(final boolean[][][][] grid4d) {
        final var copy = new boolean[grid4d.length][][][];
        for (var i = 0; i < grid4d.length; i++) {
            var grid3d = grid4d[i];
            copy[i] = deepCopyGrid3D(grid3d);
        }
        return copy;
    }

    @SuppressWarnings({"squid:S134", "squid:S3776"})
    private int countActiveNeighbors(final GridPoint4D cur, final boolean[][][][] curGrid4d) {
        var activeNeighbors = 0;
        for (var w = Math.max(cur.w() - 1, 0); w <= upperBound(cur.w(), curGrid4d.length); w++) {
            for (var z = Math.max(cur.z() - 1, 0); z <= upperBound(cur.z(), curGrid4d[0].length); z++) {
                for (var y = Math.max(cur.y() - 1, 0); y <= upperBound(cur.y(), curGrid4d[0][0].length); y++) {
                    for (var x = Math.max(cur.x() - 1, 0); x <= upperBound(cur.x(), curGrid4d[0][0][0].length); x++) {
                        if (!cur.samePoint(x, y, z, w) && curGrid4d[w][z][y][x]) {
                            activeNeighbors++;
                        }
                    }
                }
            }
        }
        return activeNeighbors;
    }

}
