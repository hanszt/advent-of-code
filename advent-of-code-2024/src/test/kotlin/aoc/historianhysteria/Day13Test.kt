package aoc.historianhysteria

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day13Test {

    private companion object {
        private val day13 by lazy { Day13(Path("input/day13.txt")) }
        private val day13dr by lazy { Day13(Path("input/day13-dr.txt")) }
    }

    @Test
    fun part1dr() {
        day13dr.part1() shouldBe 0
    }

    @Test
    fun part1() {
        day13.part1() shouldBe 0
    }

}