package aoc.secret_entrance

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day04Test {

    private companion object {
        private val day04 = Day04(Path("input/day04.txt"))
        private val day04TestInput = Day04(
            """
                ..@@.@@@@.
                @@@.@.@.@@
                @@@@@.@.@@
                @.@@@@..@.
                @@.@@@@.@@
                .@@@@@@@.@
                .@.@.@.@@@
                @.@@@.@@@@
                .@@@@@@@@.
                @.@.@@@.@.
            """.trimIndent().lines()
        )
    }

    @Test
    fun testPart1TestInput() {
        day04TestInput.part1() shouldBe 13
    }

    @Test
    fun testPart1() {
        day04.part1() shouldBe 1356
    }

    @Test
    fun testPart2TestInput() {
        day04TestInput.part2() shouldBe 43
    }

    @Test
    fun testPart2() {
        day04.part2() shouldBe 8713
    }

}