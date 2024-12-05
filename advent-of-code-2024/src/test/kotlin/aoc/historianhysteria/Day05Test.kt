package aoc.historianhysteria

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day05Test {


    private companion object {
        private val day05 by lazy { Day05(Path("input/day05.txt")) }
        private val day05dr by lazy { Day05(Path("input/day05-dr.txt")) }
        private val testInput = """
            
        """.trimIndent()
    }

    @Test
    fun part1TestInput() {
        Day05(testInput).part1() shouldBe 18
    }

    @Test
    fun part2TestInput() {
        Day05(testInput).part2() shouldBe 9
    }

    @Test
    fun part1dr() {
        day05dr.part1() shouldBe 2599
    }

    @Test
    fun part2dr() {
        day05dr.part2() shouldBe 1948
    }

    @Test
    fun part1() {
        val expected = 2662
        day05.part1() shouldBe expected
    }

    @Test
    fun part2() {
        val expected = 2034
        day05.part2() shouldBe expected
    }

}
