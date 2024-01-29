package aoc.seastories

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day19BeaconScannerTest {

    private companion object {
        private val day19BeaconScannerTestInput = Day19BeaconScanner("input/day19test.txt")
        private val day19BeaconScanner = Day19BeaconScanner("input/day19.txt")
    }

    @Test
    fun `part 1 test input`() = assertEquals(79, day19BeaconScannerTestInput.part1())

    @Test
    fun `part 1 result`() = assertEquals(376, day19BeaconScanner.part1().also(::println))

    @Test
    fun `part 2 test input`() = assertEquals(3_621, day19BeaconScannerTestInput.part2())

    @Test
    fun `part 2 result`() = assertEquals(10_772, day19BeaconScanner.part2().also(::println))
}
