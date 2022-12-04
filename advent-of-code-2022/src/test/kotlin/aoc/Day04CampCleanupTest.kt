package aoc

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day04CampCleanupTest {
    private val day04CampCleanup = Day04CampCleanup("input/day04.txt")

    @Test
    fun `test part 1`() {
        val part1 = day04CampCleanup.part1()
        println(part1)
        assertEquals(459, part1)
    }

    @Test
    fun `test part 2`() {
        val part1 = day04CampCleanup.part2()
        println(part1)
        assertEquals(779, part1)
    }
}
