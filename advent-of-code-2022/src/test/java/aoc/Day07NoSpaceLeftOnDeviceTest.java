package aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day07NoSpaceLeftOnDeviceTest {

    final Day07NoSpaceLeftOnDevice day07NoSpaceLeftOnDevice = new Day07NoSpaceLeftOnDevice("input/day07.txt");

    @Test
    void testPart1() {
        final var result = day07NoSpaceLeftOnDevice.part1();
        assertEquals(1_350_966, result);
    }

    @Test
    void testPart1Test() {
        final var spaceLeftOnDevice = new Day07NoSpaceLeftOnDevice("input/day07test.txt");
        final var result = spaceLeftOnDevice.part1();
        assertEquals(95_437, result);
    }

    @Test
    void testPart2() {
        final var result = day07NoSpaceLeftOnDevice.part2();
        assertEquals(6_296_435, result);
    }

    @Test
    void testPart2Test() {
        final var result = new Day07NoSpaceLeftOnDevice("input/day07test.txt").part2();
        assertEquals(24_933_642, result);
    }

}
