package aoc.seastories

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day19BeaconScannerTest {

    private companion object {
        private val day19BeaconScannerTestInput = Day19BeaconScanner("input/day19test.txt")
        private val day19BeaconScanner = Day19BeaconScanner("input/day19.txt")
    }

    @Test
    fun `part 1 test input`() {
        day19BeaconScannerTestInput.part1() shouldBe 79
    }

    @Test
    fun `part 1 result`() {
        day19BeaconScanner.part1() shouldBe 376
    }

    @Test
    fun `part 2 test input`() {
        day19BeaconScannerTestInput.part2() shouldBe 3_621
    }

    @Test
    fun `part 2 result`() {
        day19BeaconScanner.part2() shouldBe 10_772
    }
}
