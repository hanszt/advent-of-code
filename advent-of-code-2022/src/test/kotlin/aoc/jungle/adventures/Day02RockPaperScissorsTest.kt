package aoc.jungle.adventures

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day02RockPaperScissorsTest {

    private val day02RockPaperScissors = Day02RockPaperScissors("input/day02.txt")

    @Test
    fun `test part 1 test`() = Day02RockPaperScissors("input/day02test.txt").part1() shouldBe 15

    @Test
    fun `test part 1`() = day02RockPaperScissors.part1() shouldBe 10994

    @Test
    fun `test part 2 test`() = Day02RockPaperScissors("input/day02test.txt").part2() shouldBe 12

    @Test
    fun `test part 2`() = day02RockPaperScissors.part2() shouldBe 12526
}
