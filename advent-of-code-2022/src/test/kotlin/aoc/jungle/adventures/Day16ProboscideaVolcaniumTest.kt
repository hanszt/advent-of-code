package aoc.jungle.adventures

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day16ProboscideaVolcaniumTest {

    private val day16ProboscideaVolcanium = Day16ProboscideaVolcanium("input/day16.txt")

    @Test
    fun `test part 1`() {
        val result = day16ProboscideaVolcanium.part1()
        result shouldBe 2_320
    }

    @Test
    fun `test part 1 test`() {
        val result = Day16ProboscideaVolcanium("input/day16test.txt").part1()
        result shouldBe 1_651
    }

    @Test
    fun `test part 2`() {
        val result = day16ProboscideaVolcanium.part2()
        result shouldBe 2_967
    }

    @ParameterizedTest(name = "After {0} minutes, the max pressure released should be: {1}")
    @CsvSource(value = ["5, 105", "26, 1_707", "27, 1_788", "28, 1_869", "29, 1_950", "30 , 2_031", "60, 4_461"])
    fun `test part 2 test`(minute: Int, expected: Int) {
        val result = Day16ProboscideaVolcanium("input/day16test.txt").part2(minute)
        result shouldBe expected
    }
}
