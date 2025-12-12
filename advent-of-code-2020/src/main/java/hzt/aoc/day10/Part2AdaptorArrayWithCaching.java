package hzt.aoc.day10;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// credits to TurkeyDev
public class Part2AdaptorArrayWithCaching extends Day10Challenge {

    private final Map<String, BigInteger> cache = new HashMap<>();

    public Part2AdaptorArrayWithCaching() {
        super("part 2 with caching using bigInteger",
                "What is the total number of distinct ways " +
                        "you can arrange the adapters to connect the charging outlet to your device?");
    }

    @Override
    protected Number solveByArray(final int[] array) {
        return numberOfWaysToCompleteAdaptorChain(array);
    }
    //improves runtime: Allows to skip parts of the branches in the tree to be recursively walk through.,,

    private BigInteger numberOfWaysToCompleteAdaptorChain(final int[] sortedArray) {
        if (sortedArray.length == 1) {
            return BigInteger.ONE;
        }
        var arrangements = BigInteger.ZERO;
        var index = 1;
        final var current = sortedArray[0];

        while (sortedArray.length > index && sortedArray[index] - current <= MAX_STEP_APART) {
            final var subArray = Arrays.copyOfRange(sortedArray, index, sortedArray.length);
            final var stringSubList = Arrays.toString(subArray);
            if (!cache.containsKey(stringSubList)) {
                final var subArrangements = numberOfWaysToCompleteAdaptorChain(subArray);
                cache.put(stringSubList, subArrangements);
                arrangements = arrangements.add(subArrangements);
            } else {
                arrangements = arrangements.add(cache.get(stringSubList));
            }
            index++;
        }
        return arrangements;
    }

    @Override
    protected String getMessage(final String number) {
        return String.format("The number of distinct ways to connect your adaptor is: %s%n", number);
    }
}
