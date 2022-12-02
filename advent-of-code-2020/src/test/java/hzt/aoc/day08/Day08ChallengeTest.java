package hzt.aoc.day08;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day08ChallengeTest {

    @Test
    void testPart1() {
        final var challenge = new Part1HandheldHalting();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(1548, Integer.parseInt(answer));
    }

    @Test
    void testPart2() {
        final var challenge = new Part2HandheldHalting();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(1375, Long.parseLong(answer));
    }

}
