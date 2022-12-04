package hzt.aoc.day03;

import hzt.aoc.Challenge;

import hzt.aoc.GridPoint2D;

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
        LOGGER.trace(booleanGrid2DAsString(grid));
        return String.valueOf(calculateResult(grid));
    }

    protected abstract long calculateResult(List<List<Boolean>> grid);

    int calculateNumberOfTreesEncountered(final List<List<Boolean>> treeGrid, final GridPoint2D initPositin, final GridPoint2D slope) {
        int numberOfTrees = 0;
        GridPoint2D position = initPositin;
        while (position.y() < treeGrid.size()) {
            final boolean isTree = treeGrid.get(position.y()).get(position.x());
            LOGGER.trace("x: " + position.x() + ", y: " + position.y() + ", " + "Is tree: " + isTree);
            if (isTree) {
                numberOfTrees++;
            }
            position = position.add(slope.x(), slope.y());
        }
        return numberOfTrees;
    }

    private static List<List<Boolean>> buildGrid(final List<String> inputList) {
        final double patternLength = inputList.get(0).length();
        final int height = inputList.size();
        final int length = height * (Path.SLOPE7_1.getSlope().x());
        final int timesRepeatedHorizontally = (int) Math.round(length / patternLength);
        final List<List<Boolean>> gird = new ArrayList<>();
        for (final String patternRow : inputList) {
            final List<Boolean> newRow = new ArrayList<>();
            final char[] newRowArray = patternRow.repeat(timesRepeatedHorizontally).toCharArray();
            for (final Character c : newRowArray) {
                newRow.add(c.equals(TREE));
            }
            gird.add(newRow);
        }
        return gird;
    }

    enum Path {
        SLOPE3_1(new GridPoint2D(3, 1)),
        SLOPE1_1(new GridPoint2D(1, 1)),
        SLOPE5_1(new GridPoint2D(5, 1)),
        SLOPE7_1(new GridPoint2D(7, 1)),
        SLOPE1_2(new GridPoint2D(1, 2));

        private final GridPoint2D slope;

        Path(final GridPoint2D slope) {
            this.slope = slope;
        }

        public GridPoint2D getSlope() {
            return slope;
        }
    }
}
