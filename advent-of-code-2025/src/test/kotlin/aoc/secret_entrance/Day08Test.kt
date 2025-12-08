package aoc.secret_entrance

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day08Test {

    private val day08 = Day08(Path("input/day08.txt"))
    private val day08TestInput = Day08(
        """
            162,817,812
            57,618,57
            906,360,560
            592,479,940
            352,342,300
            466,668,158
            542,29,236
            431,825,988
            739,650,466
            52,470,668
            216,146,977
            819,987,18
            117,168,530
            805,96,715
            346,949,466
            970,615,88
            941,993,340
            862,61,35
            984,92,344
            425,690,689
        """.trimIndent().lines(),
        10
    )

    @Test
    fun testPart1TestInput() {
        day08TestInput.part1() shouldBe 40
    }

    @Test
    fun testPart1() {
        day08.part1() shouldBe 102_816
    }

    @Test
    fun testPart2TestInput() {
        day08TestInput.part2() shouldBe 25272L
    }

    @Test
    fun testPart2() {
        day08.part2() shouldBe 100011612L
    }
}