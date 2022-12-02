package hzt.aoc.day09;

import java.util.ArrayList;
import java.util.List;

public class Part2EncodingError extends Day09Challenge {

    public Part2EncodingError() {
        super("part 2",
                "you must find a contiguous set of at least two numbers " +
                        "in your list which sum to the invalid number from step 1. " +
                        "To find the encryption weakness, add together the smallest and largest number in this contiguous range");
    }

    @Override
    protected long solveByXmasList(final List<Long> longs) {
        final long resultStep1 = findFirstNumberNotSumOfTwoIntegersInPreamble(longs);
        final List<Long> contiguousSumList = findSumList(longs, resultStep1);
        return findSumMinAndMaxNumber(contiguousSumList);
    }

    private List<Long> findSumList(final List<Long> longs, final long ref) {
        long sum = 0;
        final List<Long> sumList = new ArrayList<>();
        for (int i = 0; i < longs.size(); i++) {
            for (int j = i; j < longs.size(); j++) {
                sumList.add(longs.get(j));
                sum += longs.get(j);
                if (sum == ref) {
                    return sumList;
                }
            }
            sumList.clear();
            sum = 0;
        }
        return sumList;
    }

    private long findSumMinAndMaxNumber(final List<Long> list) {
        final long min = list.stream().reduce(Long::min).orElse(0L);
        final long max = list.stream().reduce(Long::max).orElse(0L);
        return min + max;
    }

    @Override
    protected String getMessage(final String sum) {
        return String.format("%s%n", sum);
    }
}
