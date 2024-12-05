package aoc.historianhysteria

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day04Test {


    private companion object {
        private val day04 by lazy { Day04(Path("input/day04.txt")) }
        private val day04dr by lazy { Day04(Path("input/day04-dr.txt")) }
        private val testInput = """
            MMMSXXMASM
            MSAMXMSMSA
            AMXSXMAAMM
            MSAMASMSMX
            XMASAMXAMM
            XXAMMXXAMA
            SMSMSASXSS
            SAXAMASAAA
            MAMMMXMMMM
            MXMXAXMASX
        """.trimIndent()
    }

    @Test
    fun part1TestInput() {
        Day04(testInput).part1() shouldBe 18
    }

    @Test
    fun part2TestInput() {
        Day04(testInput).part2() shouldBe 9
    }

    @Test
    fun part1dr() {
        day04dr.part1() shouldBe 2599
    }

    @Test
    fun part2dr() {
        day04dr.part2() shouldBe 1948
    }

    @Test
    fun part1() {
        val expected = 2662
        day04.part1() shouldBe expected
    }

    @Test
    fun part2() {
        val expected = 2034
        day04.part2() shouldBe expected
    }

}
