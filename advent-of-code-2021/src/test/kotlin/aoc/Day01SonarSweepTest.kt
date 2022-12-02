package aoc

import org.junit.jupiter.api.Test

internal class Day01SonarSweepTest {

    @Test
    fun testSumDepthIncreases() = Day01SonarSweep.part1().also(::println).assertEqualTo(1_722)

    @Test
    fun testSumDepthRangeIncreases() = Day01SonarSweep.part2().also(::println).assertEqualTo(1_748)

    @Test
    fun `part 2 v2 gives same answer`() {
        val filePath = "input/day1.txt"
        Day01SonarSweep.part2(filePath).assertEqualTo(Day01SonarSweep.part2V2(filePath))
    }
}
