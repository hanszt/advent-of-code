package aoc.seastories

import io.kotest.matchers.shouldBe
import java.util.concurrent.TimeUnit
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Timeout

internal class Day22ReactorRebootTest {

    private val day22ReactorReboot = Day22ReactorReboot("input/day22.txt")

    private val day22ReactorRebootTestInput = Day22ReactorReboot("input/day22test.txt")

    @Test
    fun `part 1 test input`() {
        day22ReactorRebootTestInput.part1() shouldBe 590_784
    }

    @Test
    fun `part 1 result`() {
        day22ReactorReboot.part1().also(::println) shouldBe 623_748
    }

    @Test
    fun `part 2 test input`() {
        Day22ReactorReboot("input/day22test-2.txt").part2() shouldBe 2_758_514_936_282_235
    }

    @Test
    @Timeout(value = 6_000, unit = TimeUnit.MILLISECONDS)
    fun `part 2 result`() {
        day22ReactorReboot.part2().also(::println) shouldBe 1_227_345_351_869_476
    }
}
