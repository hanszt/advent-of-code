package hzt.aoc.day04;

import hzt.aoc.Challenge;
import hzt.aoc.day04.model.Passport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Day04Challenge extends Challenge {

    private static final String BIRTH_YEAR = "byr";
    private static final String ISSUE_YEAR = "iyr";
    private static final String EXPIRATION_YEAR = "eyr";
    private static final String HEIGHT = "hgt";
    private static final String HAIR_COLOR = "hcl";
    private static final String EYE_COLOR = "ecl";
    private static final String PASSPORT_ID = "pid";
    private static final String COUNTRY_ID = "cid";
    private long passportListSize;

    protected Day04Challenge(final String challengeTitle, final String description) {
        super(challengeTitle, description, "20201204-input-day4.txt");
    }

    @Override
    protected String solve(final List<String> inputList) {
        final var passports = getPasswords(inputList);
        passportListSize = passports.size();
        final var validPasswords = calculateResult(passports);
        return String.valueOf(validPasswords);
    }

    private List<Passport> getPasswords(final List<String> lines) {
        final List<Passport> passportList = new ArrayList<>();
        final List<String> passportValues = new ArrayList<>();
        for (final var string : lines) {
            final var strings = string.split("\\s");
            passportValues.addAll(Arrays.asList(strings));
            if (string.matches("\\s*")) {
                passportValues.remove(string);
                passportList.add(createPassportFromValues(passportValues));
                passportValues.clear();
            }
        }
        return passportList;
    }

    private static Passport createPassportFromValues(final List<String> passwordEntries) {
        final var passport = new Passport();
        final var sb = new StringBuilder();
        for (final var passwordEntry : passwordEntries) {
            final var keyValue = passwordEntry.split(":");
            final var key = keyValue[0];
            final var value = keyValue[1];
            sb.append(String.format("Entry{key='%s', value='%s'}", key, value));
            switch (key) {
                case PASSPORT_ID -> passport.setPasswordID(value);
                case EXPIRATION_YEAR -> passport.setExpirationYear(value);
                case ISSUE_YEAR -> passport.setIssueYear(value);
                case COUNTRY_ID -> passport.setCountryId(value);
                case BIRTH_YEAR -> passport.setBirthYear(value);
                case HEIGHT -> passport.setHeight(value);
                case EYE_COLOR -> passport.setEyeColor(value);
                case HAIR_COLOR -> passport.setHairColor(value);
                default -> LOGGER.trace(() -> "No match");
            }
            sb.append(String.format("%n"));
        }
        LOGGER.trace(sb::toString);
        return passport;
    }

    protected abstract long calculateResult(List<Passport> passports);

    @Override
    public String getMessage(final String validPassports) {
        return String.format("The number of valid passports is: %s of %d%n", validPassports, passportListSize);
    }

}
