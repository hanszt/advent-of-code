package aoc.theme

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day01Test {

    private companion object {
        private val day01 = Day01(Path("input/day01.txt"))
        private val day01Dr = Day01(Path("input/day01-dr.txt"))
        private val day01Test = Day01(
            lines = """
                3   4
                4   3
                2   5
                1   3
                3   9
                3   3
            """.trimIndent().lines()
        )
    }

    @Test
    fun `test part 1`() {
        day01.part1() shouldBe 2_086_478
    }

    @Test
    fun `test part 1 v2`() {
        day01Dr.part1() shouldBe 2_904_518
    }

    @Test
    fun `test part 1 test`() {
        day01Test.part1() shouldBe 11
    }

    @Test
    fun `test part 2`() {
        day01.part2() shouldBe 24_941_624
    }

    @Test
    fun `test part 2 test`() {
        day01Test.part2() shouldBe 31
    }

    @Test
    fun `test part 2 v2`() {
        day01Dr.part2() shouldBe 18_650_129
    }
}