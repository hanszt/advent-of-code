package hzt.aoc.day18;

import hzt.aoc.Challenge;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public abstract class Day18Challenge extends Challenge {

    Day18Challenge(final String challengeTitle, final String description) {
        super(challengeTitle, description, "20201218-input-day18.txt");
    }

    long calculateAnswer(final String equationAString) {
        LOGGER.trace(() -> "Input equation: " + equationAString);
        return solveEquation(parseEquation(equationAString));
    }

    @Override
    protected String solve(final List<String> inputList) {
        final long sumAnswers = inputList.stream().map(this::calculateAnswer).reduce(Long::sum).orElse(0L);
        return getMessage(sumAnswers);
    }

    List<String> parseEquation(final String equationAString) {
        return equationAString.replaceAll("\\s", "")
                .chars()
                .mapToObj(c -> (char) c)
                .map(String::valueOf)
                .toList();
    }

    private long solveEquation(final List<String> elementList) {
        var results = elementList;
        while (results.contains("(")) {
            var indexOpenBracket = 0;
            final List<String> newList = new ArrayList<>(results);
            for (var i = 0; i < results.size(); i++) {
                if (results.get(i).equals("(")) {
                    indexOpenBracket = i;
                } else if (results.get(i).equals(")")) {
                    final var subList = results.subList(indexOpenBracket + 1, i);
                    LOGGER.trace(() -> subList);
                    final var result = evaluateBetweenParentheses(subList);
                    replaceEquationBySubResult(newList, result, indexOpenBracket, subList.size() + 1);
                    newList.remove(indexOpenBracket + 1);
                    LOGGER.trace(() -> result);
                    break;
                }
            }
            results = newList;
        }
        final var result = evaluateBetweenParentheses(results);
        LOGGER.trace(() -> result);
        return Long.parseLong(result);
    }

    void replaceEquationBySubResult(final List<String> newList, final String subResult, final int index, final int equationLength) {
        var j = 0;
        while (j < equationLength) {
            newList.remove(index);
            j++;
        }
        newList.add(index, subResult);
    }

    abstract String evaluateBetweenParentheses(List<String> strings);

    String evaluateInOrder(final List<String> subEquation) {
        final Deque<String> elementDeque = new ArrayDeque<>(subEquation);
        var result = "";
        while (!elementDeque.isEmpty()) {
            final var num1 = result.isEmpty() ? elementDeque.pollFirst() : result;
            if (elementDeque.size() > 1) {
                final var operator = elementDeque.pollFirst();
                final var num2 = elementDeque.pollFirst();
                assert num2 != null;
                final var intResult = evaluate(Long.parseLong(num1), operator, Long.parseLong(num2));
                result = String.valueOf(intResult);
            }
        }
        return result;
    }

    long evaluate(final long first, final String operator, final long second) {
        return switch (operator) {
            case "+" -> first + second;
            case "*" -> first * second;
            default -> throw new UnsupportedOperationException("Operator " + operator + " is not supported...");
        };
    }

    abstract String getMessage(long value);
}
