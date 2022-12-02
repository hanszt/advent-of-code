package hzt.aoc.day04;

import hzt.aoc.day04.model.Passport;

import java.util.List;

public class PassportProcessingPart1 extends Day04Challenge {

    public PassportProcessingPart1() {
        super("part 1", "Find the number of valid passports. ");
    }

    @Override
    protected long calculateResult(final List<Passport> passports) {
        return passports.stream()
                .filter(Passport::requiredFieldsPresent)
                .count();
    }

}
