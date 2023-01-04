package aoc.jungle.adventures;

import org.junit.jupiter.api.Test;

import static org.hzt.utils.It.println;
import static org.junit.jupiter.api.Assertions.*;

class Day05SupplyStacksTest {

        private final Day05SupplyStacks day05SupplyStacks = new Day05SupplyStacks("input/day05.txt");
    @Test
    void testPart1() {
        final var part1 = day05SupplyStacks.part1();
        println("part1 = " + part1);
        assertEquals("TDCHVHJTG", part1);
    }

    @Test
    void testPart1TestInput() {
        final var part1 = new Day05SupplyStacks("input/day05test.txt").part1();
        println("part1 = " + part1);
        assertEquals("CMZ", part1);
    }

    @Test
    void testPart2() {
        final var part2 = day05SupplyStacks.part2();
        println("part2 = " + part2);
        assertEquals("NGCMPJLHV", part2);
    }

    @Test
    void testPart2TestInput() {
        final var part1 = new Day05SupplyStacks("input/day05test.txt").part2();
        println("part2 = " + part1);
        assertEquals("MCD", part1);
    }

}
