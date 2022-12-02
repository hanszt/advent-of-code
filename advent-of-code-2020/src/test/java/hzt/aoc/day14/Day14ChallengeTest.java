package hzt.aoc.day14;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day14ChallengeTest {

    @Test
    void testPart1() {
        final var challenge = new Part1DockingData();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(15403588588538L, Long.parseLong(answer));
    }

    @Test
    void testPart2() {
        final var challenge = new Part2DockingData();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals( 3260587250457L, Long.parseLong(answer));
    }
}
