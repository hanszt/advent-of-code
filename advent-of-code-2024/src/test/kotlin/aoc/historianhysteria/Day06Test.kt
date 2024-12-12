package aoc.historianhysteria

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day06Test {


    private companion object {
        private val day06 by lazy { Day06(Path("input/day06.txt")) }
        private val day06dr by lazy { Day06(Path("input/day06-dr.txt")) }
        private val day06testInput = Day06("""
            ....#.....
            .........#
            ..........
            ..#.......
            .......#..
            ..........
            .#..^.....
            ........#.
            #.........
            ......#...
        """.trimIndent())
    }

    @Test
    fun part1TestInput() {
        day06testInput.part1() shouldBe 41
    }

    @Test
    fun part2TestInput() {
        day06testInput.part2() shouldBe 6
    }

    @Test
    fun part1dr() {
        day06dr.part1() shouldBe 5551
    }

    @Test
    fun part2dr() {
        day06dr.part2() shouldBe 1939 // 1778 too low
    }

    @Test
    fun part1() {
        day06.part1() shouldBe 4656
    }

    @Test
    fun part2() {
        day06.part2() shouldBe 0 // 1455 too low
    }

}
