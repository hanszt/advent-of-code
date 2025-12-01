package aoc.theme

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day01Test {

    private companion object {
        private val day01 = Day01(Path("input/day01.txt"))
        private val day01TestInput = Day01(
            listOf(
                "L68",
                "L30",
                "R48",
                "L5",
                "R60",
                "L55",
                "L1",
                "L99",
                "R14",
                "L82",
            )
        )
    }

    @Test
    fun `test part 1`() {
        day01.part1() shouldBe 1180
    }

    @Test
    fun `test part 2 test input`() {
        day01TestInput.part2() shouldBe 6
    }

    @Test
    fun `test part 2`() {
        day01.part2() shouldBe 6892 // 5665, 6312 Too low // 6911 Too high
    }
}