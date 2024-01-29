package aoc.seastories

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class Day21DiracDiceTest {

    private val day21DiracDice = Day21DiracDice("input/day21.txt")
    private val day21DiracDiceTestInput = Day21DiracDice("input/day21test.txt")

    @Test
    fun `part 1 test input`() {
        day21DiracDiceTestInput.part1() shouldBe 739_785
    }

    @Test
    fun `part 1 result`() {
        day21DiracDice.part1() shouldBe 604_998
    }

    @Test
    fun `part 2 test input`() {
        day21DiracDiceTestInput.part2() shouldBe 444_356_092_776_315
    }

    @Test
    fun `part 2 result`() {
        day21DiracDice.part2() shouldBe 157_253_621_231_420
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
