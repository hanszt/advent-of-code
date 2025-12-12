package hzt.aoc.day13;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Part1ShuttleSearch extends Day13Challenge {

    public Part1ShuttleSearch() {
        super("part 1",
                "What is the ID of the earliest bus you can take to the airport " +
                        "multiplied by the number of minutes you'll need to wait for that bus?");
    }

    @Override
    protected String solve(final List<String> inputList) {
        final var earliestTimestamp = Integer.parseInt(inputList.get(0));
        final var busNumbersToWaitingTimes = Arrays.stream(inputList.get(1).split(","))
                .filter(s -> !s.matches("x"))
                .map(Integer::parseInt)
                .collect(Collectors.toMap(busNr -> busNr, busNr -> busNr - earliestTimestamp % busNr));
        var timeToWaitForEarliestBus = Integer.MAX_VALUE;
        var busNumberBelongingToSmallest = 0;
        for (final var entry : busNumbersToWaitingTimes.entrySet()) {
            if (entry.getValue() < timeToWaitForEarliestBus) {
                timeToWaitForEarliestBus = entry.getValue();
                busNumberBelongingToSmallest = entry.getKey();
            }
        }
        return String.valueOf(timeToWaitForEarliestBus * busNumberBelongingToSmallest);
    }

}
