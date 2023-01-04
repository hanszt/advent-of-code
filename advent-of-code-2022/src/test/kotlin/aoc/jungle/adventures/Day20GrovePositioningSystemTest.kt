package aoc.jungle.adventures

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day20GrovePositioningSystemTest {

    private val day20GrovePositioningSystem = Day20GrovePositioningSystem("input/day20.txt")
    @Test
    fun `test part 1`() {
        val result = day20GrovePositioningSystem.part1()
        println(result)
        assertEquals(2622, result)
    }

    @Test
    fun `test part 1 test`() {
        val result = Day20GrovePositioningSystem("input/day20test.txt").part1()
        println(result)
        assertEquals(3, result)
    }

    @Test
    fun `test part 2`() {
        val result = day20GrovePositioningSystem.part2()
        println(result)
        assertEquals(1538773034088, result)
    }

    @Test
    fun `test part 2 test`() {
        val result = Day20GrovePositioningSystem("input/day20test.txt").part2()
        println(result)
        assertEquals(1623178306, result)
    }
}
