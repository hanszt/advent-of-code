package hzt.aoc.day19;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day19ChallengeTest {

    @Test
    void testPart1() {
        final var challenge = new Part1MonsterMessages();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(192, Integer.parseInt(answer));
    }

    @Test
    void testPart2() {
        final var challenge = new Part2MonsterMessages();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals( 296, Integer.parseInt(answer));
    }
}
