package aoc.snowrescuemission

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day18Test {

    private companion object {
        private val day18TestInput = Day18("""
            R 6 (#70c710)
            D 5 (#0dc571)
            L 2 (#5713f0)
            D 2 (#d2c081)
            R 2 (#59c680)
            D 2 (#411b91)
            L 5 (#8ceee2)
            U 2 (#caa173)
            L 1 (#1b58a2)
            U 2 (#caa171)
            R 2 (#7807d2)
            U 3 (#a77fa3)
            L 2 (#015232)
            U 2 (#7a21e3)
        """.trimIndent().lines())

        private val day18 = Day18(("input/day18.txt"))
    }

    @Test
    fun part1TestInput() {
        day18TestInput.part1() shouldBe 62
    }

    @Test
    fun part2TestInput() {
        day18TestInput.part2() shouldBe 952408144115
    }

    @Test
    fun testPart1() {
        day18.part1() shouldBe 106459
    }

    @Test
    fun testPart2() {
        day18.part2() shouldBe 63806916814808
    }
}
