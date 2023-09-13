package hzt.aoc.day17;

import aoc.utils.model.GridPoint3D;

import java.util.Arrays;
import java.util.List;

public class Part1ConwayCubesByArrays extends Day17ChallengeByArrays {

    public Part1ConwayCubesByArrays() {
        super("part 1",
                "Starting with your given initial configuration, simulate six cycles. " +
                        "How many cubes are left in the active state after the sixth cycle?");
    }

    public Part1ConwayCubesByArrays(final String challengeTitle, final String description) {
        super(challengeTitle, description);
    }

    @Override
    protected long solveByGrid(final List<String> inputList) {
        var grid3d = inputListToInitGrid3D(inputList);
        for (var i = 0; i < NUMBER_OF_CYCLES; i++) {
            grid3d = updateGrid(addInactiveOuterLayer3D(grid3d));
        }
        return countActive3D(grid3d);
    }

    long countActive3D(final boolean[][][] grid3d) {
        return Arrays.stream(grid3d)
                .flatMap(Arrays::stream)
                .mapToInt(Part1ConwayCubesByArrays::countActive)
                .sum();
    }

    private static int countActive(boolean[] booleans) {
        var count = 0;
        for (var active : booleans) {
            if (active) {
                count++;
            }
        }
        return count;
    }

    private boolean[][][] updateGrid(final boolean[][][] grid3d) {
        final var newGrid3d = deepCopyGrid3D(grid3d);
        for (var z = 0; z < grid3d.length; z++) {
            final var grid2d = grid3d[z];
            for (var y = 0; y < grid2d.length; y++) {
                final var row = grid2d[y];
                for (var x = 0; x < row.length; x++) {
                    final var activeNeighbors = countActiveNeighbors(GridPoint3D.of(x, y, z), grid3d);
                    newGrid3d[z][y][x] = applyRules(row[x], activeNeighbors);
                }
            }
        }
        return addInactiveOuterLayer3D(newGrid3d);
    }

    @SuppressWarnings("squid:S134")
    int countActiveNeighbors(final GridPoint3D cur, final boolean[][][] curGrid3d) {
        var activeNeighbors = 0;
        for (var z = Math.max(cur.getZ() - 1, 0); z <= upperBound(cur.getZ(), curGrid3d.length); z++) {
            for (var y = Math.max(cur.getY() - 1, 0); y <= upperBound(cur.getY(), curGrid3d[0].length); y++) {
                for (var x = Math.max(cur.getX() - 1, 0); x <= upperBound(cur.getX(), curGrid3d[0][0].length); x++) {
                    if (!cur.equals(GridPoint3D.of(x, y, z)) && curGrid3d[z][y][x]) {
                        activeNeighbors++;
                    }
                }
            }
        }
        return activeNeighbors;
    }

}
