package aoc.seastories

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Timeout
import java.util.concurrent.TimeUnit

internal class Day22ReactorRebootTest {

    @Test
    fun `part 1 test input`() = Day22ReactorReboot.part1("input/day22test.txt") shouldBe 590_784

    @Test
    fun `part 1 result`() = Day22ReactorReboot.part1().also(::println) shouldBe 623_748

    @Test
    fun `part 2 test input`() = Day22ReactorReboot.part2("input/day22test-2.txt") shouldBe 2_758_514_936_282_235

    @Test
    @Timeout(value = 6_000, unit = TimeUnit.MILLISECONDS)
    fun `part 2 result`() = Day22ReactorReboot.part2().also(::println) shouldBe 1_227_345_351_869_476
}
