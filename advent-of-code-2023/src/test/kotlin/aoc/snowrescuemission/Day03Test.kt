package aoc.snowrescuemission

import aoc.utils.grid2d.gridPoint2D
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day03Test {

    private val testInput = listOf(
        "467..114..",
        "...*......",
        "..35..633.",
        "......#...",
        "617*......",
        ".....+.58.",
        "..592.....",
        "......755.",
        "...$.*....",
        ".664.598.."
    )

    @Test
    fun testPart1test() {
        Day03(lines = testInput).part1() shouldBe 4_361
    }

    @Test
    fun testPart1() {
        Day03("input/day03.txt").part1() shouldBe 537_832
    }

    @Test
    fun testPart2test() {
        Day03(lines = testInput).part2() shouldBe 467_835
    }

    @Test
    fun testPart2NrsAroundGearSameLine() {
        Day03(lines = listOf("500*4....10*100")).part2() shouldBe 3_000
    }

    @Test
    fun testPart2() {
        Day03("input/day03.txt").part2() shouldBe 81_939_900 // higher than 72430769, 77629054,
    }

    @Test
    fun toNrTest() {
        Day03(lines = testInput).toNr(gridPoint2D(2, 0)) shouldBe 467
    }
}
