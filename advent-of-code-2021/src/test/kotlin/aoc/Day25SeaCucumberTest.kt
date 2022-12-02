package aoc

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day25SeaCucumberTest {

    @Test
    fun `test input`() = assertEquals(58, Day25SeaCucumber.part1("input/day25test.txt"))

    @Test
    fun `step at which no sea cucumber moves`() = assertEquals(429, Day25SeaCucumber.part1().also(::println))
}
