package aoc.seastories

import aoc.seastories.Day09SmokeBasin.Companion.findBassinPoints
import aoc.seastories.Day09SmokeBasin.Companion.toLowPoints
import aoc.utils.CYAN
import aoc.utils.model.GridPoint2D
import aoc.utils.toIntGrid
import aoc.utils.withColor
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day09SmokeBasinTest {

    private val day09SmokeBasinTestInput = Day09SmokeBasin("input/day9test.txt")
    private val day09SmokeBasin = Day09SmokeBasin("input/day9.txt")

    @Test
    fun `part 1 test input`() = assertEquals(15, day09SmokeBasinTestInput.part1())

    @Test
    fun `part 1 result`() = assertEquals(530, day09SmokeBasin.part1().also(::println))

    @Test
    fun `part 2 test input`() = assertEquals(1_134, day09SmokeBasinTestInput.part2())

    @Test
    fun `part 2 result`() = assertEquals(1_019_494, day09SmokeBasin.part2().also(::println))

    @Test
    fun testFindBasinPoints() {
        val inputGrid = """
                    998789
                    985678
                    876789
                    989996
                """.trimIndent()
        val expectedBassin = """
                      878 
                     85678
                    87678 
                     8
        """.trimIndent()
        val expectedSize = expectedBassin.filter(Char::isLetterOrDigit).length

        val intGrid = inputGrid.lines().toIntGrid(Char::digitToInt)

        val (lowestPoint) = intGrid.toLowPoints().first()

        val basinPoints = LinkedHashSet<GridPoint2D>().apply { add(lowestPoint) }
        intGrid.findBassinPoints(lowestPoint.x, lowestPoint.y, basinPoints)

        basinPoints.forEach { (x, y) -> println("x=$x, y=$y, value=${intGrid[y][x]}".withColor(CYAN)) }

        assertEquals(5, intGrid[lowestPoint.y][lowestPoint.x])
        assertEquals(expectedSize, basinPoints.size)
    }
}
