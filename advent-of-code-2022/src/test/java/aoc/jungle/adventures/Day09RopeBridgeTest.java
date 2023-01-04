package aoc.jungle.adventures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day09RopeBridgeTest {

    private final Day09RopeBridge day09RopeBridge = new Day09RopeBridge("input/day09.txt");

    //6441 niet goed
    @Test
    void testPart1() {
        final var result = day09RopeBridge.part1();
        System.out.println("result = " + result);
        assertEquals(6522, result);
    }

    @Test
    void testPart1Test() {
        final var result = new Day09RopeBridge("input/day09test.txt").part1();
        assertEquals(13, result);
    }

    @Test
    void testPart2() {
        final var result = day09RopeBridge.part2();
        System.out.println("result = " + result);
        assertEquals(2717, result);
    }

    @Test
    void testPart2Test() {
        final var result = new Day09RopeBridge("input/day09test.txt").part2();
        assertEquals(1, result);
    }

    @Test
    void testPart2Test2() {
        final var result = new Day09RopeBridge("input/day09test2.txt").part2();
        assertEquals(36, result);
    }

}
