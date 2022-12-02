package hzt.aoc.day11;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static hzt.aoc.day11.Day11Challenge.FLOOR;
import static hzt.aoc.day11.Day11Challenge.OCCUPIED_SEAT;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Part2CrabCupsDockingDataSeatingSystemTest {

    @Test
    void isOccupiedAfterUpdate() {
        final int row = 3;
        final int col = 3;
        final char[][] grid = {
                {'L', 'L', 'L', 'L'},
                {'L', 'L', 'L', 'L'},
                {'L', 'L', '.', 'L'},
                {'L', '.', 'L', 'L'}};
        final int occupied = occupiedSeatsInLineOfSight(grid, row, col);
        final var gridAsString = Arrays.stream(grid)
                .map(Arrays::toString)
                .collect(Collectors.joining("\n"));

        System.out.println("\n" + gridAsString);
        assertEquals(0, occupied);

    }

    @Test
    void isOccupiedAfterUpdate1() {
        final int row = 3;
        final int col = 2;
        final char[][] grid = {
                {'#', '#', '#', '#'},
                {'#', '#', '#', '#'},
                {'#', '#', '.', '#'},
                {'#', '.', '#', '#'}};
        final int occupied = occupiedSeatsInLineOfSight(grid, row, col);
        final var gridAsString = Arrays.stream(grid)
                .map(Arrays::toString)
                .collect(Collectors.joining("\n"));

        System.out.println("\n" + gridAsString);
        assertEquals(5, occupied);

    }

    @Test
    void isOccupiedAfterUpdate3() {
        final int row = 3;
        final int col = 2;
        final char[][] grid = {
                {'#', '#', '#', '#'},
                {'#', '#', '#', '#'},
                {'#', '#', '.', '#'},
                {'#', '.', '#', '#'}};
        final int occupied = occupiedSeatsInLineOfSight(grid, row, col);
        final var gridAsString = Arrays.stream(grid)
                .map(Arrays::toString)
                .collect(Collectors.joining("\n"));

        System.out.println("\n" + gridAsString);
        assertEquals(5, occupied);

    }

    @Test
    void isOccupiedAfterUpdate2() {
        final int row = 2;
        final int col = 2;

        final char[][] grid = {
                {'#', '#', '#', '#'},
                {'#', '#', '#', '#'},
                {'#', '#', '.', '#'},
                {'#', '.', '#', '#'}};

        final int occupied = occupiedSeatsInLineOfSight(grid, row, col);

        final var gridAsString = Arrays.stream(grid)
                .map(Arrays::toString)
                .collect(Collectors.joining("\n"));

        System.out.println("\n" + gridAsString);

        assertEquals(7, occupied);

    }

    @Test
    void isOccupiedAfterUpdate4() {
        final List<Integer> occupiedValues = new ArrayList<>();

        final char[][] grid = {
                {'#', '.', '#', '#', '#'},
                {'.', '.', '#', '.', '#'},
                {'#', '#', '#', '#', '#'},
                {'#', '.', '#', '#', '#'},
                {'#', '.', '#', '#', '#'},
                {'#', '.', '#', '#', '#'}};

        final int[] expected = {
                3, 4, 5, 5, 3,
                4, 6, 6, 8, 5,
                5, 5, 8, 8, 5,
                5, 7, 8, 8, 5,
                4, 7, 7, 8, 5,
                3, 5, 5, 5, 3,};

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                final int occupied = occupiedSeatsInLineOfSight(grid, row, col);
                occupiedValues.add(occupied);
                System.out.print(occupied + ",");
            }
            System.out.println();
        }

        final var gridAsString = Arrays.stream(grid)
                .map(Arrays::toString)
                .collect(Collectors.joining("\n"));

        System.out.println(gridAsString);

        final int[] actual = occupiedValues.stream().mapToInt(i -> i).toArray();

        assertArrayEquals(expected, actual);

    }

    @Test
    void isOccupiedAfterUpdate5() {
        final char[][] grid = {
                {'#', '.', '#', '#', '.', '#', '#', '.', '#', '#'},
                {'#', '#', '#', '#', '#', '#', '#', '.', '#', '#'},
                {'#', '.', '#', '.', '#', '.', '.', '#', '.', '.'},
                {'#', '#', '#', '#', '.', '#', '#', '.', '#', '#'},
                {'#', '.', '#', '#', '.', '#', '#', '.', '#', '#'},
                {'#', '.', '#', '#', '#', '#', '#', '.', '#', '#'},
                {'.', '.', '#', '.', '#', '.', '.', '.', '.', '.'},
                {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
                {'#', '.', '#', '#', '#', '#', '#', '#', '.', '#'},
                {'#', '.', '#', '#', '#', '#', '#', '.', '#', '#'}};

        final int[] expected = {
                3, 5, 5, 5, 5, 5, 5, 5, 5, 3,
                4, 7, 7, 7, 7, 7, 7, 7, 6, 5,
                5, 8, 8, 8, 8, 8, 8, 6, 7, 5,
                5, 8, 8, 8, 8, 8, 8, 8, 7, 5,
                5, 8, 7, 8, 8, 8, 8, 8, 8, 5,
                5, 7, 8, 8, 8, 8, 8, 8, 7, 5,
                5, 7, 7, 8, 7, 7, 7, 7, 7, 5,
                5, 6, 8, 8, 8, 8, 8, 8, 7, 4,
                4, 7, 7, 8, 8, 8, 7, 7, 7, 5,
                3, 5, 5, 5, 5, 5, 5, 5, 5, 3,};

        final List<Integer> occupiedValues = new ArrayList<>();

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                final int occupied = occupiedSeatsInLineOfSight(grid, row, col);
                occupiedValues.add(occupied);
                System.out.print(occupied + ",");
            }
            System.out.println();
        }

        final var gridAsString = Arrays.stream(grid)
                .map(Arrays::toString)
                .collect(Collectors.joining("\n"));

        System.out.println(gridAsString);

        final int[] actual = occupiedValues.stream().mapToInt(i -> i).toArray();
        assertArrayEquals(expected, actual);

    }

    private static final int[][] DIRECTIONS = {
            {1, 0}, {1, 1},
            {0, 1}, {-1, 1},
            {-1, 0}, {-1, -1},
            {0, -1}, {1, -1}};

    int occupiedSeatsInLineOfSight(final char[][] curGrid, final int row, final int col) {
        int occupiedInLineOfSight = 0;
        for (final int[] dir : DIRECTIONS) {
            if (occupiedInLineOfSight(curGrid, row, col, dir)) {
                occupiedInLineOfSight++;
            }
        }
        return occupiedInLineOfSight;
    }

    private boolean occupiedInLineOfSight(final char[][] curGrid, final int row, final int col, final int[] dir) {
        int dRow = row;
        int dCol = col;
        while (dRow >= 0 && dRow < curGrid.length && dCol >= 0 && dCol < curGrid[0].length) {
            if (row != dRow || col != dCol) {
                final char checked = curGrid[dRow][dCol];
                if (checked != FLOOR) {
                    if (checked == OCCUPIED_SEAT) {
                        return true;
                    }
                    break;
                }
            }
            dCol += dir[0];
            dRow += dir[1];
        }
        return false;
    }


}
