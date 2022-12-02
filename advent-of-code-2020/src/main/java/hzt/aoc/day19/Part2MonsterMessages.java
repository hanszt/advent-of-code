package hzt.aoc.day19;

import java.util.regex.Pattern;

// Credits to Johan de Jong
public class Part2MonsterMessages extends Day19Challenge {

    public Part2MonsterMessages() {
        super("part 2",
                "How many messages completely match rule 0? (with loops)");
    }

    @Override
    protected long countMatches() {
        addRuleToRulesMaps("8: 42 | 42 8");
        addRuleToRulesMaps("11: 42 31 | 42 11 31");
        LOGGER.trace(parsedInputAsString(rulesToSubRules, messages));
        final String startRule = rulesAsStringMap.get(0);
        final String regex = ruleRegex2(startRule);
        final Pattern pattern = Pattern.compile(regex);
        return messages.stream().filter(message -> pattern.matcher(message).matches()).count();
    }

    private String ruleRegex2(final String rule) {
        return ruleRegex2(rule, 0);
    }

    private String ruleRegex2(final String rule, int depth) {
        if (depth > 200) {
            return "x";
        }

        if (rule.startsWith("\"")) {
            return rule.substring(1, 2);
        } else {
            final StringBuilder sb = new StringBuilder();
            final String[] parts = rule.split(" ");
            for (final String part : parts) {
                if ("|".equals(part)) {
                    sb.append('|');
                } else {
                    final int subRuleIndex = Integer.parseInt(part);
                    final String subRule = rulesAsStringMap.get(subRuleIndex);
                    sb.append('(').append(ruleRegex2(subRule, ++depth)).append(')');
                }
            }
            return sb.toString();
        }
    }

    @Override
    String getMessage(final long answer) {
        return String.format("%d", answer);
    }

}
