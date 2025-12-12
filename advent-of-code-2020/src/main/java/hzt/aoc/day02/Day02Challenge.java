package hzt.aoc.day02;


import hzt.aoc.Challenge;
import hzt.aoc.day02.model.Policy;

import java.util.List;

public abstract class Day02Challenge extends Challenge {

    private long inputListSize;

    protected Day02Challenge(final String part, final String description) {
        super(part, description, "20201202-input-day2.txt");
    }

    @Override
    protected String solve(final List<String> inputList) {
        inputListSize = inputList.size();
        final var validPasswords = inputList.stream().filter(this::passwordIsValid).count();
        return String.valueOf(validPasswords);
    }

    boolean passwordIsValid(final String line) {
        final var array = line.split(": ");
        final var string = array[0];
        final var password = array[1];
        final var policy = getPolicyFromString(string);
        return isValid(password, policy);
    }

    abstract boolean isValid(String password, Policy policy);

    private static Policy getPolicyFromString(final String string) {
        final var character = string.charAt(string.length() - 1);
        final var range = string.substring(0, string.length() - 2);
        final var lowerAndUpper = range.split("-");
        return new Policy(Integer.parseInt(lowerAndUpper[0]), Integer.parseInt(lowerAndUpper[1]), character);
    }

    @Override
   protected String getMessage(final String validPasswords) {
        return String.format("%s of the %d passwords are valid%n", validPasswords, inputListSize);

    }
}
