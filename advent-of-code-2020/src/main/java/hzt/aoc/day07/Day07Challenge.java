package hzt.aoc.day07;

import hzt.aoc.Challenge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class Day07Challenge extends Challenge {

    static final String SHINY_GOLD = "shiny gold";

    protected Day07Challenge(final String challengeTitle, final String description) {
        super(challengeTitle, description, "20201207-input-day7.txt");
    }

    @Override
    protected String solve(final List<String> inputList) {
        final Map<String, Bag> bagColorsToRule = inputList.stream()
                .map(this::extractBagFromLine)
                .collect(Collectors.toMap(bag -> bag.bagColor, bag -> bag));
        final long numberOfBags = solveByRules(bagColorsToRule);
        return String.valueOf(numberOfBags);
    }

    protected abstract long solveByRules(Map<String, Bag> bags);

    Bag extractBagFromLine(final String line) {
        final String[] containerToContent = line.split(Pattern.quote(" bags contain "));
        final Bag currentBag = new Bag(containerToContent[0]);
        final String content = containerToContent[1];
        if (!"no other bags.".equals(content)) {
            final String[] rulesAsStrings = content.split(Pattern.quote(", "));
            for (final String string : rulesAsStrings) {
                final String stringAmount = string.replaceAll(NOT_DIGIT_LENGTH_ONE_OR_MORE.pattern(), "");
                final int amount = Integer.parseInt(stringAmount);
                final String bagColor = string.replaceAll(NUMBER_LENGTH_ONE_OR_MORE.pattern(), "")
                        .split(Pattern.quote(" bag"))[0].strip(); // strip white spaces from trailing edges
                currentBag.addColorToAmount(bagColor, amount);
            }
        }
        return currentBag;
    }

    static class Bag {

        final String bagColor;
        final Map<String, Integer> childBagColorsToAmounts = new HashMap<>();

        public Bag(final String bagColor) {
            this.bagColor = bagColor;
        }

        void addColorToAmount(final String bagColor, final int amount) {
            childBagColorsToAmounts.put(bagColor, amount);
        }

        @Override
        public String toString() {
            return "Rule{" +
                    "bagColor='" + bagColor + '\'' +
                    ", childBagColorsToAmounts=" + childBagColorsToAmounts +
                    '}';
        }
    }

}
