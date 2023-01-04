package aoc.jungle.adventures

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day14RegolithReservoirTest {

    private val day14RegolithReservoir = Day14RegolithReservoir("input/day14.txt")

    @Test
    fun `test part 1 test`() = Day14RegolithReservoir("input/day14test.txt").part1() shouldBe 24

    @Test
    fun `test part 1`() = day14RegolithReservoir.part1() shouldBe 614

    @Test
    fun `test part 2 test`() = Day14RegolithReservoir("input/day14test.txt").part2() shouldBe 93

    @Test
    fun `test part 2`() = day14RegolithReservoir.part2() shouldBe 26_170
}
