package hzt.aoc.day23;

import org.hzt.utils.collections.primitives.IntList;

public class Part1CrabCups extends Day23Challenge {

    private static final int NR_OF_MOVES = 100;

    private static final int CUP_ONE_LABEL = 1;

    public Part1CrabCups() {
        super("part 1",
                "Using your labeling, simulate 100 moves. What are the labels on the cups after cup 1?");
    }

    @Override
    protected long calculateAnswer(final IntList cups) {
        final int lowestCupLabel = cups.min();
        final int highestCupLabel = cups.max();
        final var mutableCups = cups.toMutableList();
        int indexCurrent = 0;
        for (int i = 0; i < NR_OF_MOVES; i++) {
            final int currentCupLabel = mutableCups.get(indexCurrent);
            final var threePickedUpCups = listPickedUpCups(indexCurrent, mutableCups);
            mutableCups.removeAll(threePickedUpCups);
            final int targetCupLabel = determineTargetCupLabel(currentCupLabel, lowestCupLabel, highestCupLabel, mutableCups);
            final int targetIndex = getIndexByLabel(targetCupLabel, mutableCups);
            mutableCups.addAll(targetIndex + 1, threePickedUpCups);
            final var newIndex = getIndexByLabel(currentCupLabel, mutableCups);
            indexCurrent = ((newIndex + 1) < mutableCups.size()) ? (newIndex + 1) : 0;
        }
        return arrangeInOrder(mutableCups);
    }

    private long arrangeInOrder(final IntList cups) {
        final int indexCupOne = getIndexByLabel(CUP_ONE_LABEL, cups);
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < cups.size() - 1; i++) {
            stringBuilder.append(cups.get((indexCupOne + 1 + i) % cups.size()));
        }
        return Long.parseLong(stringBuilder.toString());
    }

    @Override
    String getMessage(final long global) {
        return String.format("%d", global);
    }

}
