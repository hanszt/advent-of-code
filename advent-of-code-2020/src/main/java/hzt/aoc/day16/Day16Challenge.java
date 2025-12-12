package hzt.aoc.day16;

import hzt.aoc.Challenge;
import org.hzt.utils.collections.primitives.IntList;
import org.hzt.utils.collections.primitives.IntMutableList;
import org.hzt.utils.sequences.Sequence;

import java.util.ArrayList;
import java.util.List;

import static aoc.utils.grid2d.GridPoint2DKt.GridPoint2D;

public abstract class Day16Challenge extends Challenge {

    Day16Challenge(final String challengeTitle, final String description) {
        super(challengeTitle, description, "20201216-input-day16.txt");
    }

    @Override
    protected String solve(final List<String> inputList) {
        final List<Field> fields = new ArrayList<>();
        final var yourTicketValues = IntMutableList.empty();
        final List<IntList> nearbyTicketValues = new ArrayList<>();
        Field.setNext(0);
        var inputPart = 0;
        for (final var s : inputList) {
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

    protected abstract long solveByParsedInput(List<Field> fields, IntList yourTicketValues, List<IntList> nearbyTicketValues);

    private static void addField(final String s, final List<Field> fields) {
        final var array = s.split(": ");
        final var field = new Field(array[0]);
        final var ranges = array[1].split(" or ");
        for (final var range : ranges) {
            final var lowerUpper = range.split("-");
            final var lower = Integer.parseInt(lowerUpper[0]);
            final var upper = Integer.parseInt(lowerUpper[1]);
            field.addRange(GridPoint2D(lower, upper));
        }
        fields.add(field);
    }

    protected IntList findValidTicketValues(final List<Field> fields, final List<IntList> nearbyTicketValues) {
        return Sequence.of(nearbyTicketValues)
                .mapMultiToInt(IntList::forEachInt)
                .filter(value -> fieldsContainValue(value, fields))
                .toList();
    }

    boolean fieldsContainValue(final int value, final List<Field> fields) {
        return fields.stream().anyMatch(field -> field.containsValueInRanges(value));
    }


    abstract String getMessage(long value);
}
