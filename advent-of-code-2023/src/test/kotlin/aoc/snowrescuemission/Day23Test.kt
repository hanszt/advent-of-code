package aoc.snowrescuemission

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day23Test {

    private companion object {
        private val day23 by lazy { Day23(Path("input/day23.txt")) }
        private val day23Test by lazy {
            Day23(
                """
                #.#####################
                #.......#########...###
                #######.#########.#.###
                ###.....#.>.>.###.#.###
                ###v#####.#v#.###.#.###
                ###.>...#.#.#.....#...#
                ###v###.#.#.#########.#
                ###...#.#.#.......#...#
                #####.#.#.#######.#.###
                #.....#.#.#.......#...#
                #.#####.#.#.#########v#
                #.#...#...#...###...>.#
                #.#.#v#######v###.###v#
                #...#.>.#...>.>.#.###.#
                #####v#.#.###v#.#.###.#
                #.....#...#...#.#.#...#
                #.#########.###.#.#.###
                #...###...#...#...#.###
                ###.###.#.###v#####v###
                #...#...#.#.>.>.#.>.###
                #.###.###.#.###.#.#v###
                #.....###...###...#...#
                #####################.#
            """.trimIndent().lines()
            )
        }
    }

    @Test
    fun part1TestInput() {
        day23Test.part1() shouldBe 94
    }

    @Test
    fun part2TestInput() {
        day23Test.part2() shouldBe 154
    }

    @Test
    fun testPart1() {
        day23.part1() shouldBe 2366
    }

    @Test
    fun testPart2() {
        day23.part2() shouldBe 6682
    }

    @Nested
    inner class SolutionZebaluTests {

        @Test
        fun testPart1() {
            day23.part1Zebalu() shouldBe 2366
        }

        @Test
        fun testPart2() {
            day23.part2Zebalu() shouldBe 6682
        }
    }
}
