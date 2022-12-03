package aoc

import aoc.Day21DiracDice.toStartingPositions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import aoc.utils.mapBoth
import java.io.File
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class Day21DiracDiceTest {

    @Test
    fun `part 1 test input`() = assertEquals(739_785, Day21DiracDice.part1("input/day21test.txt"))

    @Test
    fun `part 1 result`() = assertEquals(604_998, Day21DiracDice.part1().also(::println))

    @Test
    fun `part 2 test input`() = assertEquals(444_356_092_776_315, Day21DiracDice.part2("input/day21test.txt"))

    @Test
    fun `part 2 result`() = assertEquals(157_253_621_231_420, Day21DiracDice.part2().also(::println))

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
    fun `test player1 playing rounds`(round: Int) = assertTrue { Day21DiracDice.player1Playing(round) }

    @ParameterizedTest
    @ValueSource(ints = [3, 4, 5, 9, 10, 11, 15, 16, 17])
    fun `test player2 playing rounds`(round: Int) = assertFalse { Day21DiracDice.player1Playing(round) }
}
