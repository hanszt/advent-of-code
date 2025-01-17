package hzt.aoc.day17;

import hzt.aoc.GridPoint4D;

import java.util.ArrayList;
import java.util.List;

public class Part2ConwayCubes extends Part1ConwayCubes {

    public Part2ConwayCubes() {
        super("part 2",
                "Starting with your given initial configuration, simulate six cycles in a 4-dimensional space. " +
                        "How many cubes are left in the active state after the sixth cycle?");
    }

    @Override
    protected long solveByGrid(final List<String> inputList) {
        var grid4d = getInitGrid4D(inputList);
        for (var i = 0; i < NUMBER_OF_CYCLES; i++) {
            addInactiveOuterLayer4D(grid4d);
            var index = i;
            LOGGER.trace(() -> "Iteration: " + index);
            var w = -(grid4d.size() - 1) / 2;
            for (final var grid3D : grid4d) {
                var finalW = w;
                LOGGER.trace(() -> String.format("at w = %d %s", finalW, grid3DAsString(grid3D)));
                w++;
            }
            grid4d = applyRules4D(grid4d);
        }
        return countActive4D(grid4d);
    }

    private long countActive4D(final List<List<List<List<Boolean>>>> grid4d) {
        long active = 0;
        for (final var grid3D : grid4d) {
            active += countActive3D(grid3D);
        }
        return active;
    }

    private void addInactiveOuterLayer4D(final List<List<List<List<Boolean>>>> grid4d) {
        for (final var grid3d : grid4d) {
            addInactiveOuterLayer3D(grid3d);
        }
        final var grid3D = grid4d.get(0);
        final var width = grid3D.get(0).get(0).size();
        final var height = grid3D.get(0).size();
        final var depth = grid3D.size();

        grid4d.add(0, createInActiveXYZGrid(width, height, depth));
        grid4d.add(createInActiveXYZGrid(width, height, depth));
    }

    private List<List<List<List<Boolean>>>> getInitGrid4D(final List<String> inputList) {
        final List<List<List<List<Boolean>>>> grid4D = new ArrayList<>();
        final var grid3D = getInitGrid3D(inputList);
        grid4D.add(grid3D);
        return grid4D;
    }

    private List<List<List<List<Boolean>>>> applyRules4D(final List<List<List<List<Boolean>>>> grid4d) {
        final var newGrid4d = copyGrid4D(grid4d);
        for (var w = 0; w < grid4d.size(); w++) {
            final var grid3d = grid4d.get(w);
            for (var z = 0; z < grid3d.size(); z++) {
                final var grid2d = grid3d.get(z);
                for (var y = 0; y < grid2d.size(); y++) {
                    final var row = grid2d.get(y);
                    for (var x = 0; x < row.size(); x++) {
                        final var activeNeighbors = countActiveNeighbors(GridPoint4D.of(x, y, z, w), grid4d);
                        final var currentActive = applyRules(row.get(x), activeNeighbors);
                        newGrid4d.get(w).get(z).get(y).set(x, currentActive);
                    }
                }
            }
        }
        addInactiveOuterLayer4D(newGrid4d);
        return newGrid4d;
    }

    private List<List<List<List<Boolean>>>> copyGrid4D(final List<List<List<List<Boolean>>>> grid4d) {
        final List<List<List<List<Boolean>>>> copy = new ArrayList<>();
        for (final var grid3d : grid4d) {
            copy.add(copyGrid3D(grid3d));
        }
        return copy;
    }

    private int countActiveNeighbors(final GridPoint4D cur, final List<List<List<List<Boolean>>>> curGrid4d) {
        var activeNeighbors = 0;
        for (var w = Math.max(cur.w() - 1, 0); w <= upperBound(cur.w(), curGrid4d.size()); w++) {
            for (var z = Math.max(cur.z() - 1, 0); z <= upperBound(cur.z(), curGrid4d.get(0).size()); z++) {
                for (var y = Math.max(cur.y() - 1, 0); y <= upperBound(cur.y(), curGrid4d.get(0).get(0).size()); y++) {
                    for (var x = Math.max(cur.x() - 1, 0); x <= upperBound(cur.x(), curGrid4d.get(0).get(0).get(0).size()); x++) {
                        if (isActiveNeighbor(GridPoint4D.of(x, y, z, w), cur, curGrid4d)) {
                            activeNeighbors++;
                        }
                    }
                }
            }
        }
        return activeNeighbors;
    }

    private static boolean isActiveNeighbor(final GridPoint4D checked,
                                            final GridPoint4D cur,
                                            final List<List<List<List<Boolean>>>> curGrid4d) {
        return !cur.equals(checked) && curGrid4d.get(checked.w()).get(checked.z()).get(checked.y()).get(checked.x());
    }

}
