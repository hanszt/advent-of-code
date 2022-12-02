package hzt.aoc.day16;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Part1TicketTranslation extends Day16Challenge {

    public Part1TicketTranslation() {
        super("part 1",
                "Consider the validity of the nearby tickets you scanned. What is your ticket scanning error rate?");
    }

    @Override
    protected long solveByParsedInput(final List<Field> fields, final List<Integer> yourTicketValues, final List<List<Integer>> nearbyTicketValues) {
        final List<Integer> inValidTicketValues = nearbyTicketValues.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        inValidTicketValues.removeAll(findValidTicketValues(fields, nearbyTicketValues));
        return inValidTicketValues.stream().reduce(Integer::sum).orElse(0);
    }

    @Override
    String getMessage(final long global) {
        return String.format("%d", global);
    }

}
