package aoc

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day14RegolithReservoirTest {

    private val day14RegolithReservoir = Day14RegolithReservoir("input/day14.txt")
    @Test
    fun `test part 1 test`() {
        val result = Day14RegolithReservoir("input/day14test.txt").part1()
        assertEquals(24, result)
    }

    @Test
    fun `test part 1`() {
        val result = day14RegolithReservoir.part1()
        assertEquals(614, result)
    }

    @Test
    fun `test part 2 test`() {
        val result = Day14RegolithReservoir("input/day14test.txt").part2()
        assertEquals(93, result)
    }

    @Test
    fun `test part 2`() {
        val result = day14RegolithReservoir.part2()
        assertEquals(26_170, result)
    }
}
