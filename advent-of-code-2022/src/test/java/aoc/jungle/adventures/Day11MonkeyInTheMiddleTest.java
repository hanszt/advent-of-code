package aoc.jungle.adventures;

import org.junit.jupiter.api.Test;

import static org.hzt.utils.It.println;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day11MonkeyInTheMiddleTest {

    @Test
    void testPart1() {
        final var result = new Day11MonkeyInTheMiddle("input/day11.txt").part1();
        println(result);
        assertEquals(54_752, result);
    }

    @Test
    void testPart1test() {
        final var result = new Day11MonkeyInTheMiddle("input/day11test.txt").part1();
        println(result);
        assertEquals(10_605, result);
    }

    @Test
    void testPart2() {
        final var result = new Day11MonkeyInTheMiddle("input/day11.txt").part2();
        println(result);
        assertEquals(13_606_755_504L, result);
    }

    @Test
    void testPart2test() {
        final var result = new Day11MonkeyInTheMiddle("input/day11test.txt").part2();
        println(result);
        assertEquals(2_713_310_158L, result);
    }

}
