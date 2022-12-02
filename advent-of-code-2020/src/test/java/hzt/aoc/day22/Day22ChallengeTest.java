package hzt.aoc.day22;

import hzt.aoc.Challenge;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day22ChallengeTest {

    @Test
    void testPart1() {
        final Challenge challenge = new Part1CrabCombat();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(33772, Integer.parseInt(answer));
    }

    @Test
    void testPart2() {
        final Challenge challenge = new Part2CrabCombat();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(35070, Integer.parseInt(answer));
    }
}
