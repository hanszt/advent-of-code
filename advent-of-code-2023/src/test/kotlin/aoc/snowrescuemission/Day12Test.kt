package aoc.snowrescuemission

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled("Not implemented")
class Day12Test {

    @Test
    fun testPart1() {
        Day12("input/day12.txt").part1() shouldBe 0
    }

    @Test
    fun testinputPart2() {
        Day12(lines = """
            ???.### 1,1,3
            .??..??...?##. 1,1,3
            ?#?#?#?#?#?#?#? 1,3,1,6
            ????.#...#... 4,1,1
            ????.######..#####. 1,6,5
            ?###???????? 3,2,1
        """.trimIndent().lines()).part1() shouldBe 21
    }
}