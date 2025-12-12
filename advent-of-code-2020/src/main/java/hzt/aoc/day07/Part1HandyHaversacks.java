package hzt.aoc.day07;

import java.util.Map;

public class Part1HandyHaversacks extends Day07Challenge {

    public Part1HandyHaversacks() {
        super("part 1",
                "What is the number of bag colors that can contain" +
                        " at least one shiny gold bag?");
    }

    @Override
    long solveByRules(final Map<String, Bag> bags) {
        return bags.values().stream()
                .filter(bag -> hasDescendent(bags, SHINY_GOLD, bag))
                .count();
    }

    private static boolean hasDescendent(final Map<String, Bag> bags, final String target, final Bag bag) {
        for (final var color : bag.childBagColorsToAmounts().keySet()) {
            if (color.equals(target) || hasDescendent(bags, target, bags.get(color))) {
                return true;
            }
        }
        return false;
    }

@Override
    protected String getMessage(final String numberOfBags) {
        return String.format("The number of bags containing a %s bag at least once: %s%n", SHINY_GOLD, numberOfBags);
    }

}
