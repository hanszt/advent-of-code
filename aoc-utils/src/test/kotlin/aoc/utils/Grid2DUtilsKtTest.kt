package aoc.utils

import aoc.utils.grid2d.*
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.Month

internal class Grid2DUtilsKtTest {

    @Test
    fun `test rotated rotates int matrix by 90 deg clockwise`() {
        val input = arrayOf(
            intArrayOf(1, 2, 3),
            intArrayOf(4, 5, 6)
        )
        val expected = arrayOf(
            intArrayOf(4, 1),
            intArrayOf(5, 2),
            intArrayOf(6, 3)
        )
        val rotatedClockWise = input.rotated()

        rotatedClockWise shouldBe expected
    }

    @Test
    fun `test rotated counter clockwise rotates int matrix by 90 deg counter clockwise`() {
        val input = arrayOf(
            intArrayOf(1, 2, 3),
            intArrayOf(4, 5, 6)
        )
        val expected = arrayOf(
            intArrayOf(3, 6),
            intArrayOf(2, 5),
            intArrayOf(1, 4)
        )
        val rotatedCounterClockWise = input.rotatedCc()

        rotatedCounterClockWise shouldBe expected
    }

    @Test
    fun `test rotated rotates char 2d array by 90 deg clockwise`() {
        val input = arrayOf(
            charArrayOf('1', '2'),
            charArrayOf('3', '4')
        )
        val expected = arrayOf(
            charArrayOf('3', '1'),
            charArrayOf('4', '2')
        )
        val rotatedClockWise = input.rotated()

        rotatedClockWise shouldBe expected
    }

    @Test
    fun `test rotated rotates String list by 90 deg clockwise`() {
        val input = listOf(
            "123q",
            "456w"
        )
        val expected = listOf(
            "41",
            "52",
            "63",
            "wq"
        )
        val rotatedClockWise = input.rotated()

        rotatedClockWise shouldBe expected
    }

    @Test
    fun `int grid mirrored horizontally`() {
        val expected = arrayOf(
            intArrayOf(3, 2, 1),
            intArrayOf(6, 5, 4)
        )
        val input = arrayOf(
            intArrayOf(1, 2, 3),
            intArrayOf(4, 5, 6)
        )
        val mirrored = input.mirroredHorizontally()

        mirrored shouldBe expected
    }

    @Test
    fun `int grid mirrored vertically`() {
        val expected = arrayOf(
            intArrayOf(4, 5, 6),
            intArrayOf(1, 2, 3)
        )
        val input = arrayOf(
            intArrayOf(1, 2, 3),
            intArrayOf(4, 5, 6)
        )
        val mirrored = input.mirroredVertically()

        mirrored shouldBe expected
    }

    @Test
    fun `char grid mirrored vertically`() {
        val expected = arrayOf(
            charArrayOf('4', '5', '6'),
            charArrayOf('1', '2', '3')
        )
        val input = arrayOf(
            charArrayOf('1', '2', '3'),
            charArrayOf('4', '5', '6')
        )
        val mirrored = input.mirroredVertically()

        mirrored shouldBe expected
    }

    @Test
    fun `grid mirrored horizontally`() {
        val expected = arrayOf(
            charArrayOf('3', '2', '1'),
            charArrayOf('6', '5', '4')
        )
        val input = arrayOf(
            charArrayOf('1', '2', '3'),
            charArrayOf('4', '5', '6')
        )
        val mirrored = input.mirroredHorizontally()

        mirrored shouldBe expected
    }

    @Test
    fun `a copied grid should be unaffected by changes in original grid`() {
        val input = """
            1234
            4321
            1234
        """.trimIndent()
        val originalGrid = input.lines().toMutableGrid { it }
        val copy = originalGrid.copyGrid()
        originalGrid[0][0] = 'a'

        originalGrid[0][0] shouldBe 'a'
        copy[0][0] shouldBe '1'
    }

    @Test
    fun `test from string grid to month grid using gridOf`() {
        val input = """
            12 3
            5  2
            11 4
        """.trimIndent()
        val monthGrid = input.lines().toMutableGrid(regex = oneOrMoreWhiteSpaces) { Month.of(it.toInt()) }

        monthGrid.gridAsString(separator = " ")

        monthGrid[0][0] shouldBe Month.DECEMBER
    }

    @Nested
    inner class GridTest {

        @Test
        fun `iterable of points to grid`() {
            val s = listOf(gridPoint2D(1, 1), gridPoint2D(2, 2), gridPoint2D(3, 3))
                .toGrid { x, y, isWall -> if (isWall) "#" else "." }
                .toString()

            s shouldBe """
              | # . .
              | . # .
              | . . #
            """.trimMargin()
        }
    }
}
