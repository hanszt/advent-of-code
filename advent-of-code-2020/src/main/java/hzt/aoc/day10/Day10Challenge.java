package hzt.aoc.day10;

import hzt.aoc.Challenge;
import org.hzt.utils.sequences.Sequence;

import java.util.List;

public abstract class Day10Challenge extends Challenge {

    static final int MAX_STEP_APART = 3;

    Day10Challenge(final String challengeTitle, final String description) {
        super(challengeTitle, description, "20201210-input-day10.txt");
    }

    Day10Challenge(final String challengeTitle, final String description, final String inputFilename) {
        super(challengeTitle, description, inputFilename);
    }


    @Override
    protected String solve(final List<String> inputList) {
        final var list = Sequence.of(inputList)
                .filterNot(String::isEmpty)
                .mapToInt(Integer::parseInt)
                .sorted()
                .toMutableList();

        list.add(0, 0); // add socket jolt value
        list.add(list.last() + MAX_STEP_APART); // add built in phone adaptor jolt value
        return String.valueOf(solveByArray(list.toArray()));
    }

    protected abstract Number solveByArray(int[] array);


    long calculateTheProductBetweenOneAndThreeDifference(final int[] sortedArray) {
        long oneDifference = 0;
        long threeDifference = 0;
        for (int i = 0; i < sortedArray.length - 1; i++) {
            final int difference = sortedArray[i + 1] - sortedArray[i];
            if (difference == 1) {
                oneDifference++;
            }
            if (difference == MAX_STEP_APART) {
                threeDifference++;
            }
        }
        return oneDifference * threeDifference;
    }

}
