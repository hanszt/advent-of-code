package hzt.aoc.day07;

import hzt.aoc.io.IOController2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day07ChallengeTest {

    @Test
    void testPart1() {
        final var challenge = new Part1HandyHaversacks();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(378, Integer.parseInt(answer));
    }

    @Test
    void testPart2() {
        final var challenge = new Part2HandyHaversacks();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(27526, Integer.parseInt(answer));
    }

    @Test
    void testGetBagColorMap() {
        final var strings = new IOController2().readInputFileByLine("20201207-input-day7.txt");
        final var colorBagMap = new Part1HandyHaversacks().getColorBagMap(strings);
        final var mutedTurquoise = colorBagMap.get("muted turquoise");

        final var expected = mutedTurquoise.toTreeString(1).trim();

        final var actual = mutedTurquoise.depthFirstDepthTrackingSequence()
                .map(e -> " ".repeat(e.treeDepth()) + e.node())
                .joinToString("\n");

        System.out.println("actual = " + actual);

        assertEquals(expected, actual);
    }
}
