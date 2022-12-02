package hzt.aoc.day19;

import hzt.aoc.Challenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Day19Challenge extends Challenge {

    static final int START_RULE = 0;

    Day19Challenge(final String challengeTitle, final String description) {
        super(challengeTitle, description, "20201219-input-day19.txt");
    }

    final Map<Integer, String> rulesAsStringMap = new HashMap<>();
    final Map<Integer, List<List<Integer>>> rulesToSubRules = new HashMap<>();
    final Map<Integer, Character> endChars = new HashMap<>();
    final List<String> messages = new ArrayList<>();

    @Override
    protected String solve(final List<String> inputList) {
        parseInputList(inputList);
        return getMessage(countMatches());
    }

    private void parseInputList(final List<String> inputList) {
        for (final String line : inputList) {
            if (line.matches("[a-b]{2,}")) {
                messages.add(line);
            } else if (!line.isEmpty()) {
                addRuleToRulesMaps(line);
            }
        }
    }

    void addRuleToRulesMaps(final String line) {
        final String[] ruleNrToSubRules = line.split(":");
        final int ruleNr = Integer.parseInt(ruleNrToSubRules[0]);
        final String subRulesAsString = ruleNrToSubRules[1].strip();
        rulesAsStringMap.put(ruleNr, subRulesAsString);
        if (subRulesAsString.contains("\"")) {
            endChars.put(ruleNr, subRulesAsString.replace("\"", "").charAt(0));
        } else {
            final String[] subRulesAsArray = subRulesAsString.split("\\|");
            final List<List<Integer>> subRules = Arrays.stream(subRulesAsArray)
                    .map(this::stringToRuleList)
                    .collect(Collectors.toList());
            rulesToSubRules.put(ruleNr, subRules);
        }
    }

    String parsedInputAsString(final Map<Integer, List<List<Integer>>> rulesToSubRules, final List<String> messages) {
        final StringBuilder sb = new StringBuilder();
        sb.append(String.format("%nTargetRules:%n"));
        endChars.forEach((k, v) -> sb.append(k).append("->").append(v).append(String.format("%n")));
        sb.append(String.format("%nRules:%n"));
        rulesToSubRules.forEach((k, v) -> sb.append(k).append("->").append(v).append(String.format("%n")));
        sb.append(String.format("%nMessages:%n"));
        messages.forEach(str -> sb.append(str).append(String.format("%n")));
        return sb.toString();
    }


    private List<Integer> stringToRuleList(final String s) {
        return Arrays.stream(s.strip().split("\\s")).map(Integer::parseInt).collect(Collectors.toList());
    }

    protected abstract long countMatches();


    abstract String getMessage(long value);
}
