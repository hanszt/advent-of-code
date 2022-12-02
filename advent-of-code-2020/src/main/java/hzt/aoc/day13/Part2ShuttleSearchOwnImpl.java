package hzt.aoc.day13;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class Part2ShuttleSearchOwnImpl extends Day13Challenge {

    private static final Pattern X_PATTERN = Pattern.compile("x");
    public Part2ShuttleSearchOwnImpl() {
        super("part 2",
                "What is the earliest timestamp such that all of the listed bus IDs depart at " +
                        "offsets matching their positions in the list? (This is a brute force implementation. Is too slow)",
                "20201213-input-day13ref2.txt");
    }

    @Override
    protected String solve(final List<String> inputList) {
        final List<String> busNrList = Arrays.asList(inputList.get(1).split(","));
        final int highestIndex = busNrList.size() - 1;
        final Map<Integer, Integer> listPositionsToBusNrs = new TreeMap<>();
        for (int i = 0; i < busNrList.size(); i++) {
            if (!X_PATTERN.matcher(busNrList.get(i)).matches()) {
                listPositionsToBusNrs.put(i, Integer.parseInt(busNrList.get(i)));
            }
        }
        BigInteger earliestTimestamp = BigInteger.ZERO;
        while (true) {
            final List<Boolean> matches = new ArrayList<>();
            for (final Map.Entry<Integer, Integer> entry : listPositionsToBusNrs.entrySet()) {
                final int listPosition = entry.getKey();
                final int busNr = entry.getValue();
                final BigInteger value = (earliestTimestamp.mod(BigInteger.valueOf(busNr))
                        .add(BigInteger.valueOf((long) listPosition - highestIndex)));
                matches.add(value.equals(BigInteger.ZERO));
            }
            if (!matches.contains(false)) {
                break;
            }
            earliestTimestamp = earliestTimestamp.add(BigInteger.ONE);
        }
        return String.valueOf(earliestTimestamp.subtract(BigInteger.valueOf(highestIndex)));
    }

    @Override
    protected String getMessage(final String global) {
        return String.format("%s (only works for small inputs)", global);
    }
}
