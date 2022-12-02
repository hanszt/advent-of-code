package hzt.aoc.day02;

import hzt.aoc.day02.model.Policy;

public class Part2PasswordPhilosophy extends Day02Challenge {

    public Part2PasswordPhilosophy() {
        super("part 2",
                "Count the passwords that are valid, see part two of ChallengeDay2.md for the validity rules");
    }

    @Override
    boolean isValid(final String password, final Policy policy) {
        int matchesWithPolicyChar = 0;
        if (password.charAt(policy.getLowerBound() - 1) == policy.getCharacter()) {
            matchesWithPolicyChar++;
        }
        if (password.charAt(policy.getUpperBound() - 1) == policy.getCharacter()) {
            matchesWithPolicyChar++;
        }
        return matchesWithPolicyChar == 1;
    }

}
