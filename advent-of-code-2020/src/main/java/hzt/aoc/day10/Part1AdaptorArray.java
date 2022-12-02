package hzt.aoc.day10;

import java.util.List;

public class Part1AdaptorArray extends Day10Challenge {

    public Part1AdaptorArray() {
        super("part 1",
                "Find a chain that uses all of your adapters to connect the charging outlet to " +
                        "your device's built-in adapter and count the joltage differences between the charging outlet, " +
                        "the adapters, and your device. " +
                        "What is the number of 1-jolt differences multiplied by the number of 3-jolt differences?");
    }

    @Override
    protected Number solveByList(final List<Integer> sortedlist) {
        return calculateTheProductBetweenOneAndThreeDifference(sortedlist);
    }

    @Override
    protected String getMessage(final String number) {
        return String.format("The number of 1-jolt differences multiplied by the number of 3-jolt differences is: %s", number);
    }

}
