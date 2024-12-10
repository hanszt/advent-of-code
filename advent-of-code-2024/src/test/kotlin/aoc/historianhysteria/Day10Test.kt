package aoc.historianhysteria

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day10Test {

    private companion object {
        private val day10 by lazy { Day10(Path("input/day10.txt")) }
        private val day10dr by lazy { Day10(Path("input/day10-dr.txt")) }
        private val day10testInput = Day10(
            """
                89010123
                78121874
                87430965
                96549874
                45678903
                32019012
                01329801
                10456732
            """.trimIndent().lines()
        )
    }

    @Test
    fun part1TestInput() {
        day10testInput.part1() shouldBe 36
    }

    @Test
    fun testInputP1FourPaths() {
        Day10(
            """
            ..90..9
            ...1.98
            ...2..7
            6543456
            765.987
            876....
            987....
        """.trimIndent().lines()
        ).part1() shouldBe 4
    }

    @Test
    fun part2TestInput() {
        day10testInput.part2() shouldBe 81
    }

    @Test
    fun `part 2 test input 3 paths`() {
        Day10(
            """
                .....0.
                ..4321.
                ..5..2.
                ..6543.
                ..7..4.
                ..8765.
                ..9....
            """.trimIndent().lines()
        ).part2() shouldBe 3
    }

    @Test
    fun part1dr() {
        day10dr.part1Elizarov() shouldBe 611
        day10dr.part1() shouldBe 611
    }

    @Test
    fun part2dr() {
        day10dr.part2() shouldBe 1380
    }

    @Test
    fun part1() {
        day10.part1() shouldBe 778
    }

    @Test
    fun part2() {
        day10.part2() shouldBe 1925
    }

}
