package hzt.aoc.day02;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day02ChallengeTest {

    @Test
    void testPart1() {
        final var challenge = new Part1PasswordPhilosophy();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(467, Integer.parseInt(answer));
    }

    @Test
    void testPart2() {
        final var challenge = new Part2PasswordPhilosophy();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(441, Integer.parseInt(answer));
    }
}
