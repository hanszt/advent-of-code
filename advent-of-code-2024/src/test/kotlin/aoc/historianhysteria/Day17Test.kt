package aoc.historianhysteria

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day17Test {

    private companion object {
        private val day17 by lazy { Day17(Path("input/day17.txt")) }
        private val day17Test by lazy {
            Day17(
                """
                    Register A: 729
                    Register B: 0
                    Register C: 0

                    Program: 0,1,5,4,3,0
                """.trimIndent().lines()
            )
        }
    }

    @Test
    fun part1Test() {
        day17Test.part1() shouldBe "4,6,3,5,6,3,5,2,1,0"
    }

    @Test
    fun part1() {
        day17.part1() shouldBe "1,5,3,0,2,5,2,5,3"
    }

    @Test
    fun part2() {
        day17.part2() shouldBe 108107566389757L
    }

}