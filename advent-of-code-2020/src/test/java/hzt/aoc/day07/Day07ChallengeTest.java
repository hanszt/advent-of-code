package hzt.aoc.day07;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day07ChallengeTest {

    @Test
    void testPart1() {
        final var challenge = new Part1HandyHaversacks();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(378, Integer.parseInt(answer));
    }

    @Test
    void testPart2() {
        final var challenge = new Part2HandyHaversacks();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(27526, Integer.parseInt(answer));
    }
}
