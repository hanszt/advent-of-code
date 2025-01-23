package aoc.snowrescuemission

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day14Test {

    private companion object {
        private val day14 = Day14("input/day14-dr.txt")
    }

    @Test
    fun testPart1() {
        day14.part1() shouldBe 108_826
    }

    @Test
    fun testPart2() {
        day14.part2() shouldBe 99_291
    }

    private val testGrid = """
                O....#....
                O.OO#....#
                .....##...
                OO.#O....O
                .O.....O#.
                O.#..O.#.#
                ..O..#O..O
                .......O..
                #....###..
                #OO..#....
            """.trimIndent().lines()

    @Test
    fun testPart1TestInput() {
        Day14(grid = testGrid).part1() shouldBe 136
    }

    @Test
    fun testPart2TestInput() {
        Day14(grid = testGrid).part2() shouldBe 64
    }
}