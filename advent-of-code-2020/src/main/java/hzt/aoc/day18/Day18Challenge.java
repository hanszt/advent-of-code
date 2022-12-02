package hzt.aoc.day18;

import hzt.aoc.Challenge;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Day18Challenge extends Challenge {

    Day18Challenge(final String challengeTitle, final String description) {
        super(challengeTitle, description, "20201218-input-day18.txt");
    }

    long calculateAnswer(final String equationAString) {
        LOGGER.trace("Input equation: " + equationAString);
        return solveEquation(parseEquation(equationAString));
    }

    @Override
    protected String solve(final List<String> inputList) {
        final long sumAnswers = inputList.stream().map(this::calculateAnswer).reduce(Long::sum).orElse(0L);
        return getMessage(sumAnswers);
    }

    List<String> parseEquation(final String equationAString) {
        return equationAString.replaceAll("\\s", "")
                .chars().mapToObj(c -> (char) c)
                .map(String::valueOf)
                .collect(Collectors.toList());
    }

    private long solveEquation(List<String> elementList) {
        String result;
        while (elementList.contains("(")) {
            int indexOpenBracket = 0;
            final List<String> newList = new ArrayList<>(elementList);
            for (int i = 0; i < elementList.size(); i++) {
                if (elementList.get(i).equals("(")) {
                    indexOpenBracket = i;
                } else if (elementList.get(i).equals(")")) {
                    final List<String> subList = elementList.subList(indexOpenBracket + 1, i);
                    LOGGER.trace(subList);
                    result = evaluateBetweenParentheses(subList);
                    replaceEquationBySubResult(newList, result, indexOpenBracket, subList.size() + 1);
                    newList.remove(indexOpenBracket + 1);
                    LOGGER.trace(result);
                    break;
                }
            }
            elementList = newList;
        }
        result = evaluateBetweenParentheses(elementList);
        LOGGER.trace(result);
        return Long.parseLong(result);
    }

    void replaceEquationBySubResult(final List<String> newList, final String subResult, final int index, final int equationLength) {
        int j = 0;
        while (j < equationLength) {
            newList.remove(index);
            j++;
        }
        newList.add(index, subResult);
    }

    abstract String evaluateBetweenParentheses(List<String> strings);

    String evaluateInOrder(final List<String> subEquation) {
        final Deque<String> elementDeque = new ArrayDeque<>(subEquation);
        String result = "";
        while (!elementDeque.isEmpty()) {
            final String num1 = result.isEmpty() ? elementDeque.pollFirst() : result;
            if (elementDeque.size() > 1) {
                final String operator = elementDeque.pollFirst();
                final String num2 = elementDeque.pollFirst();
                assert num2 != null;
                final long intResult = evaluate(Long.parseLong(num1), operator, Long.parseLong(num2));
                result = String.valueOf(intResult);
            }
        }
        return result;
    }

    long evaluate(final long first, final String operator, final long second) {
        if (operator.equals("+")) {
            return first + second;
        } else if (operator.equals("*")) {
            return first * second;
        } else {
            throw new UnsupportedOperationException("Operator " + operator + " is not supported...");
        }
    }

    abstract String getMessage(long value);
}
