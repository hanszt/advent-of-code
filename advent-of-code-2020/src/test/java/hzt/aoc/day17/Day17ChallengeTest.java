package hzt.aoc.day17;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day17ChallengeTest {

    @Test
    void testPart1() {
        final var challenge = new Part1ConwayCubes();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(252, Integer.parseInt(answer));
    }

    @Test
    void testPart2() {
        final var challenge = new Part2ConwayCubes();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(2160, Integer.parseInt(answer));
    }
}
