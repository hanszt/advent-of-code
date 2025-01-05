package aoc.historianhysteria

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day25Test {

    companion object {
        private val day25TestInput = Day25("""
            #####
            .####
            .####
            .####
            .#.#.
            .#...
            .....

            #####
            ##.##
            .#.##
            ...##
            ...#.
            ...#.
            .....

            .....
            #....
            #....
            #...#
            #.#.#
            #.###
            #####

            .....
            .....
            #.#..
            ###..
            ###.#
            ###.#
            #####

            .....
            .....
            .....
            #....
            #.#..
            #.#.#
            #####
        """.trimIndent().lines())
        private val day25 by lazy { Day25(Path("input/day25.txt")) }
    }

    @Test
    fun part1TestInput() {
        day25TestInput.part1() shouldBe 3
    }

    @Test
    fun testPart1() {
        day25.part1() shouldBe 3395
    }


}