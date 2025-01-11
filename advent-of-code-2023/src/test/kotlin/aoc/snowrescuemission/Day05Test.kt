package aoc.snowrescuemission

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day05Test {

    private companion object {
        private val day05 = Day05(Path("input/day05.txt"))
        private val day05dr = Day05(Path("input/day05-dr.txt"))
        private val day05Test = Day05(
            """
                seeds: 79 14 55 13

                seed-to-soil map:
                50 98 2
                52 50 48

                soil-to-fertilizer map:
                0 15 37
                37 52 2
                39 0 15

                fertilizer-to-water map:
                49 53 8
                0 11 42
                42 0 7
                57 7 4

                water-to-light map:
                88 18 7
                18 25 70

                light-to-temperature map:
                45 77 23
                81 45 19
                68 64 13

                temperature-to-humidity map:
                0 69 1
                1 0 69

                humidity-to-location map:
                60 56 37
                56 93 4
            """.trimIndent()
        )
    }


    @Test
    fun testPart1() {
        day05dr.part1() shouldBe 165_788_812
    }

    @Test
    fun testPart2Test() {
        day05Test.part2() shouldBe 46
    }

    @Test
    fun testPart2() {
        day05dr.part2() shouldBe 1_928_058
    }


    @Test
    fun testPart2HztInput() {
        day05.part1() shouldBe 218513636
    }
}