package aoc.seastories

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day01SonarSweepTest {

    @Test
    fun testSumDepthIncreases() = Day01SonarSweep.part1().also(::println) shouldBe 1_722

    @Test
    fun testSumDepthRangeIncreases() = Day01SonarSweep.part2().also(::println) shouldBe 1_748

    @Test
    fun `part 2 v2 gives same answer`() {
        val filePath = "input/day1.txt"
        Day01SonarSweep.part2V2(filePath) shouldBe Day01SonarSweep.part2(filePath)
    }
}
