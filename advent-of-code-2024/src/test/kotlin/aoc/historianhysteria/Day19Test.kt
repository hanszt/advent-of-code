package aoc.historianhysteria

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day19Test {

    private companion object {
        private val day19 by lazy { Day19(Path("input/day19.txt")) }
        private val day19dr by lazy { Day19(Path("input/day19-dr.txt")) }
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
        day19Test.part1() shouldBe 0
    }

    @Test
    fun part1dr() {
        day19dr.part1() shouldBe 0
    }

    @Test
    fun part1() {
        day19.part1() shouldBe 0
    }

}