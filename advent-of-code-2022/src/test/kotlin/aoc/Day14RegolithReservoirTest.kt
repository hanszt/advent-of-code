package aoc

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day14RegolithReservoirTest {

    private val day14RegolithReservoir = Day14RegolithReservoir("input/day14.txt")
    @Test
    fun `test part 1 test`() {
        val result = Day14RegolithReservoir("input/day14test.txt").part1()
        result shouldBe 24
    }

    @Test
    fun `test part 1`() {
        val result = day14RegolithReservoir.part1()
        result shouldBe 614
    }

    @Test
    fun `test part 2 test`() {
        val result = Day14RegolithReservoir("input/day14test.txt").part2()
        result shouldBe 93
    }

    @Test
    fun `test part 2`() {
        val result = day14RegolithReservoir.part2()
        result shouldBe 26_170
    }
}
