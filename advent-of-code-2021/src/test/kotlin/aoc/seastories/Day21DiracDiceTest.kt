package aoc.seastories

import aoc.seastories.Day21DiracDice.toStartingPositions
import aoc.utils.mapBoth
import io.kotest.matchers.shouldBe
import java.io.File
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class Day21DiracDiceTest {

    @Test
    fun `part 1 test input`() {
        Day21DiracDice.part1("input/day21test.txt") shouldBe 739_785
    }

    @Test
    fun `part 1 result`() {
        Day21DiracDice.part1() shouldBe 604_998
    }

    @Test
    fun `part 2 test input`() {
        Day21DiracDice.part2("input/day21test.txt") shouldBe 444_356_092_776_315
    }

    @Test
    fun `part 2 result`() {
        Day21DiracDice.part2() shouldBe 157_253_621_231_420
    }

    @Test
    fun `my method for part 2 gives wrong answer and is slow`() {
        val (player1Pos, player2Pos) = File("input/day21test.txt").toStartingPositions()
        val (player1, player2) = (player1Pos to player2Pos).mapBoth { Day21DiracDice.Player(score = 0, position = it) }

        val winningThreshold = 8
        val expectedMaxWinningCount = Day21DiracDice.findMaxWinningCount(player1Pos, player2Pos, winningThreshold)
        val maxWinningCount = Day21DiracDice.findMaxWinningCount(player1, player2, winningThreshold)

        println("expectedMaxWinningCount = $expectedMaxWinningCount")
        println("maxWinningCount = $maxWinningCount")

        assertNotEquals(expectedMaxWinningCount, maxWinningCount)
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1, 2, 6, 7, 8, 12, 13, 14])
    fun `test player1 playing rounds`(round: Int) {
        Day21DiracDice.player1Playing(round) shouldBe true
    }

    @ParameterizedTest
    @ValueSource(ints = [3, 4, 5, 9, 10, 11, 15, 16, 17])
    fun `test player2 playing rounds`(round: Int) {
        Day21DiracDice.player1Playing(round) shouldBe false
    }
}
