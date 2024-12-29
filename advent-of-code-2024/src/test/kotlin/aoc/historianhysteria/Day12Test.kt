package aoc.historianhysteria

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day12Test {

    private companion object {
        private val day12 by lazy { Day12(Path("input/day12.txt")) }
        private val day12testInput = Day12(
            """
                AAAA
                BBCD
                BBCC
                EEEC
            """.trimIndent().lines()
        )
        private val day12LargeTestInput = Day12(
            """
                RRRRIICCFF
                RRRRIICCCF
                VVRRRCCFFF
                VVRCCCJFFF
                VVVVCJJCFE
                VVIVCCJJEE
                VVIIICJJEE
                MIIIIIJJEE
                MIIISIJEEE
                MMMISSJEEE
            """.trimIndent().lines()
        )
    }

    @Test
    fun part1Test() {
        day12testInput.part1() shouldBe 140
    }

    @Test
    fun part1TestLarge() {
        day12LargeTestInput.part1() shouldBe 1930
    }

    @Test
    fun part1() {
        day12.part1() shouldBe 1533644L
    }

    @Test
    fun part2Test() {
        day12testInput.part2() shouldBe 80
    }

    @Test
    fun part2TestLarge() {
        day12LargeTestInput.part2() shouldBe 1206
    }

    @Test
    fun part2() {
        day12.part2() shouldBe 936718
    }

}