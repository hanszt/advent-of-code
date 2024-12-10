package aoc.jungle.adventures

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day24BlizzardBasinTest {

    private companion object {
        private val day24BlizzardBasin = Day24BlizzardBasin("input/day24.txt")
    }

    @Test
    fun `test part 1`() {
        day24BlizzardBasin.part1() shouldBe 230
    }

    @Test
    fun `test part 2`() {
        day24BlizzardBasin.part2() shouldBe 713
    }

}
