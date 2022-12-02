package hzt.aoc.day16;

import hzt.aoc.Challenge;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day16ChallengeTest {

    @Test
    void testPart1() {
        final Challenge challenge = new Part1TicketTranslation();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(32835, Integer.parseInt(answer));
    }

    @Test
    void testPart2() {
        final Challenge challenge = new Part2TicketTranslation();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(514662805187L, Long.parseLong(answer));
    }
}
