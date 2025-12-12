package hzt.aoc.day14;

import hzt.aoc.Challenge;

import java.util.ArrayList;
import java.util.List;

public abstract class Day14Challenge extends Challenge {

    Day14Challenge(final String challengeTitle, final String description) {
        super(challengeTitle, description, "20201214-input-day14.txt");
    }

    @Override
    protected String solve(final List<String> inputList) {
        final List<Program> programs = new ArrayList<>();
        Program program = null;
        for (final var line : inputList) {
            final var array = line.split(" = ");
            if (array[0].matches("mask")) {
                program = new Program(array[1]);
                programs.add(program);
            } else if (program != null) {
                final var value = Integer.parseInt(array[1]);
                final var memoryLocation = Integer.parseInt(array[0].substring(4, array[0].length() - 1));
                program.put(value, memoryLocation);
            }
        }
        return getMessage(count(programs));
    }

    abstract long count(List<Program> programs);


    abstract String getMessage(long value);

}
