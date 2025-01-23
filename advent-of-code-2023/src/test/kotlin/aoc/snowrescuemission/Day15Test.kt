package aoc.snowrescuemission

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day15Test {

    private companion object {
        private val day15 = Day15(Path("input/day15-dr.txt"))
    }

    @Test
    fun testPart1() {
        day15.part1() shouldBe 494980
    }

    @Test
    fun testPart2() {
        day15.part2() shouldBe 247933L
    }

}