package hzt.aoc.day03;

import aoc.utils.grid2d.Grid2DUtilsKt;
import aoc.utils.grid2d.GridPoint2D;
import hzt.aoc.Challenge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Day03Challenge extends Challenge {

    private static final Character TREE = '#';

    protected Day03Challenge(final String part, final String description) {
        super(part, description, "20201203-input-day3.txt");
    }

    @Override
    protected String solve(final List<String> inputList) {
        final List<List<Boolean>> grid = !inputList.isEmpty() ? buildGrid(inputList) : Collections.emptyList();
        LOGGER.trace(() -> Grid2DUtilsKt.gridAsString(grid));
        return String.valueOf(calculateResult(grid));
    }

    protected abstract long calculateResult(List<List<Boolean>> grid);

    int calculateNumberOfTreesEncountered(final List<List<Boolean>> treeGrid, final GridPoint2D slope) {
        var numberOfTrees = 0;
        var position = GridPoint2D.ZERO;
        while (true) {
            final var y = position.getY();
            if (y >= treeGrid.size()) {
                break;
            }
            final var x = position.getX();
            final boolean isTree = treeGrid.get(y).get(x);
            LOGGER.trace(() -> "x: " + x + ", y: " + y + ", " + "Is tree: " + isTree);
            if (isTree) {
                numberOfTrees++;
            }
            position = position.plus(slope);
        }
        return numberOfTrees;
    }

    private static List<List<Boolean>> buildGrid(final List<String> inputList) {
        final double patternLength = inputList.get(0).length();
        final var height = inputList.size();
        final var length = height * (Path.SLOPE7_1.getSlope().getX());
        final var timesRepeatedHorizontally = (int) Math.round(length / patternLength);
        final List<List<Boolean>> gird = new ArrayList<>();
        for (final var patternRow : inputList) {
            final List<Boolean> newRow = new ArrayList<>();
            final var newRowArray = patternRow.repeat(timesRepeatedHorizontally).toCharArray();
            for (final Character c : newRowArray) {
                newRow.add(c.equals(TREE));
            }
            gird.add(newRow);
        }
        return gird;
    }

    enum Path {
        SLOPE3_1(GridPoint2D.of(3, 1)),
        SLOPE1_1(GridPoint2D.of(1, 1)),
        SLOPE5_1(GridPoint2D.of(5, 1)),
        SLOPE7_1(GridPoint2D.of(7, 1)),
        SLOPE1_2(GridPoint2D.of(1, 2));

        private final GridPoint2D slope;

        Path(final GridPoint2D slope) {
            this.slope = slope;
        }

        public GridPoint2D getSlope() {
            return slope;
        }
    }
}
