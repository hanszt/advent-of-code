package aoc.seastories

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day07TheTreacheryOfWalesTest {

    private companion object {
        private val day07TheTreacheryOfWalesTestInput = Day07TheTreacheryOfWales("input/day7test.txt")
        private val day07TheTreacheryOfWales = Day07TheTreacheryOfWales("input/day7.txt")
    }

    @Test
    fun `part 1 test input`() = assertEquals(37, day07TheTreacheryOfWalesTestInput.part1())

    @Test
    fun `part 1 result`() = assertEquals(340_987, day07TheTreacheryOfWales.part1().also(::println))

    @Test
    fun `part 2 test input`() = assertEquals(168, day07TheTreacheryOfWalesTestInput.part2())

    @Test
    fun `part 2 result`() = assertEquals(96_987_874, day07TheTreacheryOfWales.part2().also(::println))
}
