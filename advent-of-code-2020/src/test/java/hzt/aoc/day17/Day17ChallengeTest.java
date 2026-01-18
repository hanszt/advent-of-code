package hzt.aoc.day17;

import org.hzt.utils.io.FileX;
import org.hzt.utils.iterables.Collectable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day17ChallengeTest {

    @Test
    void testPart1() {
        final var challenge = new Part1ConwayCubes();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(252, Integer.parseInt(answer));
    }

    @Test
    void testPart1Set() {
        final var lines = FileX.fromResource("/input/20201217-input-day17.txt").useLines(Collectable::toList);
        final var challenge = new ConwayCubes(lines);
        assertEquals(252, challenge.part1());
    }

    @Test
    void testPart2() {
        final var challenge = new Part2ConwayCubes();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(2160, Integer.parseInt(answer));
    }

    @Test
    void testPart2Set() {
        final var lines = FileX.fromResource("/input/20201217-input-day17.txt").useLines(Collectable::toList);
        final var challenge = new ConwayCubes(lines);
        assertEquals(2160, challenge.part2());
    }
}
