package aoc.seastories

import aoc.seastories.Day04GiantSquid.isWinningBoard
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class Day04GiantSquidTest {

    @Test
    fun `part 1 test input`() = assertEquals(4_512, Day04GiantSquid.part1("input/day4test.txt"))

    @Test
    fun `part 1 result`() = assertEquals(50_008, Day04GiantSquid.part1().also(::println))

    @Test
    fun `part 2 test input`() = assertEquals(1_924, Day04GiantSquid.part2("input/day4test.txt"))

    @Test
    fun `part 2 result`() = assertEquals(17_408, Day04GiantSquid.part2().also(::println))

    @Test
    fun testWinningBoardTrueIfPresentInRow() {
        val nrsDrawn = listOf(1, 4, 5, 7)
        val winningBoard = arrayOf(
            intArrayOf(7, 4, 5, 1),
            intArrayOf(2, 3, 6, 8),
            intArrayOf(12, 11, 10, 9),
            intArrayOf(13, 14, 15, 16)
        )
        assertTrue(winningBoard.isWinningBoard(nrsDrawn))
    }

    @Test
    fun testWinningBoardTrueIfPresentInCol() {
        val nrsDrawn = listOf(1, 4, 5, 7)
        val winningBoard = arrayOf(
            intArrayOf(1, 6, 9, 8),
            intArrayOf(4, 2, 3, 10),
            intArrayOf(7, 11, 12, 13),
            intArrayOf(5, 16, 15, 14)
        )
        assertTrue(winningBoard.isWinningBoard(nrsDrawn))
    }

    @Test
    fun testWinningBoardFalseIfNotPresentInRowOrCol() {
        val nrsDrawn = listOf(1, 4, 5, 7)
        val winningBoard = arrayOf(
            intArrayOf(6, 4, 5, 1),
            intArrayOf(2, 3, 7, 8),
            intArrayOf(12, 11, 10, 9),
            intArrayOf(13, 14, 15, 16)
        )
        assertFalse(winningBoard.isWinningBoard(nrsDrawn))
    }
}
