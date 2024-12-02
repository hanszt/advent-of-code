package aoc.seastories

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day25SeaCucumberTest {

    @Test
    fun `test input`() {
        Day25SeaCucumber("input/day25test.txt").part1() shouldBe 58
    }

    @Test
    fun `step at which no sea cucumber moves`() {
        Day25SeaCucumber("input/day25.txt").part1().also(::println) shouldBe 429
    }
}
