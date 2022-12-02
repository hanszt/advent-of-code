package hzt.aoc.day25;

import hzt.aoc.Challenge;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day25ChallengeTest {

    @Test
    void testPart1() {
        final Challenge challenge = new Part1ComboBreaker();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(4968512, Long.valueOf(answer));
    }
}
