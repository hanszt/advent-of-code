package hzt.aoc.day13;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day13ChallengeTest {

    @Test
    void testPart1() {
        final var challenge = new Part1ShuttleSearch();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(2947, Integer.parseInt(answer));
    }

    @Test
    void testPart2() {
        final var challenge = new Part2ShuttleSearch();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(526090562196173L, Long.parseLong(answer));
    }
}
