package aoc.historianhysteria

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day11Test {

    private companion object {
        private val day11 by lazy { Day11("0 4 4979 24 4356119 914 85734 698829") }
        private val day11dr by lazy { Day11("0 5601550 3914 852 50706 68 6 645371") }
        private val day11testInput = Day11("125 17")
    }

    @Test
    fun part1TestInput() {
        day11testInput.part1() shouldBe 55_312
    }

    @Test
    fun part1TestInput6Blinks() {
        day11testInput.stoneCount(blinkAmount = 6) shouldBe 22
    }

    @Test
    fun part1dr() {
        day11dr.part1() shouldBe 189_092
    }

    @Test
    fun part2dr() {
        day11dr.part2() shouldBe 224_869_647_102_559
    }

    @Test
    fun part1() {
        day11.part1() shouldBe 188_902
    }

    @Test
    fun part2() {
        day11.part2() shouldBe 223_894_720_281_135
    }

}