package hzt.aoc.day01;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;

public class Part1ReportRepair extends Day01Challenge {

    public Part1ReportRepair() {
        super("part 1",
                "Find the product of the two entries that sum to 2020");
    }

    @Override
    protected List<int[]> findIntegersListThatSumTo2020(final NavigableSet<Integer> integers) {
        final List<int[]> entriesList = new ArrayList<>();
        for (final var integer : integers) {
            final int difference = SUM_TO_BE_FOUND - integer;
            if (integers.contains(difference)) {
                final int[] entries = {integer, difference};
                entriesList.add(entries);
                break;
            }
        }
        return entriesList;
    }
}
