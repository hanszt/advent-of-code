package aoc.historianhysteria;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day02Test {

    private static final Day02 day02dr = new Day02(Path.of("input/day02-dr.txt"));
    private static final Day02 day02 = new Day02(Path.of("input/day02.txt"));
    private static final String TEST_INPUT = """
            7 6 4 2 1
            1 2 7 8 9
            9 7 6 2 1
            1 3 2 4 5
            8 6 4 4 1
            1 3 6 7 9
            """;

    @Test
    void testPart1Test() {
        assertEquals(2, new Day02(TEST_INPUT.lines()).part1());
    }

    @Test
    void testPart2Test() {
        assertEquals(4, new Day02(TEST_INPUT.lines()).part2());
    }

    @Test
    void testPart1Dr() {
        assertEquals(369, day02dr.part1());
    }

    @Test
    void testPart2Dr() {
        assertEquals(428, day02dr.part2());
    }

    @Test
    void testPart1() {
        assertEquals(502, day02.part1());
    }

    @Test
    void testPart2() {
        assertEquals(544, day02.part2());
    }
}
