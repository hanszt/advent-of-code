package aoc.jungle.adventures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day01CalorieCountingTest {

    private final Day01CalorieCounting day01CalorieCounting = new Day01CalorieCounting("input/day01.txt");

    @Test
    void testPart1() {
        final var maxSum = day01CalorieCounting.part1();
        assertEquals(72_511, maxSum);
    }

    @Test
    void testPart2() {
        final var topThree = day01CalorieCounting.part2();
        assertEquals(212_117, topThree);
    }

}
