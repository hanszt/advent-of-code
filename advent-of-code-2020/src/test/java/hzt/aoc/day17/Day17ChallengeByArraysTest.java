package hzt.aoc.day17;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static aoc.utils.model.GridPoint3DKt.gridPoint3D;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day17ChallengeByArraysTest {

    private final Part1ConwayCubesByArrays conwayCubes = new Part1ConwayCubesByArrays();

    @Test
    void testPart1() {
        final var challenge = new Part1ConwayCubesByArrays();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(252, Integer.parseInt(answer));
    }

    @Test
    void testPart2() {
        final var challenge = new Part2ConwayCubesByArrays();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(2160, Integer.parseInt(answer));
    }

    @Test
    void countActiveNeighbors() {
        final boolean[][][] input = {{
                {true, false, true},
                {true, true, false},
                {false, false, false},
        }};
        System.out.println(conwayCubes.grid3DAsString(input));
        final int neighbours = conwayCubes.countActiveNeighbors(gridPoint3D(1, 1, 0), input);
        assertEquals(3, neighbours);
    }

    @Test
    void testAddInactiveOuterLayer2D() {
        boolean[][] grid = {{true}};

        final var enlargedGrid = Day17ChallengeByArrays.addInactiveOuterLayer2D(grid);

        final var nrOfElements = Arrays.stream(enlargedGrid)
                .mapToInt(a -> a.length)
                .sum();

        assertAll(
                () -> assertEquals(9, nrOfElements),
                () -> assertEquals(3, enlargedGrid.length),
                () -> assertEquals(3, enlargedGrid[0].length)
        );
    }

    @Test
    void testAddInactiveOuterLayer3D() {
        boolean[][][] grid = {{{true}}};

        final var enlargedGrid = Day17ChallengeByArrays.addInactiveOuterLayer3D(grid);

        final var nrOfElements = Arrays.stream(enlargedGrid)
                .flatMap(Arrays::stream)
                .mapToInt(a -> a.length)
                .sum();

        assertAll(
                () -> assertEquals(27, nrOfElements),
                () -> assertEquals(3, enlargedGrid.length),
                () -> assertEquals(3, enlargedGrid[0].length),
                () -> assertEquals(3, enlargedGrid[0][0].length)
        );
    }

    @Test
    void testAddInactiveOuterLayer4D() {
        boolean[][][][] grid = {{{{true}}}};

        final var enlargedGrid = Part2ConwayCubesByArrays.addInactiveOuterLayer4D(grid);

        final var nrOfElements = Arrays.stream(enlargedGrid)
                .flatMap(Arrays::stream)
                .flatMap(Arrays::stream)
                .mapToInt(a -> a.length)
                .sum();

        assertAll(
                () -> assertEquals(27 * 3, nrOfElements),
                () -> assertEquals(3, enlargedGrid.length),
                () -> assertEquals(3, enlargedGrid[0].length),
                () -> assertEquals(3, enlargedGrid[0][0].length)
        );
    }

    @Test
    void testCreateInactiveXyzGrid() {
        final var width = 3;
        final var height = 2;
        final var depth = 5;
        final var inActiveXYZGrid = Day17ChallengeByArrays.createInActiveXYZGrid(width, height, depth);

        final var volume = Arrays.stream(inActiveXYZGrid)
                .flatMap(Arrays::stream)
                .mapToInt(a -> a.length)
                .sum();

        assertAll(
                () -> assertEquals(volume, width * height * depth)
        );
    }
}
