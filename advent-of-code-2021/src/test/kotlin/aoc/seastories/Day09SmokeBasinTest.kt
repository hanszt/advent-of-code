package aoc.seastories

import aoc.seastories.Day09SmokeBasin.Companion.findBassinPoints
import aoc.seastories.Day09SmokeBasin.Companion.toLowPoints
import aoc.utils.CYAN
import aoc.utils.grid2d.GridPoint2D
import aoc.utils.grid2d.toMutableIntGrid
import aoc.utils.withColor
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day09SmokeBasinTest {

    private val day09SmokeBasinTestInput = Day09SmokeBasin("input/day9test.txt")
    private val day09SmokeBasin = Day09SmokeBasin("input/day9.txt")

    @Test
    fun `part 1 test input`() {
        day09SmokeBasinTestInput.part1() shouldBe 15
    }

    @Test
    fun `part 1 result`() {
        day09SmokeBasin.part1().also(::println) shouldBe 530
    }

    @Test
    fun `part 2 test input`() {
        day09SmokeBasinTestInput.part2() shouldBe 1_134
    }

    @Test
    fun `part 2 result`() {
        day09SmokeBasin.part2().also(::println) shouldBe 1_019_494
    }

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

        val intGrid = inputGrid.lines().toMutableIntGrid(Char::digitToInt)

        val (lowestPoint) = intGrid.toLowPoints().first()

        val basinPoints = LinkedHashSet<GridPoint2D>().apply { add(lowestPoint) }
        intGrid.findBassinPoints(lowestPoint.x, lowestPoint.y, basinPoints)

        basinPoints.forEach { (x, y) -> println("x=$x, y=$y, value=${intGrid[y][x]}".withColor(CYAN)) }

        intGrid[lowestPoint.y][lowestPoint.x] shouldBe 5
        basinPoints shouldHaveSize expectedSize
    }
}
