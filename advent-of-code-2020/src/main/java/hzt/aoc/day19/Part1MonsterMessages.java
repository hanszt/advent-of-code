package hzt.aoc.day19;

import java.util.ArrayList;
import java.util.List;

// credits to Turkey dev
// We used the principle of bnf's here
public class Part1MonsterMessages extends Day19Challenge {

    public Part1MonsterMessages() {
        super("part 1",
                "How many messages completely match rule 0? (without loops)");
    }

    @Override
    protected long countMatches() {
        LOGGER.trace(() -> parsedInputAsString(rulesToSubRules, messages));
        return messages.stream()
                .map(Part1MonsterMessages::asCharList)
                .filter(this::matches)
                .count();
    }

    private static List<Character> asCharList(final String message) {
        return message.chars()
                .mapToObj(c -> (char) c)
                .collect(ArrayList::new, List::add, List::addAll);
    }

    private boolean matches(final List<Character> messageChars) {
        // chars has to be empty to match the same length after going through matches method
        return matches(messageChars, START_RULE) && messageChars.isEmpty();
    }

    // requires a mutableList so that's why a list of chars is passed instead of a string
    private boolean matches(final List<Character> messageChars, final int rule) {
        if (endChars.containsKey(rule)) {
            return ruleIsEndRule(rule, messageChars);
        }

        final var allSubRules = rulesToSubRules.get(rule);
        for (final var subRules : allSubRules) {
            final List<Character> charsCopy = new ArrayList<>(messageChars);
            boolean matchesAll = true;
            for (final int curRule : subRules) {
                if (!matches(charsCopy, curRule)) {
                    matchesAll = false;
                    break;
                }
            }
            if (matchesAll) {
                while (messageChars.size() > charsCopy.size()) {
                    messageChars.removeFirst();
                }
                return true;
            }
        }
        return false;
    }

    private boolean ruleIsEndRule(final int rule, final List<Character> messageChars) {
        if (messageChars.getFirst().equals(endChars.get(rule))) {
            messageChars.removeFirst();
            return true;
        } else {
            return false;
        }
    }

    @Override
    String getMessage(final long answer) {
        return String.format("%d", answer);
    }

}
