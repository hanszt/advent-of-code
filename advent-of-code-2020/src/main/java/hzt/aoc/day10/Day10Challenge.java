package hzt.aoc.day10;

import hzt.aoc.Challenge;

import java.util.List;
import java.util.stream.Collectors;

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
        final List<Integer> list = inputList.stream()
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .sorted()
                .collect(Collectors.toList());

        list.add(0, 0); // add socket jolt value
        list.add(list.get(list.size() - 1) + MAX_STEP_APART); // add built in phone adaptor jolt value
        return String.valueOf(solveByList(list));
    }

    protected abstract Number solveByList(List<Integer> list);


    long calculateTheProductBetweenOneAndThreeDifference(final List<Integer> sortedlist) {
        long oneDifference = 0;
        long threeDifference = 0;
        for (int i = 0; i < sortedlist.size() - 1; i++) {
            final int difference = sortedlist.get(i + 1) - sortedlist.get(i);
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
