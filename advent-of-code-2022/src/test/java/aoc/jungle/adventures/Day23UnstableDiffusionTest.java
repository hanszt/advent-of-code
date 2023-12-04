package aoc.jungle.adventures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day23UnstableDiffusionTest {

    @Test
    void testPart1() {
        final var result = new Day23UnstableDiffusion("input/day23.txt").part1();
        assertEquals(4_052, result);
    }

    @Test
    void testPart2() {
        final var result = new Day23UnstableDiffusion("input/day23.txt").part2();
        assertEquals(978, result);
    }

    @Test
    void testPart1test() {
        final var result = new Day23UnstableDiffusion("input/day23test.txt").part1();
        assertEquals(110, result);
    }

    @Test
    void testPart2test() {
        final var result = new Day23UnstableDiffusion("input/day23test.txt").part2();
        assertEquals(20, result);
    }

}
