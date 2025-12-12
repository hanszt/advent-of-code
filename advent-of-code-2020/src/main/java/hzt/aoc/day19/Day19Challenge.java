package hzt.aoc.day19;

import hzt.aoc.Challenge;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Day19Challenge extends Challenge {

    static final int START_RULE = 0;

    Day19Challenge(final String challengeTitle, final String description) {
        super(challengeTitle, description, "20201219-input-day19.txt");
    }

    final Map<Integer, String> rulesAsStringMap = new HashMap<>();
    final Map<Integer, int[][]> rulesToSubRules = new HashMap<>();
    final Map<Integer, Character> endChars = new HashMap<>();
    final List<String> messages = new ArrayList<>();

    @Override
    protected String solve(final List<String> inputList) {
        parseInputList(inputList);
        return getMessage(countMatches());
    }

    private void parseInputList(final List<String> inputList) {
        for (final var line : inputList) {
            if (line.matches("[a-b]{2,}")) {
                messages.add(line);
            } else if (!line.isEmpty()) {
                addRuleToRulesMaps(line);
            }
        }
    }

    void addRuleToRulesMaps(final String line) {
        final var ruleNrToSubRules = line.split(":");
        final var ruleNr = Integer.parseInt(ruleNrToSubRules[0]);
        final var subRulesAsString = ruleNrToSubRules[1].strip();
        rulesAsStringMap.put(ruleNr, subRulesAsString);
        if (subRulesAsString.contains("\"")) {
            endChars.put(ruleNr, subRulesAsString.replace("\"", "").charAt(0));
        } else {
            final var subRulesAsArray = subRulesAsString.split("\\|");
            final var subRules = Arrays.stream(subRulesAsArray)
                    .map(this::stringToRuleList)
                    .toArray(int[][]::new);
            rulesToSubRules.put(ruleNr, subRules);
        }
    }

    String parsedInputAsString(final Map<Integer, int[][]> rulesToSubRules, final List<String> messages) {
        final var sb = new StringBuilder();
        sb.append(String.format("%nTargetRules:%n"));
        endChars.forEach((k, v) -> sb.append(k).append("->").append(v).append(String.format("%n")));
        sb.append(String.format("%nRules:%n"));
        rulesToSubRules.forEach((k, v) -> sb.append(k).append("->").append(gridToString(v)).append(String.format("%n")));
        sb.append(String.format("%nMessages:%n"));
        messages.forEach(str -> sb.append(str).append(String.format("%n")));
        return sb.toString();
    }

    private static String gridToString(final int[][] grid) {
        return Arrays.stream(grid)
                .map(Arrays::toString)
                .collect(Collectors.joining());
    }


    private int[] stringToRuleList(final String s) {
        return Arrays.stream(s.strip().split("\\s")).mapToInt(Integer::parseInt).toArray();
    }

    protected abstract long countMatches();


    abstract String getMessage(long value);
}
