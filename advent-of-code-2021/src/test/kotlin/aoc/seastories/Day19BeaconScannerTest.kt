package aoc.seastories

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day19BeaconScannerTest {

    @Test
    fun `part 1 test input`() = assertEquals(79, Day19BeaconScanner.part1("input/day19test.txt"))

    @Test
    fun `part 1 result`() = assertEquals(376, Day19BeaconScanner.part1().also(::println))

    @Test
    fun `part 2 test input`() = assertEquals(3_621, Day19BeaconScanner.part2("input/day19test.txt"))

    @Test
    fun `part 2 result`() = assertEquals(10_772, Day19BeaconScanner.part2().also(::println))
}
