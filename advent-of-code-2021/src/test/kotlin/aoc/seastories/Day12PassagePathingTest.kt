package aoc.seastories

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day12PassagePathingTest {

    private val day12PassagePathingTestInput = Day12PassagePathing("input/day12test.txt")
    private val day12PassagePathingMediumInput = Day12PassagePathing("input/day12test-medium.txt")
    private val day12PassagePathing = Day12PassagePathing("input/day12.txt")

    @Test
    fun `part 1 test input small`() {
        day12PassagePathingTestInput.part1() shouldBe 10
    }

    @Test
    fun `part 1 test input medium`() {
        day12PassagePathingMediumInput.part1() shouldBe 19
    }

    @Test
    fun `part 1 result`() {
        day12PassagePathing.part1().also(::println) shouldBe 3_298
    }

    @Test
    fun `part 2 test input small`() {
        day12PassagePathingTestInput.part2() shouldBe 36
    }

    @Test
    fun `part 2 test input medium`() {
        day12PassagePathingMediumInput.part2() shouldBe 103
    }

    @Test
    fun `part 2 result`() {
        day12PassagePathing.part2().also(::println) shouldBe 93_572
    }
}
