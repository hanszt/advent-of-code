package hzt.aoc.day12;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day12ChallengeTest {

    @Test
    void testPart1() {
        final var challenge = new Part1RainRisk();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(998, Integer.parseInt(answer));
    }

    @Test
    void testPart2() {
        final var challenge = new Part2RainRisk();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals( 71586, Integer.parseInt(answer));
    }
}
