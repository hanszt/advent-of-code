package aoc

import aoc.Day10CathodeRayTube.Companion.toExpectedTextOrElseThrow
import aoc.utils.BRIGHT_BLUE
import aoc.utils.withColor
import kotlin.test.Test
import kotlin.test.assertEquals

class Day10CathodeRayTubeTest {

    private val day10CathodeRayTube = Day10CathodeRayTube("input/day10.txt")

    @Test
    fun `test part 1 test`() {
        val part1 = Day10CathodeRayTube("input/day10test2.txt").part1()
        assertEquals(13_140, part1)
    }
    @Test
    fun `test part 1`() {
        val part1 = day10CathodeRayTube.part1()
        assertEquals(13_180, part1)
    }

    @Test
    fun `test part 2`() {
        val part2 = day10CathodeRayTube.part2AsGrid()
        println(part2.withColor(BRIGHT_BLUE))
        assertEquals("EZFCHJAB", part2.toExpectedTextOrElseThrow())
    }
}
