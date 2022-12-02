package hzt.aoc.day06;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day06ChallengeTest {

    @Test
    void testPart1() {
        final var challenge = new Part1CustomCustoms();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(6416, Integer.parseInt(answer));
    }

    @Test
    void testPart2() {
        final var challenge = new Part2CustomCustoms();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(3050, Integer.parseInt(answer));
    }

}
