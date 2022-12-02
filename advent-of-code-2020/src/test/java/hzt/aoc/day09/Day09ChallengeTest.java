package hzt.aoc.day09;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day09ChallengeTest {

    @Test
    void testPart1() {
        final var challenge = new Part1EncodingError();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(1639024365, Integer.parseInt(answer));
    }

    @Test
    void testPart2() {
        final var challenge = new Part2EncodingError();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals( 219202240, Long.parseLong(answer));
    }

}
