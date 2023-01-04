package aoc.jungle.adventures

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day18BoilingBouldersTest {

    private val day18BoilingBoulders = Day18BoilingBoulders("input/day18.txt")
    @Test
    fun `test part 1`() {
        val result = day18BoilingBoulders.part1()
        println(result)
        assertEquals(4_370, result)
    }

    @Test
    fun `test part 1 test`() {
        val result = Day18BoilingBoulders("input/day18test.txt").part1()
        println(result)
        assertEquals(64, result)
    }

    @Test
    fun `test part 2`() {
        val result = day18BoilingBoulders.part2()
        println(result)
        assertEquals(2_458, result)
    }

    @Test
    fun `test part 2 test`() {
        val result = Day18BoilingBoulders("input/day18test.txt").part2()
        println(result)
        assertEquals(58, result)
    }
}
