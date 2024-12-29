package aoc.historianhysteria

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day13Test {

    private companion object {
        private val day13 by lazy { Day13(Path("input/day13.txt")) }
        private val day13Test by lazy {
            Day13(
                """
            Button A: X+94, Y+34
            Button B: X+22, Y+67
            Prize: X=8400, Y=5400

            Button A: X+26, Y+66
            Button B: X+67, Y+21
            Prize: X=12748, Y=12176

            Button A: X+17, Y+86
            Button B: X+84, Y+37
            Prize: X=7870, Y=6450

            Button A: X+69, Y+23
            Button B: X+27, Y+71
            Prize: X=18641, Y=10279
        """.trimIndent().lines()
            )
        }
    }

    @Test
    fun part1Test() {
        day13Test.part1() shouldBe 480
    }

    @Test
    fun part1() {
        day13.part1() shouldBe 25751L
    }

    @Test
    fun part2Test() {
        day13Test.part2() shouldBe 875318608908
    }

    @Test
    fun part2() {
        day13.part2() shouldBe 108528956728655
    }

}