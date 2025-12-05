package aoc.secret_entrance

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.io.path.Path

class Day05Test {

    private companion object {
        private val day05 = Day05(Path("input/day05.txt"))
        private val day05TestInput = Day05(
            """
                3-5
                10-14
                16-20
                12-18

                1
                5
                8
                11
                17
                32
            """.trimIndent()
        )
    }

    @Test
    fun testPart1TestInput() {
        day05TestInput.part1() shouldBe 3
    }

    @Test
    fun testPart1() {
        day05.part1() shouldBe 513
    }

    @Test
    fun testPart2TestInput() {
        day05TestInput.part2() shouldBe 14
    }

    @Test
    fun testPart2() {
        day05.part2() shouldBe 339668510830757L
    }

    @TestFactory
    fun testMergeRanges(): List<DynamicTest> {
        infix fun List<LongRange>.shouldBeMergedTo(expected: List<LongRange>) =
            dynamicTest("$this should be merged to $expected") {
                Day05.mergeRanges(this) shouldBe expected
            }

        // Long.MIN_VALUE..Long.MAX_VALUE does lead to an out-of-memory error somehow. 🤔
        // It does go wrong on end + 1 in the tested function
        val minMaxValueRange = Long.MIN_VALUE..<Long.MAX_VALUE
        return listOf(
            emptyList<LongRange>() shouldBeMergedTo emptyList(),
            listOf(1L..5, 2L..6) shouldBeMergedTo listOf(1L..6),
            listOf(2L..5, 6L..10) shouldBeMergedTo listOf(2L..10),
            listOf(2L..5, 7L..10) shouldBeMergedTo listOf(2L..5, 7L..10),
            listOf(2L..5, 7L..10, 9L..12) shouldBeMergedTo listOf(2L..5, 7L..12),
            listOf(2L..5, 7L..10, minMaxValueRange) shouldBeMergedTo listOf(minMaxValueRange),
        )
    }
}
