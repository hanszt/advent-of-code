package aoc

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day08Test {

    private val day08 = Day08("input/day08.txt")


    @Test
    fun `test part 1`() {
        val part1 = day08.part1()
        assertEquals(1851, part1)
    }

    @Test
    fun `test part 1 test`() {
        val part1 = Day08("input/day08test.txt").part1()
        assertEquals(21, part1)
    }

    @Test
    fun `test part 2`() {
        val part2 = day08.part2()
        assertEquals(574080, part2)
    }

}
