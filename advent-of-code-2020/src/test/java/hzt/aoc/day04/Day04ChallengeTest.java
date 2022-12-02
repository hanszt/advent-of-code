package hzt.aoc.day04;

import hzt.aoc.Challenge;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day04ChallengeTest {

    @Test
    void testPart1() {
        final Challenge challenge = new PassportProcessingPart1();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(245, Integer.parseInt(answer));
    }

    @Test
    void testPart2() {
        final Challenge challenge = new PassportProcessingPart2();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(133, Integer.parseInt(answer));
    }
}
