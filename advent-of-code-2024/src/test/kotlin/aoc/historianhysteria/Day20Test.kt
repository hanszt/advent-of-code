package aoc.historianhysteria

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day20Test {

    private companion object {
        private val day20 by lazy { Day20(Path("input/day20.txt")) }
        private val day20dr by lazy { Day20(Path("input/day20-dr.txt")) }
        private val day20Test by lazy {
            Day20(
                """
                ###############
                #...#...#.....#
                #.#.#.#.#.###.#
                #S#...#.#.#...#
                #######.#.#.###
                #######.#.#...#
                #######.#.###.#
                ###..E#...#...#
                ###.#######.###
                #...###...#...#
                #.#####.#.###.#
                #.#...#.#.#...#
                #.#.#.#.#.#.###
                #...#...#...###
                ###############
            """.trimIndent().lines(),
                targetMinSaveTime = 4
            )
        }
    }

    @Nested
    inner class OwnSolutions {

        @Test
        fun part1Test() {
            day20Test.part1() shouldBe 30
        }

        @Test
        fun part1dr() {
            day20dr.part1() shouldBe 1372
        }

        @Test
        fun part1() {
            day20.part1() shouldBe 1289
        }
    }

    @Nested
    inner class ElizarovSolutions {

        @Test
        fun part1Elizarov() {
            day20.part1Elizarov() shouldBe 1289
        }

        @Test
        fun part1TestElizarov() {
            day20Test.part1Elizarov() shouldBe 30
        }

        @Test
        fun part1drElizarov() {
            day20dr.part1Elizarov() shouldBe 1372
        }

        @Test
        fun part2Elizarov() {
            day20.part2() shouldBe 982425
        }

        @Test
        fun part2rElizarov() {
            day20dr.part2() shouldBe 979014
        }
    }

}