package hzt.aoc.day10;

import java.math.BigInteger;
import java.util.Arrays;

public class Part2AdaptorArrayWithoutCaching extends Day10Challenge {

    public Part2AdaptorArrayWithoutCaching() {
        super("part 2 without caching",
                "What is the total number of distinct ways " +
                        "you can arrange the adapters to connect the charging outlet to your device?",
                "20201210-input-day10ref2.txt");
    }

    @Override
    protected Number solveByArray(final int[] array) {
        return numberOfWaysToCompleteAdaptorChain(array);
    }

    private static BigInteger numberOfWaysToCompleteAdaptorChain(final int[] sortedArray) {
        if (sortedArray.length == 1) {
            return BigInteger.ONE;
        }
        var arrangements = BigInteger.ZERO;
        var index = 1;
        final var current = sortedArray[0]; // first index in sorted list
        while (sortedArray.length > index && sortedArray[index] - current <= MAX_STEP_APART) {
            final var subArrangements = numberOfWaysToCompleteAdaptorChain(Arrays.copyOfRange(sortedArray, index, sortedArray.length));
            arrangements = arrangements.add(subArrangements);
            index++;
        }
        return arrangements;
    }

    @Override
    protected String getMessage(final String number) {
        return String.format("The number of distinct ways to connect your adaptor is (Only works for small input set): %s%n", number);
    }
}
