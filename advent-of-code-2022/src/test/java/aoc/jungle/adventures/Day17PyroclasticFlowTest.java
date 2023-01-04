package aoc.jungle.adventures;

import org.junit.jupiter.api.Test;

import static org.hzt.utils.It.println;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day17PyroclasticFlowTest {

    private final Day17PyroclasticFlow day17PyroclasticFlow = new Day17PyroclasticFlow("input/day17.txt");

    @Test
    void testPart1() {
        final var result = day17PyroclasticFlow.part1();
        println(result);
        assertEquals(3_202, result);
    }

    @Test
    void testPart2() {
        final var result = day17PyroclasticFlow.part2();
        println(result);
        assertEquals(1_591_977_077_352L, result);
    }

}
