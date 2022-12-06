package aoc

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.toBigInteger as toBigInt

internal class Day06LanternFishTest {

    @Test
    fun `part 1 test input`() = assertEquals(5_934.toBigInt(), Day06LanternFish.part1("input/day6test.txt"))

    @Test
    fun `part 1 result`() = assertEquals(386_640.toBigInt(), Day06LanternFish.part1().also(::println))

    @Test
    fun `part 2 test input`() = assertEquals(26_984_457_539.toBigInt(), Day06LanternFish.part2("input/day6test.txt"))

    @Test
    fun `part 2 result`() = assertEquals(1_733_403_626_279.toBigInt(), Day06LanternFish.part2().also(::println))
}
