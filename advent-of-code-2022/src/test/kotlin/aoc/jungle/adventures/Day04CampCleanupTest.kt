package aoc.jungle.adventures

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day04CampCleanupTest {
    private val day04CampCleanup = Day04CampCleanup("input/day04.txt")

    @Test
    fun `test part 1`() {
        val result = day04CampCleanup.part1()
        println(result)
        assertEquals(459, result)
    }

    @Test
    fun `test part 2`() {
        val result = day04CampCleanup.part2()
        println(result)
        assertEquals(779, result)
    }
}
