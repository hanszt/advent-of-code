package aoc.jungle.adventures;

import org.junit.jupiter.api.Test;

import static org.hzt.utils.It.println;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day07NoSpaceLeftOnDeviceTest {

    @Test
    void testPart1() {
        final var day07NoSpaceLeftOnDevice = new Day07NoSpaceLeftOnDevice("input/day07.txt");
        final var result = day07NoSpaceLeftOnDevice.part1();
        println(day07NoSpaceLeftOnDevice.root.toBFSTreeString(2));
        assertEquals(1_350_966, result);
    }

    @Test
    void testPart1Test() {
        final var result = new Day07NoSpaceLeftOnDevice("input/day07test.txt").part1();
        assertEquals(95_437, result);
    }

    @Test
    void testPart2() {
        final var result = new Day07NoSpaceLeftOnDevice("input/day07.txt").part2();
        assertEquals(6_296_435, result);
    }

    @Test
    void testPart2Test() {
        final var result = new Day07NoSpaceLeftOnDevice("input/day07test.txt").part2();
        assertEquals(24_933_642, result);
    }

}
