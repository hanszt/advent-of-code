package aoc.snowrescuemission

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day22Test {

    private companion object {
        private val day22 = Day22(Path("input/day22.txt"))
        private val day22TestInput = Day22("""
            1,0,1~1,2,1
            0,0,2~2,0,2
            0,2,3~2,2,3
            0,0,4~0,2,4
            2,0,5~2,2,5
            0,1,6~2,1,6
            1,1,8~1,1,9
        """.trimIndent().lines())
    }

    @Test
    fun part1TestInput() {
        day22TestInput.part1() shouldBe 5
    }

    @Test
    fun testPart1() {
        day22.part1() shouldBe 441
    }

    @Test
    fun testPart2() {
        day22.part2() shouldBe 80778L
    }
}
