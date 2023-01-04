package aoc.jungle.adventures

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals

internal class Day16ProboscideaVolcaniumTest {

    private val day16ProboscideaVolcanium = Day16ProboscideaVolcanium("input/day16.txt")

    @Test
    fun `test part 1`() {
        val result = day16ProboscideaVolcanium.part1()
        println(result)
        assertEquals(2_320, result)
    }

    @Test
    fun `test part 1 test`() {
        val result = Day16ProboscideaVolcanium("input/day16test.txt").part1()
        println(result)
        assertEquals(1_651, result)
    }

    @Test
    fun `test part 2`() {
        val result = day16ProboscideaVolcanium.part2()
        println(result)
        assertEquals(2_967, result)
    }

    @ParameterizedTest(name = "After {0} minutes, the max pressure released should be: {1}")
    @CsvSource(value = ["5, 105", "26, 1_707", "27, 1_788", "28, 1_869", "29, 1_950", "30 , 2_031", "60, 4_461"])
    fun `test part 2 test`(minute: Int, expected: Int) {
        val result = Day16ProboscideaVolcanium("input/day16test.txt").part2(minute)
        println(result)
        assertEquals(expected, result)
    }
}
