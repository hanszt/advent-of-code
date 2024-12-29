package aoc.historianhysteria

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day19Test {

    private companion object {
        private val day19 by lazy { Day19(Path("input/day19.txt")) }
        private val day19Test by lazy {
            Day19(
                """
                r, wr, b, g, bwu, rb, gb, br

                brwrr
                bggr
                gbbr
                rrbgbr
                ubwu
                bwurrg
                brgr
                bbrgwb
            """.trimIndent()
            )
        }

    }

    @Test
    fun part1Test() {
        day19Test.part1() shouldBe 6
    }

    @Test
    fun part1() {
        day19.part1() shouldBe 350
    }

    @Test
    fun part2Test() {
        day19Test.part2() shouldBe 16
    }

    @Test
    fun part2() {
        day19.part2() shouldBe 769668867512623
    }

}