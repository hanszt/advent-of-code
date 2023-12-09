package aoc.snowrescuemission

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day09Test {

    companion object {
        private val day09 = Day09("input/day09.txt")
        private val day09test = Day09("input/day09test.txt")
    }

    @Test
    fun `test part1`() {
        day09.part1() shouldBe 1992273652
    }

    @Test
    fun `test part1 test`() {
        day09test.part1() shouldBe 114
    }

    @Test
    fun `test part2`() {
        day09.part2() shouldBe 1012
    }

    @Test
    fun `test part2 test`() {
        day09test.part2() shouldBe 2
    }
}