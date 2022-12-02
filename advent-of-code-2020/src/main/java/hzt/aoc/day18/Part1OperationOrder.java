package hzt.aoc.day18;

import java.util.List;

public class Part1OperationOrder extends Day18Challenge {

    public Part1OperationOrder() {
        super("part 1",
                "Evaluate the expression on each line of the homework; what is the sum of the resulting values?");
    }

    @Override
    String evaluateBetweenParentheses(final List<String> subEquation) {
        return evaluateInOrder(subEquation);
    }

    @Override
    String getMessage(final long global) {
        return String.format("%d", global);
    }
}
