package hzt.aoc.day03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day03ChallengeTest {

    @Test
    void testPart1() {
        final var challenge = new TreesEncounteredPart1();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(259, Integer.parseInt(answer));
    }

    @Test
    void testPart2() {
        final var challenge = new TreesEncounteredPart2();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(2224913600L, Long.parseLong(answer));
    }
}
