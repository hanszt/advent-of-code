package hzt.aoc.day24;

import hzt.aoc.Challenge;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day24ChallengeTest {

    @Test
    void testPart1() {
        final Challenge challenge = new Part1LobbyLayout();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(500, Long.valueOf(answer));
    }

    @Test
    void testPart2() {
        final Challenge challenge = new Part2LobbyLayout();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(4280, Long.valueOf(answer));
    }

}
