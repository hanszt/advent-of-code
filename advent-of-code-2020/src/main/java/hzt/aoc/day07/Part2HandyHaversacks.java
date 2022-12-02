package hzt.aoc.day07;

import java.util.Map;

public class Part2HandyHaversacks extends Day07Challenge {

    public Part2HandyHaversacks() {
        super("part 2",
                "How many individual bags are required inside your single shiny gold bag?");
    }

    @Override
    protected long solveByRules(final Map<String, Bag> bags) {
        // We counted the target bag, reduce count by 1.
        return !bags.isEmpty() ? (countInnerBagsRecursive(bags, bags.get(SHINY_GOLD)) - 1) : 0;
    }

    private static long countInnerBagsRecursive(final Map<String, Bag> bags, final Bag bag) {
        long sum = 1L;
        for (final var entry : bag.childBagColorsToAmounts.entrySet()) {
            sum += entry.getValue() * countInnerBagsRecursive(bags, bags.get(entry.getKey()));
        }
        return sum;
    }

    @Override
    public String getMessage(final String numberOfBags) {
        return String.format("The number of individual bags required inside the %s bag: %s%n", SHINY_GOLD, numberOfBags);
    }
}
