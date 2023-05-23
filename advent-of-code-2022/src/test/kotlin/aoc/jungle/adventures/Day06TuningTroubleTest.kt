package aoc.jungle.adventures

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day06TuningTroubleTest {


    private val day06TuningTrouble = Day06TuningTrouble("input/day06.txt")

    @Test
    fun `test part 1`() {
        val result = day06TuningTrouble.part1()
        println(result)
        assertEquals(1929, result)
    }

    @Test
    fun `test part 1 test input`() {
        val result = Day06TuningTrouble("input/day06test.txt").part1()
        println(result)
        assertEquals(7, result)
    }

    @Test
    fun `test part 2`() {
        val result = day06TuningTrouble.part2()
        println(result)
        assertEquals(3298, result)
    }
}
