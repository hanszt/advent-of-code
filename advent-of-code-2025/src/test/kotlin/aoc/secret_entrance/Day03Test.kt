package aoc.secret_entrance

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day03Test {

    private companion object {
        private val day03 = Day03(Path("input/day03.txt"))
        private val day03TestInput = Day03(
            """
                987654321111111
                811111111111119
                234234234234278
                818181911112111
            """.trimIndent().lines()
        )
    }

    @Test
    fun testPart1TestInput() {
        day03TestInput.part1() shouldBe 357
    }

    @Test
    fun testPart1() {
        day03.part1() shouldBe 16887
    }

    @Test
    fun testPart2TestInput() {
        day03TestInput.part2() shouldBe 3121910778619
    }

}