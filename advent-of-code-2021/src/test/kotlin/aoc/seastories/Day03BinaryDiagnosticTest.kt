package aoc.seastories

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day03BinaryDiagnosticTest {

    @Test
    fun `part 1 test input`() = assertEquals(198, Day03BinaryDiagnostic.part1("input/day3test.txt"))

    @Test
    fun `part 1 result`() = assertEquals(3_633_500, Day03BinaryDiagnostic.part1().also(::println))

    @Test
    fun `part 2 test input`() = assertEquals(230, Day03BinaryDiagnostic.part2("input/day3test.txt"))

    @Test
    fun `part 2 result`() = assertEquals(4_550_283, Day03BinaryDiagnostic.part2().also(::println))
}
