package hzt.aoc.day16;

import org.hzt.utils.It;
import org.hzt.utils.collections.primitives.IntList;
import org.hzt.utils.sequences.Sequence;

import java.util.List;

public class Part1TicketTranslation extends Day16Challenge {

    public Part1TicketTranslation() {
        super("part 1",
                "Consider the validity of the nearby tickets you scanned. What is your ticket scanning error rate?");
    }

    @Override
    protected long solveByParsedInput(final List<Field> fields, final IntList yourTicketValues, final List<IntList> nearbyTicketValues) {
        final var inValidTicketValues = Sequence.of(nearbyTicketValues)
                .flatMapToInt(It::self)
                .toMutableList();
        inValidTicketValues.removeAll(findValidTicketValues(fields, nearbyTicketValues));
        return inValidTicketValues.sum();
    }

    @Override
    String getMessage(final long global) {
        return String.format("%d", global);
    }

}
