package aoc.historianhysteria

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day22Test {

    private companion object {
        private val day22 by lazy { Day22(Path("input/day22.txt")) }
        private val day22dr by lazy { Day22(Path("input/day22-dr.txt")) }
        private val day22Test by lazy {
            Day22(
                """""".trimIndent().lines()
            )
        }
    }

    @Test
    fun part1Test() {
        day22Test.part1() shouldBe 0
    }

    @Test
    fun part1dr() {
        day22dr.part1() shouldBe 0
    }

    @Test
    fun part1() {
        day22.part1() shouldBe 0
    }

}