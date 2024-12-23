package aoc.snowrescuemission

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day12Test {

    @Test
    fun testPart1() {
        Day12("input/day12.txt").part1() shouldBe 7195
    }

    @Test
    fun testPart1dr() {
        Day12("input/day12dr.txt").part1() shouldBe 7090
    }

    @Test
    fun testPart2() {
        Day12("input/day12.txt").part2() shouldBe 33992866292225
    }

    @Test
    fun testPart2dr() {
        Day12("input/day12dr.txt").part2() shouldBe 6792010726878
    }

    @Test
    fun testinputPart2() {
        Day12(
            lines = """
            ???.### 1,1,3
            .??..??...?##. 1,1,3
            ?#?#?#?#?#?#?#? 1,3,1,6
            ????.#...#... 4,1,1
            ????.######..#####. 1,6,5
            ?###???????? 3,2,1
        """.trimIndent().lines()
        ).part1() shouldBe 21
    }
}