package aoc.jungle.adventures;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day03RucksackReorganisationTest {

    final Day03RucksackReorganisation rucksackReorganisation = new Day03RucksackReorganisation("input/day03.txt");

    @Test
    void testPart1() {
        final var result = rucksackReorganisation.part1();
        System.out.println("result = " + result);
        assertEquals(7793, result);
    }

    @Test
    void testPart2() {
        final var result = rucksackReorganisation.part2();
        System.out.println("result = " + result);
        assertEquals(2499, result);
    }
    @ParameterizedTest(name = "{0} should map to priority {1}")
    @CsvSource(value = {"a, 1", "b, 2", "c, 3", "z, 26", "A, 27", "Z, 52"})
    void testCharacterToPriority(char letter, int expected) {
        assertEquals(expected, Day03RucksackReorganisation.toPriority(letter));
    }

}
