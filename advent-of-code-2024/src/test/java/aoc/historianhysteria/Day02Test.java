package aoc.historianhysteria;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day02Test {

    private static final Day02 day02 = new Day02("input/day02.txt");
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
    void testPart1() {
        assertEquals(369, day02.part1());
    }

    @Test
    void testPart2() {
        assertEquals(428, day02.part2());
    }
}
