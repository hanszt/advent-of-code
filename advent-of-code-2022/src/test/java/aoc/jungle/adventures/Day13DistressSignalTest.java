package aoc.jungle.adventures;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

import static aoc.jungle.adventures.Day13DistressSignal.*;
import static org.hzt.utils.It.println;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class Day13DistressSignalTest {

    private final Day13DistressSignal day13 = new Day13DistressSignal("input/day13.txt");

    @Test
    void testPart1() {
        final var result = day13.part1();
        assertEquals(5003, result);
    }

    @Test
    void testPart1test() {
        final var result = new Day13DistressSignal("input/day13test.txt").part1();
        assertEquals(13, result);
    }

    @Test
    void testPart2() {
        final var result = day13.part2();
        assertEquals(20280, result);
    }

    @Test
    void testPart2test() {
        final var result = new Day13DistressSignal("input/day13test.txt").part2();
        assertEquals(140, result);
    }

    @TestFactory
    Stream<DynamicTest> testParseNrList() {
        return Stream.of(
                parseNrTest("[[4,3,4,[2]]]", 1, 4),
                parseNrTest("[1,[2,[3,[4,[5,6,7]]]],8,9]", 4, 9),
                parseNrTest("[[[0,7,1,[0,5,5]],[],10,4,[10,[0,5,8,3,10],6,4,5]],[5],[2],[[0],[2]],[6,[8],7,[[2],6,[8,4],[],7],2]]", 5, 30));
    }

    private DynamicTest parseNrTest(String nrAsString, int listSize, int expectedNrCount) {
        final var displayName = "%s should contain %d nrs and parse to compList of size %d".formatted(nrAsString, expectedNrCount, listSize);

        final var list = CompList.parse(new Parser(nrAsString));
        println(list.toTreeString(2, n -> n instanceof CompNumber nr ? String.valueOf(nr.value()) : "List:"));

        final var nrCount = list.depthFirstSequence().count(s -> s instanceof CompNumber);
        final var treeString = list.toTreeString("[", ",", "]",
                n -> n instanceof CompNumber nr ? String.valueOf(nr.value()) : n.isLeaf() ? "[]" : "");

        return dynamicTest(displayName, () -> assertAll(
                () -> assertEquals(listSize, list.childrenSequence().count()),
                () -> assertEquals(expectedNrCount, nrCount),
                () -> assertEquals(nrAsString, treeString)
        ));
    }
}
