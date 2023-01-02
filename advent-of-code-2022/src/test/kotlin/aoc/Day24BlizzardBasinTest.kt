package aoc

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day24BlizzardBasinTest {

    private val day24BlizzardBasin = Day24BlizzardBasin("input/day24.txt")
    @Test
    fun `test part 1`() = day24BlizzardBasin.part1().also(::println) shouldBe 230

    @Test
    fun `test part 2`() = day24BlizzardBasin.part2().also(::println) shouldBe 713

}
