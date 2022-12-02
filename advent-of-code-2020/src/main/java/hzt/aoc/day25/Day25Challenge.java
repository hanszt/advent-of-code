package hzt.aoc.day25;

import hzt.aoc.Challenge;

import java.util.List;

public abstract class Day25Challenge extends Challenge {

    static final int NUMBER_TO_DIVIDE_BY = 2020_12_27;
    static final int INIT_SUBJECT_NUMBER = 7;

    Day25Challenge(final String challengeTitle, final String description) {
        super(challengeTitle, description, "20201225-input-day25.txt");
    }

    @Override
    protected String solve(final List<String> inputList) {
        final long cardPublicKey = Long.parseLong(inputList.get(0));
        final long doorPublicKey = Long.parseLong(inputList.get(1));
        return getMessage(solveByInput(cardPublicKey, doorPublicKey));

    }

    protected abstract long solveByInput(long cardPublicKey, long doorPublicKey);


    abstract String getMessage(long value);
}
