package hzt.aoc.day17;

import aoc.utils.grid2d.Grid2DUtilsKt;
import hzt.aoc.Challenge;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class Day17Challenge extends Challenge {

    static final int NUMBER_OF_CYCLES = 6;
    static final char ACTIVE = '#';

    Day17Challenge(final String challengeTitle, final String description) {
        super(challengeTitle, description, "20201217-input-day17.txt");
    }

    @Override
    protected String solve(final List<String> inputList) {
        return String.valueOf(solveByGrid(inputList));
    }

    protected abstract long solveByGrid(List<String> inputList);

    List<List<List<Boolean>>> getInitGrid3D(final List<String> inputList) {
        final List<List<List<Boolean>>> grid3D = new ArrayList<>();
        final List<List<Boolean>> grid2D = new ArrayList<>();
        for (final var line : inputList) {
            final var charRow = line.toCharArray();
            final List<Boolean> row = new ArrayList<>();
            for (final var state : charRow) {
                row.add(state == ACTIVE);
            }
            grid2D.add(row);
        }
        grid3D.add(grid2D);
        return grid3D;
    }

    List<List<List<Boolean>>> copyGrid3D(final List<List<List<Boolean>>> grid3d) {
        final List<List<List<Boolean>>> copy = new ArrayList<>();
        for (final var gridXY : grid3d) {
            final List<List<Boolean>> newGridXY = new ArrayList<>();
            for (final var rowX : gridXY) {
                final List<Boolean> newRowX = new ArrayList<>(rowX);
                newGridXY.add(newRowX);
            }
            copy.add(newGridXY);
        }
        return copy;
    }

    void addInactiveOuterLayer3D(final List<List<List<Boolean>>> grid3d) {
        for (final var gridXY : grid3d) {
            for (final var rowX : gridXY) {
                rowX.addFirst(false);
                rowX.add(false);
            }
            final var size = gridXY.getFirst().size();
            gridXY.addFirst(createInActiveRow(size));
            gridXY.add(createInActiveRow(size));
        }
        final var firstPlane = grid3d.getFirst();
        final var newWidth = firstPlane.getFirst().size();
        final var newHeight = firstPlane.size();
        grid3d.addFirst(createInActiveXYPlane(newWidth, newHeight));
        grid3d.add(createInActiveXYPlane(newWidth, newHeight));
    }

    List<Boolean> createInActiveRow(final int width) {
        return IntStream.range(0, width)
                .mapToObj(_ -> false)
                .collect(Collectors.toList());
    }

    List<List<Boolean>> createInActiveXYPlane(final int width, final int height) {
        final List<List<Boolean>> inActiveGridXY = new ArrayList<>();
        for (var y = 0; y < height; y++) {
            inActiveGridXY.add(createInActiveRow(width));
        }
        return inActiveGridXY;
    }

    List<List<List<Boolean>>> createInActiveXYZGrid(final int width, final int height, final int depth) {
        final List<List<List<Boolean>>> inActiveGridXYZ = new ArrayList<>();
        for (var z = 0; z < depth; z++) {
            inActiveGridXYZ.add(createInActiveXYPlane(width, height));
        }
        return inActiveGridXYZ;
    }

    String grid3DAsString(final List<List<List<Boolean>>> grid3d) {
        final var sb = new StringBuilder();
        var z = -(grid3d.size() - 1) / 2;
        for (final var gridXY : grid3d) {
            sb.append(String.format("%nSlice at z = %d", z));
            sb.append(Grid2DUtilsKt.gridAsString(gridXY));
            z++;
        }
        return sb.toString();
    }


    /**
     * If a cube is active and exactly 2 or 3 of its neighbors are also active, the cube remains active. Otherwise, the cube becomes inactive.
     * If a cube is inactive but exactly 3 of its neighbors are active, the cube becomes active. Otherwise, the cube remains inactive.
     */
    boolean applyRules(final boolean active, final int activeNeighbors) {
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
