package aoc.historianhysteria

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day17Test {

    private companion object {
        private val day17 by lazy { Day17(Path("input/day17.txt")) }
        private val day17dr by lazy { Day17(Path("input/day17-dr.txt")) }
    }

    @Test
    fun part1dr() {
        day17dr.part1() shouldBe 0
    }

    @Test
    fun part1() {
        day17.part1() shouldBe 0
    }

}