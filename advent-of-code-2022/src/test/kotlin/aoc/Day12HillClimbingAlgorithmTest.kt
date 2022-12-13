package aoc

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day12HillClimbingAlgorithmTest {

    private val day12HillClimbingAlgorithm = Day12HillClimbingAlgorithm("input/day12.txt")
    @Test
    fun `test part 1`() {
        val result = day12HillClimbingAlgorithm.part1()
        println(result)
        assertEquals(408, result)
    }

    @Test
    fun `test part 1 test`() {
        val result = Day12HillClimbingAlgorithm("input/day12test.txt").part1()
        println(result)
        assertEquals(31, result)
    }

    @Test
    fun `test part 2`() {
        val result = day12HillClimbingAlgorithm.part2()
        println(result)
        assertEquals(399, result)
    }

    @Test
    fun `test part 2 test`() {
        val result = Day12HillClimbingAlgorithm("input/day12test.txt").part2()
        println(result)
        assertEquals(29, result)
    }
}
