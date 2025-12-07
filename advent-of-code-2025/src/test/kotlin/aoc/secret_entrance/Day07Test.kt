package aoc.secret_entrance

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day07Test {

    private companion object {
        private val day07 = Day07(Path("input/day07.txt"))
        private val day07TestInput = Day07(
            """
                .......S.......
                ...............
                .......^.......
                ...............
                ......^.^......
                ...............
                .....^.^.^.....
                ...............
                ....^.^...^....
                ...............
                ...^.^...^.^...
                ...............
                ..^...^.....^..
                ...............
                .^.^.^.^.^...^.
                ...............
            """.trimIndent().lines()
        )
    }

    @Test
    fun testPart1TestInput() {
        day07TestInput.part1() shouldBe 21
    }

    @Test
    fun testPart1() {
        day07.part1() shouldBe 1642
    }

    @Test
    fun testPart2TestInput() {
        day07TestInput.part2() shouldBe 40
    }

    @Test
    fun testPart2() {
        day07.part2() shouldBe 47274292756692L
    }
}
