package aoc.snowrescuemission

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day11Test {

    @Test
    fun testPart1() {
        Day11("input/day11.txt").part1() shouldBe 9_536_038
    }

    @Test
    fun testPart2() {
        Day11("input/day11.txt").part2() shouldBe 6875
    }

    @Test
    fun testPart1SimpleInput() {
        Day11(
            image = """
                ...#......
                .......#..
                #.........
                ..........
                ......#...
                .#........
                .........#
                ..........
                .......#..
                #...#.....
            """.trimIndent()
        ).part1() shouldBe 374
    }
}