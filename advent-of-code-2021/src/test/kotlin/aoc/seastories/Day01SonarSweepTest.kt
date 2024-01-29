package aoc.seastories

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day01SonarSweepTest {

    private val day01SonarSweep = Day01SonarSweep("input/day1.txt")

    @Test
    fun testSumDepthIncreases(){
        day01SonarSweep.part1().also(::println) shouldBe 1_722
    }

    @Test
    fun testSumDepthRangeIncreases() {
        day01SonarSweep.part2().also(::println) shouldBe 1_748
    }

    @Test
    fun `part 2 v2 gives same answer`() {
        val filePath = "input/day1.txt"
        day01SonarSweep.part2V2(filePath) shouldBe day01SonarSweep.part2()
    }
}
