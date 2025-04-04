package aoc.historianhysteria

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day09Test {

    private companion object {
        private val day09 by lazy { Day09(Path("input/day09.txt")) }
        private val day09dr by lazy { Day09(Path("input/day09-dr.txt")) }
        private val day09testInput = Day09("2333133121414131402")
    }

    @Test
    fun part1TestInput() {
        day09testInput.part1() shouldBe 1928
    }

    @Test
    fun part2TestInput() {
        day09testInput.part2() shouldBe 2858
    }

    @Test
    fun part1dr() {
        day09dr.part1() shouldBe 6_390_180_901_651 // 90994085674 too low, 6_390_444_967_413 too high
    }

    @Test
    fun part2dr() {
        day09dr.part2() shouldBe 6412390114238L
    }

    @Test
    fun part1() {
        day09.part1() shouldBe 6258319840548
    }

    @Test
    fun part2() {
        day09.part2() shouldBe 6286182965311L
    }

}
