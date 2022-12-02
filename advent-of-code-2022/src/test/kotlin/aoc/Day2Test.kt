package aoc

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day2Test {

    private val day02RockPaperScissors = Day02RockPaperScissors("input/day2.txt")

    @Test
    fun `test part 1 test`() {
        val score = Day02RockPaperScissors("input/day2test.txt").part1()
        assertEquals(15, score)
    }


    @Test
    fun `test part 1`() {
        val score = day02RockPaperScissors.part1()
        assertEquals(10994, score)
    }

    @Test
    fun `test part 2 test`() {
        val score = Day02RockPaperScissors("input/day2test.txt").part2()
        assertEquals(12, score)
    }


    @Test
    fun `test part 2`() {
        val score = day02RockPaperScissors.part2()
        assertEquals(12526, score)
    }

}
