package aoc.seastories

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.toBigInteger as toBigInt

internal class Day06LanternFishTest {

    private val day06LanternFish = Day06LanternFish("input/day6.txt")

    @Test
    fun `part 1 test input`() = assertEquals(5_934.toBigInt(), Day06LanternFish("input/day6test.txt").part1())

    @Test
    fun `part 1 result`() = assertEquals(386_640.toBigInt(), day06LanternFish.part1().also(::println))

    @Test
    fun `part 2 test input`() = assertEquals(26_984_457_539.toBigInt(), Day06LanternFish("input/day6test.txt").part2())

    @Test
    fun `part 2 result`() = assertEquals(1_733_403_626_279.toBigInt(), day06LanternFish.part2().also(::println))
}
