package aoc.seastories

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day03BinaryDiagnosticTest {

    private val day03BinaryDiagnosticTestInput = Day03BinaryDiagnostic("input/day3test.txt")
    private val day03BinaryDiagnostic = Day03BinaryDiagnostic("input/day3.txt")

    @Test
    fun `part 1 test input`() = assertEquals(198, day03BinaryDiagnosticTestInput.part1())

    @Test
    fun `part 1 result`() = assertEquals(3_633_500, day03BinaryDiagnostic.part1().also(::println))

    @Test
    fun `part 2 test input`() = assertEquals(230, day03BinaryDiagnosticTestInput.part2())

    @Test
    fun `part 2 result`() = assertEquals(4_550_283, day03BinaryDiagnostic.part2().also(::println))
}
