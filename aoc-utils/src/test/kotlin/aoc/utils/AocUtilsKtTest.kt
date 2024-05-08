package aoc.utils

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

internal class AocUtilsKtTest {

    @ParameterizedTest
    @ValueSource(strings = ["sdfsd", "hallo"])
    fun `test input contains no digits`(input: String) {
        input.containsNoDigits() shouldBe true
    }

    @ParameterizedTest
    @ValueSource(strings = ["sdfs2d", "hallo3", "HAns#4"])
    fun `test input contains digits`(input: String) {
        input.containsNoDigits() shouldBe false
    }

    @ParameterizedTest
    @ValueSource(strings = ["345", "2342342424234", "234234234", "3453"])
    fun `test input is natural nr`(input: String) {
        input.isNaturalNumber() shouldBe true
    }

    @ParameterizedTest
    @ValueSource(strings = ["sdfs2d", "hallo3", "HAns#4", "-345345", "345,345"])
    fun `test input is not a natural nr`(input: String) {
        input.isNaturalNumber() shouldBe false
    }

    @TestFactory
    fun wrapBackTo(): List<DynamicTest> {
        fun test(expected: List<Int>, to: Int, after: Int): DynamicTest {
            val ints = (0..20).map { it.wrapBack(to, after) }
            return dynamicTest("should equal '$expected', when wrapping back to $to after $after")
            { ints shouldBe expected }
        }
        return listOf(
            test(listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4), 2, 9),
            test(listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 1, 10),
            test(listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 7, 8, 9, 10), 7, 16)
        )
    }

    @ParameterizedTest(name = "range {0} sums to: {1}")
    @CsvSource(
        "1..10 -> 55",
        "1..100 -> 5050",
        "1..1000 -> 500500",
        delimiterString = " -> "
    )
    fun `test sum natural nrs`(intRangeToSum: String, expected: Int) {
        val (start, end) = intRangeToSum.split("..").map(String::toInt)

        sumNaturalNrs(start, end) shouldBe expected
    }

    @ParameterizedTest(name = "The least common multiple of {0} and {1} should be {2}")
    @CsvSource(
        "12, 36, 36",
        "13, 17, 221",
        "5, 7, 35",
        "6, 4, 12",
        "24, 36, 72"
    )
    fun testLeastCommonMultiple(nr1: Long, nr2: Long, expected: Long) {
        nr1.lcm(nr2) shouldBe expected
    }

    @Test
    fun leastCommonMultipleSample() {
        24 lcm 36 shouldBe 72
    }

    @Test
    fun greatestCommonDivisorSample() {
        12 gcd 32 shouldBe 4
    }
}
