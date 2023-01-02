package aoc

import aoc.Day10CathodeRayTube.Companion.toExpectedTextOrElseThrow
import aoc.utils.BRIGHT_BLUE
import aoc.utils.withColor
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class Day10CathodeRayTubeTest {

    @Test
    fun `test part 1`() = Day10CathodeRayTube("input/day10.txt").part1() shouldBe 13_180

    @Test
    fun `test part 1 test`() = Day10CathodeRayTube("input/day10test2.txt").part1() shouldBe 13_140

    @Test
    fun `test part 2`() {
        val part2 = Day10CathodeRayTube("input/day10.txt").part2AsGrid()
        println(part2.withColor(BRIGHT_BLUE))
        part2.toExpectedTextOrElseThrow() shouldBe "EZFCHJAB"
    }
}
