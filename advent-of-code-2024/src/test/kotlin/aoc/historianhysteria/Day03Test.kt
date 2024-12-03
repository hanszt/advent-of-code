package aoc.historianhysteria

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day03Test {

    @Test
    fun part1() {
        Day03("input/day03-dr.txt").part1() shouldBe 153_469_856
    }

    @Test
    fun part2() {
        Day03("input/day03-dr.txt").part2() shouldBe 77_055_967
    }

}