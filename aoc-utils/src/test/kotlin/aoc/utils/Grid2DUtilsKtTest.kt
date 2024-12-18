package aoc.utils

import aoc.utils.model.GridPoint2D.Companion.by
import aoc.utils.model.gridPoint2D
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.Month
import java.time.Year

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
    fun `test rotate rotates generic matrix by 90 deg clockwise`() {
        val input = listOf(
            listOf(gridPoint2D(1, 1), gridPoint2D(2, 2))
        )
        val expected = listOf(
            listOf(1 by 1),
            listOf(2 by 2)
        )
        val rotatedClockWise = input.rotated()

        rotatedClockWise shouldBe expected
    }

    @Test
    fun `test rotated rotates generic 2d array by 90 deg clockwise`() {
        val input = arrayOf(
            arrayOf(1 by 1, 2 by 2),
            arrayOf(3 by 3, 4 by 4)
        )
        val expected = arrayOf(
            arrayOf(3 by 3, 1 by 1),
            arrayOf(4 by 4, 2 by 2)
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
    fun `generic grid mirrored vertically`() {
        val expected = arrayOf(
            arrayOf(Year.of(4), Year.of(5), Year.of(6)),
            arrayOf(Year.of(1), Year.of(2), Year.of(3))
        )
        val input = arrayOf(
            arrayOf(Year.of(1), Year.of(2), Year.of(3)),
            arrayOf(Year.of(4), Year.of(5), Year.of(6))
        )
        val mirrored = input.mirroredVertically()

        mirrored shouldBe expected
    }

    @Test
    fun `grid mirrored horizontally`() {
        val expected = arrayOf(
            arrayOf("3", "2", "1"),
            arrayOf("6", "5", "4")
        )
        val input = arrayOf(
            arrayOf("1", "2", "3"),
            arrayOf("4", "5", "6")
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
        val originalGrid = input.lines().toGridOf { it }
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
        val monthGrid = input.lines().toGridOf(regex = oneOrMoreWhiteSpaces) { Month.of(it.toInt()) }

        monthGrid.gridAsString(separator = " ")

        monthGrid[0][0] shouldBe Month.DECEMBER
    }
}
