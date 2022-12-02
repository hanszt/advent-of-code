package hzt.aoc.day20;

import hzt.aoc.Challenge;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day20ChallengeTest {

    @Test
    void testPart1() {
        final Challenge challenge = new Part1JurassicJigsaw();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(83_775_126_454_273L, Long.parseLong(answer));
    }

    @Test
    void testPart2() {
        final Challenge challenge = new Part2JurassicJigsaw();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(1993, Integer.parseInt(answer));
    }
}
