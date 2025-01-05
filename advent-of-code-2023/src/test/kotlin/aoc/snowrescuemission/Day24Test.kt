package aoc.snowrescuemission

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day24Test {

    private companion object {
        private val day24TestInput = Day24(
            input = """
                        19, 13, 30 @ -2,  1, -2
                        18, 19, 22 @ -1, -1, -2
                        20, 25, 34 @ -2, -2, -4
                        12, 31, 28 @ -1, -2, -1
                        20, 19, 15 @  1, -5, -3
                    """.trimIndent().lines(),
            min = 7,
            max = 27
        )
        private val day24 = Day24(Path("input/day24.txt"))
    }

    @Test
    fun testPart1TestInput() {
        day24TestInput.part1() shouldBe 2
    }

    @Test
    fun testPart2TestInput() {
        day24TestInput.part2() shouldBe 47
    }


    @Test
    fun testPart1() {
        day24.part1() shouldBe 20361
    }

    @Test
    fun testPart2() {
        day24.part2() shouldBe 558415252330828
    }
}
