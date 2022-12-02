package hzt.aoc.day01;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

public class Part1ReportRepair extends Day01Challenge {

    public Part1ReportRepair() {
        super("part 1",
                "Find the product of the two entries that sum to 2020");
    }

    @Override
    protected List<Integer[]> findIntegersListThatSumTo2020(final SortedSet<Integer> integers) {
        final List<Integer[]> entriesList = new ArrayList<>();
        for (final Integer integer : integers) {
            final int difference = SUM_TO_BE_FOUND - integer;
            if (integers.contains(difference)) {
                final Integer[] entries = {integer, difference};
                entriesList.add(entries);
                break;
            }
        }
        return entriesList;
    }
}
