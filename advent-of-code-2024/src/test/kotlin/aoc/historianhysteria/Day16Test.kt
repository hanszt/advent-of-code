package aoc.historianhysteria

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day16Test {

    private companion object {
        private val day16 by lazy { Day16(Path("input/day16.txt")) }
        private val day16dr by lazy { Day16(Path("input/day16-dr.txt")) }
        private val day16Test by lazy {
            Day16(
                """
                ###############
                #.......#....E#
                #.#.###.#.###.#
                #.....#.#...#.#
                #.###.#####.#.#
                #.#.#.......#.#
                #.#.#####.###.#
                #...........#.#
                ###.#.#####.#.#
                #...#.....#.#.#
                #.#.#.###.#.#.#
                #.....#...#.#.#
                #.###.#.#.#.#.#
                #S..#.....#...#
                ###############
            """.trimIndent().lines()
            )
        }
    }

    @Test
    fun part1Test() {
        day16Test.part1() shouldBe 7036
    }

    @Test
    fun part1dr() {
        day16dr.part1() shouldBe 135512
    }

    @Test
    fun part1() {
        day16.part1() shouldBe 75416
    }

    @Test
    fun part2Test() {
        day16Test.part2() shouldBe 45
    }

    @Test
    fun part2dr() {
        day16dr.part2() shouldBe 541
    }

    @Test
    fun part2() {
        day16.part2() shouldBe 476
    }

}