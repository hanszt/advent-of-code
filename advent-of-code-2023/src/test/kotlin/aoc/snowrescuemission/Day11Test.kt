package aoc.snowrescuemission

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Day11Test {

    @Test
    fun testPart1() {
        Day11("input/day11.txt").part1() shouldBe 9_536_038
    }

    @Test
    fun testPart2() {
        Day11("input/day11.txt").part2() shouldBe 447_744_640_566
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

    @ParameterizedTest
    @CsvSource("100, 8410", "10, 1030")
    fun testPart2SimpleInput(multiplier: Long, expected: Int) {
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
        ).findSumDistancesGalaxies { it * (multiplier - 1) } shouldBe expected
    }
}