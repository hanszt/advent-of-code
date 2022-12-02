package hzt.aoc.day06;

import hzt.aoc.day06.model.Group;

import java.util.List;

public class Part1CustomCustoms extends Day06Challenge {

    public Part1CustomCustoms() {
        super("part 1", "For each group, count the number of questions to which anyone answered 'yes'. " +
                "What is the sum of those counts?. ");
    }

    @Override
    protected int calculateResult(final List<Group> groups) {
        return groups.stream().map(Group::amountAnyoneAnsweredYes).reduce(0, (acc, cur) -> acc += cur);
    }

    @Override
    protected String getMessage(final String result) {
        return String.format("The sum of the counts in each group to which anyone answered 'yes' is: %s%n", result);
    }
}
