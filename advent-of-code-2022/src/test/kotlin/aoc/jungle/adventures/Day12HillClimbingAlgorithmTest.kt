package aoc.jungle.adventures

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day12HillClimbingAlgorithmTest {

    private val day12HillClimbingAlgorithm = Day12HillClimbingAlgorithm("input/day12.txt")

    @Test
    fun `test part 1`() {
        day12HillClimbingAlgorithm.part1() shouldBe 408
    }

    @Test
    fun `test part 1 test`() {
        Day12HillClimbingAlgorithm("input/day12test.txt").part1() shouldBe 31
    }

    @Test
    fun `test part 2`() {
        day12HillClimbingAlgorithm.part2() shouldBe 399
    }

    @Test
    fun `test part 2 test`() {
        Day12HillClimbingAlgorithm("input/day12test.txt").part2().also(::println) shouldBe 29
    }
}
