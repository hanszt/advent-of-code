package hzt.aoc.day06.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Group {

    private final List<Character> answers;
    private final List<List<Character>> personsInGroupAnswers;

    public Group(final List<Character> answers, final List<List<Character>> personsInGroupAnswers) {
        this.answers = answers;
        this.personsInGroupAnswers = personsInGroupAnswers;
    }

    public int amountAnyoneAnsweredYes() {
        final Set<Character> answeredYes = new HashSet<>(answers);
        return answeredYes.size();
    }

    public Set<Character> getGivenAnswerSet() {
        return new HashSet<>(answers);
    }

    public int amountEveryoneAnsweredYes() {
        int amountAnswersEveryoneAnsweredYes = 0;
        for (final Character answer : getGivenAnswerSet()) {
            boolean everyOneAnsweredYes = true;
            for (final List<Character> personAnswers : personsInGroupAnswers) {
                if (!personAnswers.contains(answer)) {
                    everyOneAnsweredYes = false;
                    break;
                }
            }
            if (everyOneAnsweredYes) {
                amountAnswersEveryoneAnsweredYes++;
            }
        }
        return amountAnswersEveryoneAnsweredYes;
    }

    @Override
    public String toString() {
        return "Group{" +
                "answers=" + answers +
                ", personsInGroupAnswers=" + personsInGroupAnswers +
                '}';
    }
}
