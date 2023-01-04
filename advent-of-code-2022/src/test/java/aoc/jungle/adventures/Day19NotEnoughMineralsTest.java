package aoc.jungle.adventures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day19NotEnoughMineralsTest {

    @Test
    void testPart1() {
        final var result = new Day19NotEnoughMinerals("input/day19.txt").part1();
        assertEquals(1_616, result);
    }

    @Test
    void testPart1test() {
        final var result = new Day19NotEnoughMinerals("input/day19test.txt").part1();
        assertEquals(33, result);
    }

    @Test
    void testPart2() {
        final var result = new Day19NotEnoughMinerals("input/day19.txt").part2();
        assertEquals(8_990, result);
    }

    @Test
    void testPart2test() {
        final var result = new Day19NotEnoughMinerals("input/day19test.txt").part2();
        assertEquals(3_472, result);
    }

}
