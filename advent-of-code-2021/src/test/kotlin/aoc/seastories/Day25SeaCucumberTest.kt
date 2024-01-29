package aoc.seastories

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day25SeaCucumberTest {

    @Test
    fun `test input`() = assertEquals(58, Day25SeaCucumber("input/day25test.txt").part1())

    @Test
    fun `step at which no sea cucumber moves`() = assertEquals(429, Day25SeaCucumber("input/day25.txt").part1().also(::println))
}
