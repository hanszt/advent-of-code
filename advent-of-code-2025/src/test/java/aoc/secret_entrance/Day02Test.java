package aoc.secret_entrance;

import org.hzt.utils.io.FileX;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day02Test {

    private static final Day02 day02 = new Day02(FileX.of(Path.of("input/day02.txt")).readText());
    private static final Day02 day02Sample = new Day02("11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124");

    @Test
    void testPart1() {
        assertEquals(31839939622L, day02.part1());
    }

    @Test
    void testPart2Sample() {
        assertEquals(4174379265L, day02Sample.part2());
    }

    @Test
    void testPart2() {
        assertEquals(41662374059L, day02.part2());
    }

}