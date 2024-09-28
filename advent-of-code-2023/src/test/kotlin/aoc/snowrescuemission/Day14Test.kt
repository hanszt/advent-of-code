package aoc.snowrescuemission

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class Day14Test {

    @Test
    fun testPart1() {
        Day14("input/day14.txt").part1() shouldBe 108_826
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
    @Disabled("Not implemented")
    fun testPart2TestInput() {
        Day14(grid = testGrid).part2() shouldBe 64
    }
}