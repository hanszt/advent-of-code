package hzt.aoc.day16;

import hzt.aoc.Challenge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static aoc.utils.model.GridPoint2DKt.gridPoint2D;

public abstract class Day16Challenge extends Challenge {

    Day16Challenge(final String challengeTitle, final String description) {
        super(challengeTitle, description, "20201216-input-day16.txt");
    }

    @Override
    protected String solve(final List<String> inputList) {
        final List<Field> fields = new ArrayList<>();
        final List<Integer> yourTicketValues = new ArrayList<>();
        final List<List<Integer>> nearbyTicketValues = new ArrayList<>();
        Field.setNext(0);
        int inputPart = 0;
        for (final String s : inputList) {
            if (!s.isBlank()) {
                if ("your ticket:".equals(s)) {
                    inputPart++;
                }
                if ("nearby tickets:".equals(s)) {
                    inputPart++;
                }
                if (inputPart == 0) {
                    addField(s, fields);
                }
                if (inputPart == 1 && !"your ticket:".equals(s)) {
                    yourTicketValues.addAll(commaSeparatedStringToIntegerList(s));
                }
                if (inputPart == 2 && !"nearby tickets:".equals(s)) {
                    nearbyTicketValues.add(commaSeparatedStringToIntegerList(s));
                }
            }
        }
        return getMessage(solveByParsedInput(fields, yourTicketValues, nearbyTicketValues));
    }

    protected abstract long solveByParsedInput(List<Field> fields, List<Integer> yourTicketValues, List<List<Integer>> nearbyTicketValues);

    private static void addField(final String s, final List<Field> fields) {
        final String[] array = s.split(": ");
        final Field field = new Field(array[0]);
        final String[] ranges = array[1].split(" or ");
        for (final String range : ranges) {
            final String[] lowerUpper = range.split("-");
            final int lower = Integer.parseInt(lowerUpper[0]);
            final int upper = Integer.parseInt(lowerUpper[1]);
            field.addRange(gridPoint2D(lower, upper));
        }
        fields.add(field);
    }

    protected List<Integer> findValidTicketValues(final List<Field> fields, final List<List<Integer>> nearbyTicketValues) {
        return nearbyTicketValues.stream().flatMap(Collection::stream)
                .filter(value -> fieldsContainValue(value, fields))
                .toList();
    }

    boolean fieldsContainValue(final int value, final List<Field> fields) {
        return fields.stream().anyMatch(field -> field.containsValueInRanges(value));
    }


    abstract String getMessage(long value);
}
