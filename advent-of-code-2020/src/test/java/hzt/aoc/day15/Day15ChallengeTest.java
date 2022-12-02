package hzt.aoc.day15;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day15ChallengeTest {

    @Test
    void testPart1() {
        final var challenge = new Part1RambunctiousRecitation();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(1373, Integer.parseInt(answer));
    }

    @Test
    void testPart2() {
        final var challenge = new Part2RambunctiousRecitation();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals( 112458, Long.parseLong(answer));
    }

}
