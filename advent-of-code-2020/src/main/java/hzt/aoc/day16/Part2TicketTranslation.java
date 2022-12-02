package hzt.aoc.day16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// credits to turkey dev
public class Part2TicketTranslation extends Day16Challenge {

    private static final int FIRST_SIX_FIELDS = 6;

    public Part2TicketTranslation() {
        super("part 2",
                "Once you work out which field is which, look for the six fields on your ticket that start with the word departure. \n" +
                        "What do you get if you multiply those six values together?");
    }

    @Override
    protected long solveByParsedInput(final List<Field> fields,
                                      final List<Integer> ourTicketValues,
                                      final List<List<Integer>> nearbyTickets) {
        final boolean[][] possibleMatches = new boolean[fields.size()][ourTicketValues.size()];
        for (final boolean[] possibleMatch : possibleMatches) {
            Arrays.fill(possibleMatch, true);
        }
        final List<List<Integer>> validTickets = findValidTickets(fields, nearbyTickets);
        for (final List<Integer> ticket : validTickets) {
            for (int col = 0; col < ticket.size(); col++) {
                for (int row = 0; row < fields.size(); row++) {
                    if (!fields.get(row).containsValueInRanges(ticket.get(col))) {
                        possibleMatches[row][col] = false;
                    }
                }
            }
        }
        LOGGER.trace(ourTicketValues);

        iterateUntilUniqueValueForeEachField(possibleMatches);
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(booleanGrid2DAsString(possibleMatches));
        }
        return getAnswer(possibleMatches, ourTicketValues);
    }

    private void iterateUntilUniqueValueForeEachField(final boolean[][] possibleMatches) {
        while (!isDone(possibleMatches)) {
            filterOutUniqueValues(possibleMatches);
        }
    }

    public void filterOutUniqueValues(final boolean[][] possibleMatches) {
        for (int col = 0; col < possibleMatches[0].length; col++) {
            int count = 0;
            int index = -1;
            for (int row = 0; row < possibleMatches.length; row++) {
                if (possibleMatches[col][row]) {
                    count++;
                    index = row;
                }
            }
            if (count == 1) {
                for (int i = 0; i < possibleMatches.length; i++) {
                    if (i != col) {
                        possibleMatches[i][index] = false;
                    }
                }
            }
        }
    }

    public boolean isDone(final boolean[][] possibleMatches) {
        for (final boolean[] possibleMatch : possibleMatches) {
            int matches = 0;
            for (final boolean match : possibleMatch) {
                if (match) {
                    matches++;
                }
            }
            if (matches > 1) {
                return false;
            }
        }
        return true;
    }

    protected List<List<Integer>> findValidTickets(final List<Field> fields, final List<List<Integer>> nearbyTickets) {
        final List<List<Integer>> validTickets = new ArrayList<>();
        for (final List<Integer> nearbyTicket : nearbyTickets) {
            final boolean containsOnlyValidValues = nearbyTicket.stream().allMatch(value -> fieldsContainValue(value, fields));
            if (containsOnlyValidValues) {
                validTickets.add(nearbyTicket);
            }
        }
        return validTickets;
    }

    private static long getAnswer(final boolean[][] possibleMatches, final List<Integer> ourTicketValues) {
        long answer = 1;
        for (int row = 0; row < FIRST_SIX_FIELDS; row++) {
            for (int col = 0; col < possibleMatches.length; col++) {
                if (possibleMatches[row][col]) {
                    final int value = ourTicketValues.get(col);
                    answer *= value;
                    break;
                }
            }
        }
        return answer;
    }

    @Override
    String getMessage(final long global) {
        return String.format("%d", global);
    }
}
