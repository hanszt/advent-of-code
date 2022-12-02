package aoc

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Timeout
import java.util.concurrent.TimeUnit

internal class Day22ReactorRebootTest {

    @Test
    fun `part 1 test input`() = assertEquals(590_784, Day22ReactorReboot.part1("input/day22test.txt"))

    @Test
    fun `part 1 result`() = assertEquals(623_748, Day22ReactorReboot.part1().also(::println))

    @Test
    fun `part 2 test input`() = assertEquals(2_758_514_936_282_235, Day22ReactorReboot.part2("input/day22test-2.txt"))

    @Test
    @Timeout(value = 6_000, unit = TimeUnit.MILLISECONDS)
    fun `part 2 result`() = assertEquals(1_227_345_351_869_476, Day22ReactorReboot.part2().also(::println))
}
