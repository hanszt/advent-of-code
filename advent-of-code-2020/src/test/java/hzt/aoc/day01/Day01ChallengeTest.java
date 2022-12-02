package hzt.aoc.day01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day01ChallengeTest {

    @Test
    void testPart1() {
        final var challenge = new Part1ReportRepair();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(252724, Integer.parseInt(answer));
    }

    @Test
    void testPart2() {
        final var challenge = new Part2ReportRepair();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(276912720, Integer.parseInt(answer));
    }
}
