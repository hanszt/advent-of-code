package aoc.snowrescuemission

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day01Test {

    @Test
    fun `test part 1`() {
        Day01("input/day01.txt").part1() shouldBe 2_086_478
    }

    @Test
    fun `test part 1 v2`() {
        Day01("input/day01-drillster.txt").part1() shouldBe 2_904_518
    }


    @Test
    fun `test part 1 test`() {
        Day01(lines = """
            3   4
            4   3
            2   5
            1   3
            3   9
            3   3
        """.trimIndent().lines()).part1() shouldBe 11
    }

    @Test
    fun `test part 2`() {
        Day01("input/day01.txt").part2() shouldBe 24_941_624
    }

    @Test
    fun `test part 2 test`() {
        Day01(lines = """
            3   4
            4   3
            2   5
            1   3
            3   9
            3   3
        """.trimIndent().lines()).part2() shouldBe 31
    }

    @Test
    fun `test part 2 v2`() {
        Day01("input/day01-drillster.txt").part2() shouldBe 18_650_129
    }
}