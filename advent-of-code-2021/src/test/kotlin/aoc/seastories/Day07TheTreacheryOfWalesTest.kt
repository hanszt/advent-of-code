package aoc.seastories

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day07TheTreacheryOfWalesTest {

    @Test
    fun `part 1 test input`() = assertEquals(37, Day07TheTreacheryOfWales.part1("input/day7test.txt"))

    @Test
    fun `part 1 result`() = assertEquals(340_987, Day07TheTreacheryOfWales.part1().also(::println))

    @Test
    fun `part 2 test input`() = assertEquals(168, Day07TheTreacheryOfWales.part2("input/day7test.txt"))

    @Test
    fun `part 2 result`() = assertEquals(96_987_874, Day07TheTreacheryOfWales.part2().also(::println))
}
