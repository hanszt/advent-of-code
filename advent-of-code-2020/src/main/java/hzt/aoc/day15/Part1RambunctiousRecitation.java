package hzt.aoc.day15;

import org.hzt.utils.collections.primitives.IntMutableList;

public class Part1RambunctiousRecitation extends Day15Challenge {

    private static final int THRESHOLD = 2020;

    public Part1RambunctiousRecitation() {
        super("part 1",
                "Given your starting numbers, what will be the 2020th number spoken?");
    }

    @Override
    protected int getNthNumberSpoken(final IntMutableList numbers) {
        var last = 0;
        var start = System.nanoTime();
        while (numbers.size() < THRESHOLD) {
            final var prevLast = numbers.removeLast();
            var newLast = 0;
            for (var index = numbers.size() - 1; index >= 0; index--) {
                if (numbers.get(index) == prevLast) {
                    newLast = numbers.size() - index;
                    break;
                }
            }
            last = newLast;
            numbers.add(prevLast);
            numbers.add(last);
            start = logTime(numbers.size(), 200, 20, last, start);
        }
        return last;
    }

    @Override
    protected String getMessage(final String answer) {
        return String.format("The %dth number spoken is: %s", THRESHOLD, answer);
    }

}
