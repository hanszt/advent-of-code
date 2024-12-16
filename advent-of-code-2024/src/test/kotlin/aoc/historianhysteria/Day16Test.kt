package aoc.historianhysteria

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day16Test {

    private companion object {
        private val day16 by lazy { Day16(Path("input/day16.txt")) }
        private val day16dr by lazy { Day16(Path("input/day16-dr.txt")) }
    }

    @Test
    fun part1dr() {
        day16dr.part1() shouldBe 0
    }

    @Test
    fun part1() {
        day16.part1() shouldBe 0
    }

}