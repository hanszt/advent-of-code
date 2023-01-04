package aoc.jungle.adventures;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hzt.utils.It.println;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.*;

class Day25FullOfHotAirTest {

    private final Day25FullOfHotAir day25FullOfHotAir = new Day25FullOfHotAir("input/day25.txt");

    @Test
    void testPart1() {
        final var result = day25FullOfHotAir.part1();
        println(result);
        assertEquals("122-2=200-0111--=200", result);
    }

    @Test
    void testPart2() {
        final var result = day25FullOfHotAir.part2();
        println(result);
        assertEquals("No part 2. Merry christmas! 🎄", result);
    }

    @ParameterizedTest(name = "Snafu nr `{0}`, should convert to decimal: {1}")
    @MethodSource("nrs")
    void testFromSnafu(String snafuNr, int expected) {
        assertEquals(expected, day25FullOfHotAir.fromSnafu(snafuNr));
    }

    @ParameterizedTest(name = "Decimal: {1} should convert to snafuNr: `{0}`")
    @MethodSource("nrs")
    void testToSnafu(String expected, int nr) {
        assertEquals(expected, day25FullOfHotAir.toSnafu(nr));
    }

    private static Stream<Arguments> nrs() {
        return Stream.of(
                "1, 1",
                "2, 2",
                "1=, 3",
                "1-, 4",
                "10, 5",
                "11, 6",
                "12, 7",
                "2=, 8",
                "2-, 9",
                "20, 10",
                "1=0, 15",
                "1-0, 20",
                "1=11-2, 2022",
                "1-0---0, 12345",
                "1121-1110-1=0, 314159265"
        ).map(Day25FullOfHotAirTest::toArguments);
    }

    @NotNull
    private static Arguments toArguments(String s) {
        final var split = s.split(", ");
        return arguments(split[0], split[1]);
    }

}
