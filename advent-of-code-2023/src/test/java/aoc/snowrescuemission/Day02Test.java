package aoc.snowrescuemission;

import org.hzt.utils.collections.ListX;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day02Test {

    @Test
    void testPart1() {
        assertEquals(2679, new Day02("input/day02.txt").part1());
    }

    @Test
    void testPart2TestInput() {
        final var lines = ListX.of(
                "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
                "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
                "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
                "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
                "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"
        );
        assertEquals(2286, new Day02(lines).part2());
    }

    @Test
    void testPart2() {
        assertEquals(77607, new Day02("input/day02.txt").part2());
    }
}
