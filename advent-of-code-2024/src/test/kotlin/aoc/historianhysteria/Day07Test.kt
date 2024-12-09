package aoc.historianhysteria

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day07Test {

    private companion object {
        private val day07 by lazy { Day07(Path("input/day07.txt")) }
        private val day07dr by lazy { Day07(Path("input/day07-dr.txt")) }
        private val day07testInput = Day07("""
            190: 10 19
            3267: 81 40 27
            83: 17 5
            156: 15 6
            7290: 6 8 6 15
            161011: 16 10 13
            192: 17 8 14
            21037: 9 7 18 13
            292: 11 6 16 20
        """.trimIndent())
    }

    @Test
    fun part1TestInput() {
        day07testInput.part1() shouldBe 3749
    }

    @Test
    fun part2TestInput() {
        day07testInput.part2() shouldBe 11387
    }

    @Test
    fun part1dr() {
        day07dr.part1() shouldBe 975_671_981_569
    }

    @Test
    fun part2dr() {
        day07dr.part2() shouldBe 0
    }

    @Test
    fun part1() {
        day07.part1() shouldBe 42283209483350
    }

    @Test
    fun part2() {
        day07.part2() shouldBe 42283209483350
    }

}
