package hzt.aoc.day23;

import hzt.aoc.Challenge;
import org.hzt.utils.collections.primitives.IntList;
import org.hzt.utils.sequences.primitives.IntSequence;

import java.util.List;

public abstract class Day23Challenge extends Challenge {
    private static final int CUPS_PICKED_UP = 3;

    Day23Challenge(final String challengeTitle, final String description) {
        super(challengeTitle, description, "20201223-input-day23.txt");
    }

    @Override
    protected String solve(final List<String> inputList) {
        final var integers = IntList.of(inputList.getFirst().chars()
                .map(Character::getNumericValue)
                .toArray());

        return getMessage(calculateAnswer(integers));
    }

    protected abstract long calculateAnswer(IntList integers);

    int determineTargetCupLabel(final int currentCupLabel,
                                final int lowestCupLabel,
                                final int highestCupLabel,
                                final IntList cups) {
        int targetCupLabel = currentCupLabel - 1;
        while (!cups.contains(targetCupLabel)) {
            targetCupLabel--;
            if (targetCupLabel < lowestCupLabel) {
                targetCupLabel = highestCupLabel;
            }
        }
        return targetCupLabel;
    }

    IntList listPickedUpCups(final int indexCurrent, final IntList cups) {
        return IntSequence.iterate(indexCurrent + 1, i -> i + 1)
                .takeWhile(index -> index <= indexCurrent + CUPS_PICKED_UP)
                .map(index -> cups.get(index % cups.size()))
                .toList();
    }

    int getIndexByLabel(final int label, final IntList cups) {
        int index = 0;
        for (int i = 0; i < cups.size(); i++) {
            if (cups.get(i) == label) {
                index = i;
                break;
            }
        }
        return index;
    }

    abstract String getMessage(long value);
}
