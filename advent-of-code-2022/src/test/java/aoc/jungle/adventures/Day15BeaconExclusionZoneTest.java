package aoc.jungle.adventures;

import org.junit.jupiter.api.Test;

import static org.hzt.utils.It.println;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day15BeaconExclusionZoneTest {

    private static final Day15BeaconExclusionZone day15BeaconExclusionZone = new Day15BeaconExclusionZone("input/day15.txt");

    @Test
    void testPart1() {
        final var result = day15BeaconExclusionZone.part1();
        assertEquals(5_335_787, result);
    }

    @Test
    void testPart2() {
        final var result = day15BeaconExclusionZone.part2();
        assertEquals(13_673_971_349_056L, result);
    }

}
