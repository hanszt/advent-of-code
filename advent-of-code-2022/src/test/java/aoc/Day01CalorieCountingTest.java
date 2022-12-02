package aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day01CalorieCountingTest {

    private final Day01CalorieCounting day01CalorieCounting = new Day01CalorieCounting("input/day1.txt");

    @Test
    void testPart1() {
        final var maxSum = day01CalorieCounting.part1();
        System.out.println(maxSum);
        assertEquals(72511, maxSum);
    }

    @Test
    void testPart2() {
        final var topThree = day01CalorieCounting.part2();
        System.out.println(topThree);
        assertEquals(212117, topThree);
    }

}
