package hzt.aoc.day15;

import hzt.aoc.AocLogger;
import hzt.aoc.Challenge;
import org.hzt.utils.collections.primitives.IntMutableList;

import java.util.List;

public abstract class Day15Challenge extends Challenge {

    private static final AocLogger LOGGER = AocLogger.getLogger(Day15Challenge.class);

    Day15Challenge(final String challengeTitle, final String description) {
        super(challengeTitle, description, "20201215-input-day15.txt");
    }

    @Override
    protected String solve(final List<String> inputList) {
        final var numbers = commaSeparatedStringToIntegerList(inputList.getFirst());
        return String.valueOf(getNthNumberSpoken(IntMutableList.of(numbers)));
    }

    long logTime(final int counter, final int step, final int offset, final int lastNumberSpoken, long start) {
        if (counter % step == offset) {
            LOGGER.info(String.format("size: %9d, last number: %9d, time to calculate: %3.3f ms",
                    counter, lastNumberSpoken, (System.nanoTime() - start) / 1e6));
            start = System.nanoTime();
        }
        return start;
    }

    protected abstract int getNthNumberSpoken(IntMutableList numbers);

}
