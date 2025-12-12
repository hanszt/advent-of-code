package hzt.aoc.day07;

import hzt.aoc.Challenge;
import org.hzt.graph.TreeNode;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class Day07Challenge extends Challenge {

    static final String SHINY_GOLD = "shiny gold";

    protected Day07Challenge(final String challengeTitle, final String description) {
        super(challengeTitle, description, "20201207-input-day7.txt");
    }

    @Override
    protected String solve(final List<String> inputList) {
        final var bagColorsToBag = getColorBagMap(inputList);

        final var numberOfBags = solveByRules(bagColorsToBag);
        return String.valueOf(numberOfBags);
    }

    Map<String, Bag> getColorBagMap(final List<String> inputList) {
        final var bagColorsToBag = inputList.stream()
                .map(this::extractBagFromLine)
                .collect(Collectors.toMap(bag -> bag.bagColor, bag -> bag));

        bagColorsToBag.values()
                .forEach(b -> b.childBagColorsToAmounts.forEach((c, i) -> b.children().add(bagColorsToBag.get(c))));
        return bagColorsToBag;
    }

    abstract long solveByRules(Map<String, Bag> bags);

    Bag extractBagFromLine(final String line) {
        final var containerToContent = line.split(Pattern.quote(" bags contain "));
        final var currentBag = new Bag(containerToContent[0]);
        final var content = containerToContent[1];
        if (!"no other bags.".equals(content)) {
            final var rulesAsStrings = content.split(Pattern.quote(", "));
            for (final var string : rulesAsStrings) {
                final var stringAmount = string.replaceAll(NOT_DIGIT_LENGTH_ONE_OR_MORE.pattern(), "");
                final var amount = Integer.parseInt(stringAmount);
                final var bagColor = string.replaceAll(NUMBER_LENGTH_ONE_OR_MORE.pattern(), "")
                        .split(Pattern.quote(" bag"))[0].strip(); // strip white spaces from trailing edges
                currentBag.childBagColorsToAmounts.put(bagColor, amount);
            }
        }
        return currentBag;
    }

    record Bag(String bagColor, Map<String, Integer> childBagColorsToAmounts, List<Bag> children) implements TreeNode<Bag> {

        public Bag(final String bagColor) {
            this(bagColor, new HashMap<>(), new ArrayList<>());
        }

        @Override
        public Iterator<Bag> childrenIterator() {
            return children.iterator();
        }

        @Override
        public String toString() {
            return "Bag{" +
                    "bagColor='" + bagColor + '\'' +
                    ", childBagColorsToAmounts=" + childBagColorsToAmounts +
                    '}';
        }
    }

}
