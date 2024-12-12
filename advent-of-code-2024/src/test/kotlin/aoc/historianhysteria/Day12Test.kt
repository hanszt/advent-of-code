package aoc.historianhysteria

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day12Test {

    private companion object {
        private val day12 by lazy { Day12(Path("input/day12.txt")) }
        private val day12dr by lazy { Day12(Path("input/day12-dr.txt")) }
        private val day12testInput = Day12(
            """
                AAAA
                BBCD
                BBCC
                EEEC
            """.trimIndent().lines()
        )
    }

    @Test
    fun part1dr() {
        day12dr.part1() shouldBe 611
    }


}