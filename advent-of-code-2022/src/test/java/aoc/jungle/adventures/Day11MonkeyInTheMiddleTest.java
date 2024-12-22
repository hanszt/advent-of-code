package aoc.jungle.adventures;

import org.hzt.utils.io.FileX;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hzt.utils.It.println;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day11MonkeyInTheMiddleTest {

    private static final Day11MonkeyInTheMiddle DAY_11_MONKEY_IN_THE_MIDDLE_TEST = new Day11MonkeyInTheMiddle("input/day11test.txt");
    public static final Day11MonkeyInTheMiddle DAY_11_MONKEY_IN_THE_MIDDLE = new Day11MonkeyInTheMiddle("input/day11.txt");

    @Test
    void testPart1() {
        final var result = DAY_11_MONKEY_IN_THE_MIDDLE.part1();
        println(result);
        assertEquals(54_752, result);
    }

    @Test
    void testPart1test() {
        final var result = DAY_11_MONKEY_IN_THE_MIDDLE_TEST.part1();
        println(result);
        assertEquals(10_605, result);
    }

    @Test
    void testPart2() {
        final var result = DAY_11_MONKEY_IN_THE_MIDDLE.part2();
        println(result);
        assertEquals(13_606_755_504L, result);
    }

    @Test
    void testPart2test() {
        final var result = DAY_11_MONKEY_IN_THE_MIDDLE_TEST.part2();
        println(result);
        assertEquals(2_713_310_158L, result);
    }

    @Nested
    class ElizarovTests {

        @Test
        void testElizarov() {
            final var result = Day11ElizarovKt.day11Part2(FileX.of("input/day11.txt").useLines(s -> s.toList()));
            assertEquals(13_606_755_504L, result);
        }
    }

}
