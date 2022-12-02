package hzt.aoc.day02;

import hzt.aoc.day02.model.Policy;

public class Part1PasswordPhilosophy extends Day02Challenge {

    public Part1PasswordPhilosophy() {
        super("part 1",
                "Count the passwords that are valid, see part one of ChallengeDay2.md for the validity rules");
    }

    boolean isValid(final String password, final Policy policy) {
        int matchesWithPolicyChar = 0;
        for (int i = 0; i < password.length(); i++) {
            final char character = password.charAt(i);
            if (character == policy.getCharacter()) {
                matchesWithPolicyChar++;
            }
        }
        return matchesWithPolicyChar >= policy.getLowerBound() && matchesWithPolicyChar <= policy.getUpperBound();
    }

}
