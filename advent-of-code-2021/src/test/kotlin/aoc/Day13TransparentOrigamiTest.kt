package aoc

import aoc.Day13TransparentOrigami.toExpectedTextOrElseThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.*

internal class Day13TransparentOrigamiTest {

    @Test
    fun `part 1 test input`() = assertEquals(17, Day13TransparentOrigami.part1("input/day13test.txt"))

    @Test
    fun `part 1 result`() = assertEquals(607, Day13TransparentOrigami.part1().also(::println))

    @Test
    fun `part 1 other input`() = assertEquals(693, Day13TransparentOrigami.part1("input/day13-2.txt"))

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
        val result = Day13TransparentOrigami.part2GridAsString("input/day13test.txt")
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
        val result = Day13TransparentOrigami.part2GridAsString("input/day13-2.txt")
        println(result.withColor(BRIGHT_BLUE))
        assertEquals(expected, result)
    }

    @Test
    fun `part 2 result as grid and as string`() {
        val grid = Day13TransparentOrigami.part2GridAsString("input/day13.txt")
        println(grid.withColor(RED))
        val text = grid.toExpectedTextOrElseThrow()
        println(text.withColors(RED, ICY_BG))
        assertEquals("CPZLPFZL", text)
    }
}
