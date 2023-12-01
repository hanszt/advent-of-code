package aoc.jungle.adventures

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day08TreeTopTreeHouseTest {

    private val day08TreeTopTreeHouse = Day08TreeTopTreeHouse("input/day08.txt")

    @Test
    fun `test part 1 test`() {
        Day08TreeTopTreeHouse("input/day08test.txt").part1() shouldBe 21
    }

    @Test
    fun `test part 1`() {
        day08TreeTopTreeHouse.part1() shouldBe 1_851
    }

    @Test
    fun `test part 2`() {
        day08TreeTopTreeHouse.part2() shouldBe 574_080
    }
}
