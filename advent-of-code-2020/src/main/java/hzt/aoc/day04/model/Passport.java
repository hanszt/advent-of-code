package hzt.aoc.day04.model;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Set;
import java.util.regex.Pattern;

public class Passport {

    private static final Logger LOGGER = LogManager.getLogger(Passport.class);

    private static final Pattern HAIR_COLOR_PATTERN = Pattern.compile("(#)([\\da-fA-F]{6})");
    private static final Pattern PASSWORD_ID_PATTERN = Pattern.compile("(\\d{9})");
    private static final Pattern NR_PATTERN = Pattern.compile("\\d+");
    private static final Pattern FOUR_DIGIT_NR_PATTERN = Pattern.compile("\\d{4}");

    private static final int LOWEST_BIRTH_YEAR = 1920;
    private static final int HIGHEST_BIRTH_YEAR = 2002;
    private static final int LOWEST_ISSUE_YEAR = 2010;
    private static final int HIGHEST_ISSUE_YEAR = 2020;
    private static final int LOWEST_EXPIRATION_YEAR = 2020;
    private static final int HIGHEST_EXPIRATION_YEAR = 2030;
    private static final Set<String> VALID_EYE_COLORS = Set.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
    private static final int UNIT_LENGTH = 2;
    private static final int MINIMUM_HEIGHT_STRING_LENGTH = 3;

    private String passwordID;
    private String expirationYear;
    private String issueYear;
    private String countryId;
    private String birthYear;
    private String height;
    private String eyeColor;
    private String hairColor;

    public Passport() {
        super();
    }

    public Passport(final String passwordID,
                    final String expirationYear,
                    final String issueYear,
                    final String birthYear,
                    final String height,
                    final String eyeColor,
                    final String hairColor) {
        this.passwordID = passwordID;
        this.expirationYear = expirationYear;
        this.issueYear = issueYear;
        this.birthYear = birthYear;
        this.height = height;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
    }

    // in part 1, a password is valid when all fields have a value. Only country ID is optional
    public boolean requiredFieldsPresent() {
        final boolean mandatoryPassportFieldsPresent = passwordID != null && expirationYear != null && issueYear != null;
        final boolean userFieldsPresent = birthYear != null && height != null && eyeColor != null && hairColor != null;
        return mandatoryPassportFieldsPresent && userFieldsPresent;
    }

    public boolean fieldsMeetCriteria() {
        if (requiredFieldsPresent()) {
            final boolean birthYearValid = checkYear(birthYear, LOWEST_BIRTH_YEAR, HIGHEST_BIRTH_YEAR);
            final boolean issueYearValid = checkYear(issueYear, LOWEST_ISSUE_YEAR, HIGHEST_ISSUE_YEAR);
            final boolean expirationYearValid = checkYear(expirationYear, LOWEST_EXPIRATION_YEAR, HIGHEST_EXPIRATION_YEAR);
            final boolean heightValid = checkHeight(height);
            // a # followed by exactly six characters 0-9 or a-f.
            final boolean hairColorValid = hairColor != null && HAIR_COLOR_PATTERN.matcher(hairColor).matches();
            final boolean eyeColorValid = eyeColor != null && VALID_EYE_COLORS.contains(eyeColor);
            // a nine-digit number, including leading zeroes.
            final boolean passportIdValid = PASSWORD_ID_PATTERN.matcher(passwordID).matches();
            LOGGER.trace(isValidAsString(birthYearValid, issueYearValid, expirationYearValid, heightValid,
                    hairColorValid, eyeColorValid, passportIdValid));
            final var personCharacteristicsValid = eyeColorValid && hairColorValid && heightValid;
            final var passWordValid = passportIdValid && expirationYearValid && issueYearValid && birthYearValid;
            return personCharacteristicsValid && passWordValid;
        } else {
            return false;
        }
    }

    private static String isValidAsString(final boolean birthYearValid,
                                   final boolean issueYearValid,
                                   final boolean expirationYearValid,
                                   final boolean heightValid,
                                   final boolean hairColorValid,
                                   final boolean eyeColorValid,
                                   final boolean passportIdValid) {
        return "birthYearValid=" + birthYearValid +
                "\nissueYearValid=" + issueYearValid +
                "\nexpirationYearValid=" + expirationYearValid +
                "\nheightValid=" + heightValid +
                "\nhairColorValid=" + hairColorValid +
                "\neyeColorValid=" + eyeColorValid +
                "\npassportIdValid=" + passportIdValid + "\n";
    }

    private static boolean checkHeight(final String height) {
        if (height.length() >= MINIMUM_HEIGHT_STRING_LENGTH) {
            final String value = height.substring(0, height.length() - UNIT_LENGTH);
            final String unit = height.substring(height.length() - UNIT_LENGTH);
            final boolean valueIsNumber = NR_PATTERN.matcher(value).matches();
            LOGGER.trace("value is number=" + valueIsNumber + " Value=" + value);
            if (valueIsNumber) {
                return checkHeightUnit(value, unit);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private static boolean checkHeightUnit(String value, String unit) {
        final int heightValue = Integer.parseInt(value);
        if ("cm".equals(unit)) {
            return heightValue >= 150 && heightValue <= 193;
        } else if ("in".equals(unit)) {
            return heightValue >= 59 && heightValue <= 76;
        } else {
            return false;
        }
    }

    private static boolean checkYear(final String input, final int lower, final int upper) {
        final boolean matchesFourDigits = input.length() == 4 && FOUR_DIGIT_NR_PATTERN.matcher(input).matches();
        if (matchesFourDigits) {
            final int year = Integer.parseInt(input);
            return year >= lower && year <= upper;
        } else {
            return false;
        }
    }

    public void setPasswordID(final String passwordID) {
        this.passwordID = passwordID;
    }

    public void setExpirationYear(final String expirationYear) {
        this.expirationYear = expirationYear;
    }

    public void setIssueYear(final String issueYear) {
        this.issueYear = issueYear;
    }

    public void setCountryId(final String countryId) {
        this.countryId = countryId;
    }

    public void setBirthYear(final String birthYear) {
        this.birthYear = birthYear;
    }

    public void setHeight(final String height) {
        this.height = height;
    }

    public void setEyeColor(final String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public void setHairColor(final String hairColor) {
        this.hairColor = hairColor;
    }

    @Override
    public String toString() {
        return "Passport{" +
                "passwordID=" + passwordID +
                ", expirationYear=" + expirationYear +
                ", issueYear=" + issueYear +
                ", countryId=" + countryId +
                ", birthYear=" + birthYear +
                ", height=" + height +
                ", eyeColor='" + eyeColor + '\'' +
                ", hairColor='" + hairColor + '\'' +
                '}';
    }
}
