package hzt.aoc.day05;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day05ChallengeTest {

    @Test
    void testPart1() {
        final var challenge = new Part1BinaryBoarding();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(911, Integer.parseInt(answer));
    }

    @Test
    void testPart2() {
        final var challenge = new Part2BinaryBoarding();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(629, Integer.parseInt(answer));
    }
}
