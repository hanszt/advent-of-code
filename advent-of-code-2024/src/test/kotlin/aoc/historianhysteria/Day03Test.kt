package aoc.historianhysteria

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day03Test {

    private companion object {
        private val day03 = Day03("input/day03.txt")
        private val day03Java = Day03Java("input/day03.txt")
        private val day03dr = Day03("input/day03-dr.txt")
    }

    @Test
    fun part1dr() {
        day03dr.part1() shouldBe 153_469_856
    }

    @Test
    fun part2dr() {
        day03dr.part2() shouldBe 77_055_967
    }

    @Test
    fun part1() {
        val expected = 161_085_926
        day03Java.part1() shouldBe expected
        day03.part1() shouldBe expected
    }

    @Test
    fun part2() {
        val expected = 82_045_421
        day03Java.part2() shouldBe expected
        day03.part2() shouldBe expected
    }

}