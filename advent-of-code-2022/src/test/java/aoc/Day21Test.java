package aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day21Test {

    private final Day21 day13 = new Day21("input/day21.txt");

    @Test
    void testPart1() {
        final var result = day13.part1();
        assertEquals(157714751182692L, result);
    }

    @Test
    void testPart1Test() {
        final var result = new Day21("input/day21test.txt").part1();
        assertEquals(152, result);
    }

    @Test
    void testPart2() {
        final var result = day13.part2();
        assertEquals(0, result);
    }

}
