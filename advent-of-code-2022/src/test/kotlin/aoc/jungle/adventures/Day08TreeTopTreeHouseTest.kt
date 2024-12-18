package aoc.jungle.adventures

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day08TreeTopTreeHouseTest {

    private companion object {
        private val day08TreeTopTreeHouse by lazy { Day08TreeTopTreeHouse("input/day08.txt") }
        private val day08TreeTopTreeHouseDr by lazy { Day08TreeTopTreeHouse("input/day08-dr.txt") }
    }

    @Test
    fun `test part 1 test`() {
        Day08TreeTopTreeHouse("input/day08test.txt").part1() shouldBe 21
    }

    @Test
    fun `test part 1`() {
        day08TreeTopTreeHouse.part1() shouldBe 1_851
    }

    @Test
    fun `test part 1 dr`() {
        day08TreeTopTreeHouseDr.part1() shouldBe 1_794
    }


    @Test
    fun `test part 2`() {
        day08TreeTopTreeHouse.part2() shouldBe 574_080
    }

    @Test
    fun `test part 2 dr`() {
        day08TreeTopTreeHouseDr.part2() shouldBe 199_272
    }

}
