package aoc.secret_entrance

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day06Test {

    private companion object {
        private val day06 = Day06(Path("input/day06.txt"))
        private val day06TestInput = Day06(
            """
                123 328  51 64 
                 45 64  387 23 
                  6 98  215 314
                *   +   *   +  
            """.trimIndent().lines()
        )
    }

    @Test
    fun testPart1TestInput() {
        day06TestInput.part1() shouldBe 4277556
    }

    @Test
    fun testPart1() {
        day06.part1() shouldBe 5316572080628
    }

    @Test
    fun testPart2TestInput() {
        day06TestInput.part2() shouldBe 3263827
    }

    @Test
    fun testPart2() {
        day06.part2() shouldBe 11299263623062L
    }
}
