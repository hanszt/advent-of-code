package aoc.seastories

import aoc.seastories.Day13TransparentOrigami.Companion.toExpectedTextOrElseThrow
import aoc.utils.BRIGHT_BLUE
import aoc.utils.GREEN
import aoc.utils.ICY_BG
import aoc.utils.RED
import aoc.utils.withColor
import aoc.utils.withColors
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day13TransparentOrigamiTest {

    @Test
    fun `part 1 test input`() = assertEquals(17, Day13TransparentOrigami("input/day13test.txt").part1())

    @Test
    fun `part 1 result`() = assertEquals(607, Day13TransparentOrigami("input/day13.txt").part1().also(::println))

    @Test
    fun `part 1 other input`() = assertEquals(693, Day13TransparentOrigami("input/day13-2.txt").part1())

    @Test
    fun `part 2 test input`() {
        val expected = """
            █████
            █...█
            █...█
            █...█
            █████
            .....
            .....
        """.trimIndent()
        val result = Day13TransparentOrigami("input/day13test.txt").part2GridAsString()
        println(result.withColor(GREEN))
        assertEquals(expected, result)
    }

    @Test
    fun `part 2 other input`() {
        val expected = """
          █..█..██..█....████.███...██..████.█..█.
          █..█.█..█.█.......█.█..█.█..█....█.█..█.
          █..█.█....█......█..█..█.█..█...█..█..█.
          █..█.█....█.....█...███..████..█...█..█.
          █..█.█..█.█....█....█.█..█..█.█....█..█.
          .██...██..████.████.█..█.█..█.████..██..
        """.trimIndent()
        val result = Day13TransparentOrigami("input/day13-2.txt").part2GridAsString()
        println(result.withColor(BRIGHT_BLUE))
        assertEquals(expected, result)
    }

    @Test
    fun `part 2 result as grid and as string`() {
        val grid = Day13TransparentOrigami("input/day13.txt").part2GridAsString()
        println(grid.withColor(RED))
        val text = grid.toExpectedTextOrElseThrow()
        println(text.withColors(RED, ICY_BG))
        assertEquals("CPZLPFZL", text)
    }
}
