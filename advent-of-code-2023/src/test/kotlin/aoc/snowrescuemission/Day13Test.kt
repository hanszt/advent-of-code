package aoc.snowrescuemission

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day13Test {

    @Test
    fun testPart1() {
        Day13("input/day13.txt").part1() shouldBe 27_502
    }

    @Test
    fun testPart1TestInput() {
        Day13(
            text = """
            #.##..##.
            ..#.##.#.
            ##......#
            ##......#
            ..#.##.#.
            ..##..##.
            #.#.##.#.

            #...##..#
            #....#..#
            ..##..###
            #####.##.
            #####.##.
            ..##..###
            #....#..#
        """.trimIndent()
        ).part1() shouldBe 405
    }

    @Test
    fun testPart2() {
        Day13("input/day13.txt").part2() shouldBe 27_502
    }

}