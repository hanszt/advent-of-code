package aoc.jungle.adventures

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day06TuningTroubleTest {


    private val day06TuningTrouble = Day06TuningTrouble("input/day06.txt")

    @Test
    fun `test part 1`() {
        val part1 = day06TuningTrouble.part1()
        println(part1)
        assertEquals(1929, part1)
    }

    @Test
    fun `test part 1 test input`() {
        val part1 = Day06TuningTrouble("input/day06test.txt").part1()
        println(part1)
        assertEquals(7, part1)
    }

    @Test
    fun `test part 2`() {
        val part1 = day06TuningTrouble.part2()
        println(part1)
        assertEquals(3298, part1)
    }
}
