package aoc.historianhysteria

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day22Test {

    private companion object {
        private val day22 by lazy { Day22(Path("input/day22.txt")) }
        private val day22Test by lazy {
            Day22(
                """
                    1
                    10
                    100
                    2024
                """.trimIndent().lines()
            )
        }
    }

    @Test
    fun part1Test() {
        day22Test.part1() shouldBe 37327623
    }

    @Test
    fun secretNrs() {
        Day22.secretNrs(123).drop(1).take(10).toList() shouldBe listOf(
            15887950,
            16495136,
            527345,
            704524,
            1553684,
            12683156,
            11100544,
            12249484,
            7753432,
            5908254,
        )
    }

    @Test
    fun part1() {
        day22.part1() shouldBe 14691757043L
    }

    @Test
    fun part2() {
        day22.part2() shouldBe 1831
    }

}