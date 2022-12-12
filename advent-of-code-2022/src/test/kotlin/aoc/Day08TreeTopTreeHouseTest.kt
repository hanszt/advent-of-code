package aoc

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day08TreeTopTreeHouseTest {

    private val day08TreeTopTreeHouse = Day08TreeTopTreeHouse("input/day08.txt")


    @Test
    fun `test part 1`() {
        val part1 = day08TreeTopTreeHouse.part1()
        assertEquals(1_851, part1)
    }

    @Test
    fun `test part 1 test`() {
        val part1 = Day08TreeTopTreeHouse("input/day08test.txt").part1()
        assertEquals(21, part1)
    }

    @Test
    fun `test part 2`() {
        val part2 = day08TreeTopTreeHouse.part2()
        assertEquals(574_080, part2)
    }

}
