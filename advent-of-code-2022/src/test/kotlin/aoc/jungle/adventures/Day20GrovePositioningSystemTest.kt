package aoc.jungle.adventures

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day20GrovePositioningSystemTest {

    private val day20GrovePositioningSystem = Day20GrovePositioningSystem("input/day20.txt")
    @Test
    fun `test part 1`() {
        val result = day20GrovePositioningSystem.part1()
        result shouldBe 2622
    }

    @Test
    fun `test part 1 test`() {
        val result = Day20GrovePositioningSystem("input/day20test.txt").part1()
        result shouldBe 3
    }

    @Test
    fun `test part 2`() {
        val result = day20GrovePositioningSystem.part2()
        result shouldBe 1_538_773_034_088
    }

    @Test
    fun `test part 2 test`() {
        val result = Day20GrovePositioningSystem("input/day20test.txt").part2()
        println(result)
        result shouldBe 1_623_178_306
    }
}
