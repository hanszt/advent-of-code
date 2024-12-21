package aoc.historianhysteria

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day21Test {

    private companion object {
        private val day21 by lazy { Day21(Path("input/day21.txt")) }
        private val day21dr by lazy { Day21(Path("input/day21-dr.txt")) }
        private val day21Test by lazy {
            Day21(
                """""".trimIndent().lines()
            )
        }
    }

    @Test
    fun part1Test() {
        day21Test.part1() shouldBe 0
    }

    @Test
    fun part1dr() {
        day21dr.part1() shouldBe 0
    }

    @Test
    fun part1() {
        day21.part1() shouldBe 0
    }

}