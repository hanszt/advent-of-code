package hzt.aoc.day03;

import hzt.aoc.Point2D;

import java.util.List;

public class TreesEncounteredPart2 extends Day03Challenge {

    public TreesEncounteredPart2() {
        super("part 2",
                "Find the product of the number of trees crossed by all the given paths");
    }

    @Override
    protected long calculateResult(final List<List<Boolean>> grid) {
        long product = 1;
        for (final Path path : Path.values()) {
            final int numberOfTrees = calculateNumberOfTreesEncountered(grid, new Point2D(0, 0), path.getSlope());
            LOGGER.info(String.format("The number of trees crossed using %s is %d", path.name(), numberOfTrees));
            product *= numberOfTrees;
        }
        LOGGER.info("");
        return product;
    }

    @Override
   protected String getMessage(final String result) {
        return String.format("The product of all the number of trees crossed is: %s%n", result);
    }

}
