package aoc.historianhysteria

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day21Test {

    private companion object {
        private val day21 by lazy {
            Day21(
                """
                    480A
                    143A
                    983A
                    382A
                    974A
                """.trimIndent().lines()
            )
        }
        private val day21dr by lazy {
            Day21(
                """
                    671A
                    083A
                    582A
                    638A
                    341A
                """.trimIndent().lines()
            )
        }
        private val day21Test by lazy {
            Day21(
                """
                    029A
                    980A
                    179A
                    456A
                    379A
                """.trimIndent().lines()
            )
        }
    }

    @Test
    fun part1Test() {
        day21Test.part1() shouldBe 126384
    }

    @Test
    fun part1dr() {
        day21dr.part1() shouldBe 163920
    }

    @Test
    fun part1() {
        day21.part1() shouldBe 206798
    }

    @Test
    fun part2dr() {
        day21dr.part2() shouldBe 204040805018350
    }

    @Test
    fun part2() {
        day21.part2() shouldBe 251508572750680
    }

}