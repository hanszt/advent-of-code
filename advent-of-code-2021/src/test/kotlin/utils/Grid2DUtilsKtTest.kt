package utils

import model.GridPoint2D
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
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
        println(rotatedClockWise.gridAsString())
        assertArrayEquals(expected, rotatedClockWise)
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
        println(rotatedCounterClockWise.gridAsString())
        assertArrayEquals(expected, rotatedCounterClockWise)
    }

    @Test
    fun `test rotate rotates generic matrix by 90 deg clockwise`() {
        val input = listOf(
            listOf(GridPoint2D(1, 1), GridPoint2D(2, 2))
        )
        val expected = listOf(
            listOf(GridPoint2D(1, 1)),
            listOf(GridPoint2D(2, 2))
        )
        val rotatedClockWise = input.rotate()
        rotatedClockWise.forEach(::println)
        assertEquals(expected, rotatedClockWise)
    }

    @Test
    fun `test rotated rotates generic 2d array by 90 deg clockwise`() {
        val input = arrayOf(
            arrayOf(GridPoint2D(1, 1), GridPoint2D(2, 2)),
            arrayOf(GridPoint2D(3, 3), GridPoint2D(4, 4))
        )
        val expected = arrayOf(
            arrayOf(GridPoint2D(3, 3), GridPoint2D(1, 1)),
            arrayOf(GridPoint2D(4, 4), GridPoint2D(2, 2))
        )
        val rotatedClockWise = input.rotated()
        println(rotatedClockWise.gridAsString(1))
        assertArrayEquals(expected, rotatedClockWise)
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
        rotatedClockWise.forEach(::println)
        assertEquals(expected, rotatedClockWise)
    }

    @Test
    fun `int grid mirrored horizontally`() {
        val expected = arrayOf(
            intArrayOf(4, 5, 6),
            intArrayOf(1, 2, 3)
        )
        val input = arrayOf(
            intArrayOf(1, 2, 3),
            intArrayOf(4, 5, 6)
        )
        val mirrored = input.mirroredHorizontally()
        println(mirrored.gridAsString().withColor(BRIGHT_GREEN))
        assertArrayEquals(expected, mirrored)
    }

    @Test
    fun `int grid mirrored vertically`() {
        val expected = arrayOf(
            intArrayOf(3, 2, 1),
            intArrayOf(6, 5, 4)
        )
        val input = arrayOf(
            intArrayOf(1, 2, 3),
            intArrayOf(4, 5, 6)
        )
        val mirrored = input.mirroredVertically()
        println(mirrored.gridAsString().withColor(YELLOW))
        assertArrayEquals(expected, mirrored)
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
        println(mirrored.gridAsString())
        assertArrayEquals(expected, mirrored)
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
        println(mirrored.gridAsString())
        assertArrayEquals(expected, mirrored)
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

        assertEquals('a', originalGrid[0][0])
        assertEquals('1', copy[0][0])
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

        assertEquals(Month.DECEMBER, monthGrid[0][0])
    }
}
