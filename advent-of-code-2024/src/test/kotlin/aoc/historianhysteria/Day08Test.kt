package aoc.historianhysteria

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day08Test {

    private companion object {
        private val day08 by lazy { Day08(Path("input/day08.txt")) }
        private val day08dr by lazy { Day08(Path("input/day08-dr.txt")) }
        private val day08testInput = Day08("""
            ............
            ........0...
            .....0......
            .......0....
            ....0.......
            ......A.....
            ............
            ............
            ........A...
            .........A..
            ............
            ............
        """.trimIndent())
    }

    @Test
    fun part1TestInput() {
        day08testInput.part1() shouldBe 3749
    }

    @Test
    fun part2TestInput() {
        day08testInput.part2() shouldBe 11387
    }

    @Test
    fun part1dr() {
        day08dr.part1() shouldBe 975_671_981_569
    }

    @Test
    fun part2dr() {
        day08dr.part2() shouldBe 0
    }

    @Test
    fun part1() {
        day08.part1() shouldBe 42283209483350
    }

    @Test
    fun part2() {
        day08.part2() shouldBe 42283209483350
    }

}
