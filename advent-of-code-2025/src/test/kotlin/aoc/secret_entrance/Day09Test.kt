package aoc.secret_entrance

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day09Test {
    private val day09 = Day09(Path("input/day09.txt"))
    private val day09TestInput = Day09(
        """
            7,1
            11,1
            11,7
            9,7
            9,5
            2,5
            2,3
            7,3
        """.trimIndent().lines(),
    )

    @Test
    fun testPart1TestInput() {
        day09TestInput.part1() shouldBe 50
    }

    @Test
    fun testPart1() {
        day09.part1() shouldBe 4_767_418_746
    }

    @Test
    fun testPart2TestInput() {
        day09TestInput.part2() shouldBe 24
    }

    @Test
    fun testPart2() {
        day09.part2() shouldBe 1_461_987_144
    }
}