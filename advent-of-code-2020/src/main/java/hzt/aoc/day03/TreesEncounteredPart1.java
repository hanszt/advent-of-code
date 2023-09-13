package hzt.aoc.day03;

import java.util.List;

public class TreesEncounteredPart1 extends Day03Challenge {

    public TreesEncounteredPart1() {
        super("part 1",
                "Find the number of trees crossed");
    }

    @Override
    protected long calculateResult(final List<List<Boolean>> grid) {
        return calculateNumberOfTreesEncountered(grid, Path.SLOPE3_1.getSlope());
    }

    @Override
    protected String getMessage(final String result) {
        return String.format("The number of trees crossed is: %s%n", result);
    }

}
