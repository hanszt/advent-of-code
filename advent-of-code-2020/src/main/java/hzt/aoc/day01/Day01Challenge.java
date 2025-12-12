package hzt.aoc.day01;

import hzt.aoc.Challenge;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Day01Challenge extends Challenge {

    static final int SUM_TO_BE_FOUND = 2020;

    private final List<int[]> integersThatSumTo2020List = new ArrayList<>();

    protected Day01Challenge(final String challenge, final String description) {
        super(challenge, description, "20201201-input-day1.txt");
    }

    @Override
    protected String solve(final List<String> inputList) {
        final var integers = inputList.stream().map(Integer::parseInt).collect(Collectors.toSet());
        integersThatSumTo2020List.clear();
        integersThatSumTo2020List.addAll(findIntegersListThatSumTo2020(new TreeSet<>(integers)));
        LOGGER.trace(() -> getMessage(integersThatSumTo2020List));
        return String.valueOf(calculateProduct(integersThatSumTo2020List.getFirst()));
    }

    protected abstract List<int[]> findIntegersListThatSumTo2020(NavigableSet<Integer> integers);

    public String getMessage(final List<int[]> integersThatSumTo2020List) {
        final var sb = new StringBuilder();
        final var message = String.format("Output size: %d%n", integersThatSumTo2020List.size());
        sb.append(message);
        for (final var entries : integersThatSumTo2020List) {
            final var isb = new StringBuilder();
            long product = 1;
            for (final Integer integer : entries) {
                isb.append(integer).append(", ");
                product *= integer;
            }
            final var result = String.format("The %d digits from the list that sum to %d are: %s%nThe product of these digits is: %d%n",
                    entries.length, SUM_TO_BE_FOUND, isb, product);
            sb.append(result);
        }
        return sb.toString();
    }

    private long calculateProduct(final int[] entries) {
        return Arrays.stream(entries).reduce(1, (product, i) -> product * i);
    }

    @Override
    protected String getMessage(final String message) {
        return getMessage(integersThatSumTo2020List);
    }

}
