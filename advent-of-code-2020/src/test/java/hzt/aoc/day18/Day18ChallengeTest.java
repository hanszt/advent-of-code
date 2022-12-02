package hzt.aoc.day18;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day18ChallengeTest {

    @Test
    void testPart1() {
        final var challenge = new Part1OperationOrder();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(4491283311856L, Long.parseLong(answer));
    }

    @Test
    void testPart2() {
        final var challenge = new Part2OperationOrder();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals( 68852578641904L, Long.parseLong(answer));
    }
}
