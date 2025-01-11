package aoc.snowrescuemission

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * [Reference to day 9](https://www.reddit.com/r/adventofcode/comments/18orn0s/2023_day_21_part_2_links_between_days/?rdt=36924)
 */
class Day21Test {

    private companion object {
        private val day21 by lazy { Day21(Path("input/day21.txt")) }
        private val day21TestInput = Day21(
            grid = """
                      ...........
                      .....###.#.
                      .###.##..#.
                      ..#.#...#..
                      ....#.#....
                      .##..S####.
                      .##..#...#.
                      .......##..
                      .##.#.####.
                      .##..##.##.
                      ...........
                  """.trimIndent().lines(),
            availableSteps = 6
        )
    }

    @Test
    fun testPart1TestInput() {
        day21TestInput.part1() shouldBe 16
    }

    @Disabled("Not working for some test inputs")
    @ParameterizedTest
    @CsvSource(
        value = [
            "6 -> 16",
            "10 -> 50",
            "50 -> 1594",
            "100 -> 6536",
            "500 -> 167004",
            "1000 -> 668697",
            "5000 -> 16733044"],
        delimiterString = " -> "
    )
    fun testPart2TestInput(limit: Int, expected: Long) {
        day21TestInput.part2Zebalu(maxSteps = limit) shouldBe expected
    }

    @Test
    fun testPart1() {
        val expected = 3658
        day21.part1() shouldBe expected
        Day21Zebalu(Path("input/day21.txt").readLines()).part1(64) shouldBe expected
    }

    @Test
    fun testPart2() {
        val expected = 608193767979991
        day21.part2() shouldBe expected
        day21.part2Zebalu() shouldBe expected
    }
}
