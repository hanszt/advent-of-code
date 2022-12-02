package hzt.aoc.day18;

import java.util.ArrayList;
import java.util.List;

public class Part2OperationOrder extends Day18Challenge {

    public Part2OperationOrder() {
        super("part 2",
                "What do you get if you add up the results of evaluating the homework problems using these new rules?");
    }

    private static final int EVALUATION_LENGTH = 3;
    private static final String OPERATOR_TO_EVALUATE_FIRST = "+";


    @Override
    String evaluateBetweenParentheses(List<String> elementList) {
        String subResult = "0";
        List<String> newList;
        while (elementList.contains(OPERATOR_TO_EVALUATE_FIRST)) {
            newList = new ArrayList<>(elementList);
            for (int i = 0; i < elementList.size(); i++) {
                if (elementList.get(i).equals(OPERATOR_TO_EVALUATE_FIRST)) {
                    subResult = parseAndCalculateSubResult(elementList, i);
                    replaceEquationBySubResult(newList, subResult, i - 1, EVALUATION_LENGTH);
                    break;
                }
            }
            elementList = newList;
            LOGGER.trace(elementList);
        }
        if (elementList.size() > 1) {
            subResult = evaluateInOrder(elementList);
        }
        LOGGER.trace("Sub result part 2: " + subResult);
        return subResult;
    }

    private String parseAndCalculateSubResult(final List<String> elementList, final int index) {
        final long first = Long.parseLong(elementList.get(index - 1));
        final long second = Long.parseLong(elementList.get(index + 1));
        final long longSubResult = evaluate(first, OPERATOR_TO_EVALUATE_FIRST, second);
        return String.valueOf(longSubResult);
    }

    @Override
    String getMessage(final long global) {
        return String.format("%d", global);
    }
}
