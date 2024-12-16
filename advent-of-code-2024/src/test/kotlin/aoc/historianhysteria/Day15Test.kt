package aoc.historianhysteria

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day15Test {

    private companion object {
        private val day15 by lazy { Day15(Path("input/day15.txt")) }
        private val day15dr by lazy { Day15(Path("input/day15-dr.txt")) }
        private val day15TestSmall = Day15("""
            ########
            #..O.O.#
            ##@.O..#
            #...O..#
            #.#.O..#
            #...O..#
            #......#
            ########

            <^^>>>vv<v>>v<<
        """.trimIndent())
    }

    @Test
    fun part1TestSmall() {
        day15TestSmall.part1() shouldBe 2028
    }

    @Test
    fun part1dr() {
        day15dr.part1() shouldBe 0
    }

    @Test
    fun part1() {
        day15.part1() shouldBe 0
    }

}