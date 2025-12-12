package hzt.aoc.day01;

import java.util.*;

public class Part2ReportRepair extends Day01Challenge {

    public Part2ReportRepair() {
        super("part 2", "Find the product of the three digits that sum to 2020");
    }

    @Override
    protected List<int[]> findIntegersListThatSumTo2020(final NavigableSet<Integer> integers) {
        final Set<Integer> usedIntegers = new HashSet<>();
        final List<Integer> integerList = new ArrayList<>(integers);
        final List<int[]> entriesList = new ArrayList<>();
        for (var i = 0; i < integerList.size(); i++) {
            for (var j = i + 1; j < integerList.size(); j++) {
                final var difference = SUM_TO_BE_FOUND - integerList.get(i) - integerList.get(j);
                if (!usedIntegers.contains(integerList.get(i)) && !usedIntegers.contains(integerList.get(j))
                        && integers.contains(difference)) {
                    final int[] threeEntries = {integerList.get(i), integerList.get(j), difference};
                    entriesList.add(threeEntries);
                    usedIntegers.add(integerList.get(i));
                    usedIntegers.add(integerList.get(j));
                    break;
                }
            }
        }
        return entriesList;
    }
}
