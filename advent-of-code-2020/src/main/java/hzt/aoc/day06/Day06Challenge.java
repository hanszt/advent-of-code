package hzt.aoc.day06;

import hzt.aoc.Challenge;
import hzt.aoc.day06.model.Group;

import java.util.ArrayList;
import java.util.List;

public abstract class Day06Challenge extends Challenge {

    protected Day06Challenge(final String challengeTitle, final String description) {
        super(challengeTitle, description, "20201206-input-day6.txt");
    }

    @Override
    protected String solve(final List<String> inputByLineList) {
        final List<Group> groups = getGroups(inputByLineList);
        final int result = calculateResult(groups);
        return String.valueOf(result);
    }

    protected List<Group> getGroups(final List<String> inputByLineList) {
        final List<Group> groups = new ArrayList<>();
        final List<Character> groupAnswers = new ArrayList<>();
        final List<List<Character>> personsInGroupAnswers = new ArrayList<>();
        for (final String string : inputByLineList) {
            final List<Character> personAnswers = new ArrayList<>();
            for (int i = 0; i < string.length(); i++) {
                groupAnswers.add(string.charAt(i));
                personAnswers.add(string.charAt(i));
            }
            personsInGroupAnswers.add(personAnswers);
            if (string.matches("\\s*")) {
                personsInGroupAnswers.remove(personAnswers);
                groups.add(new Group(new ArrayList<>(groupAnswers), new ArrayList<>(personsInGroupAnswers)));
                groupAnswers.clear();
                personsInGroupAnswers.clear();
            }
        }
        return groups;
    }

    protected abstract int calculateResult(List<Group> groups);

}
