package aoc.seastories

import io.kotest.matchers.collections.shouldBeEmpty
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

internal class Day10SyntaxScoringTest {

    private val day10SyntaxScoring = Day10SyntaxScoring("input/day10.txt")
    private val day10SyntaxScoringTestInput = Day10SyntaxScoring("input/day10test.txt")

    @Test
    fun `part 1 test input`() = assertEquals(26_397, day10SyntaxScoringTestInput.part1())

    @Test
    fun `part 1 result`() = assertEquals(290_691, day10SyntaxScoring.part1().also(::println))

    @Test
    fun `part 2 test input`() = assertEquals(288_957, day10SyntaxScoringTestInput.part2())

    @Test
    fun `part 2 result`() = assertEquals(2_768_166_558, day10SyntaxScoring.part2().also(::println))

    @ParameterizedTest
    @ValueSource(strings = ["[]", "([])", "<([{}])>", "{()()()}"])
    fun `Test expect white space char and empty remainder when valid line`(input: String) {
        val (char, remainder) = Day10SyntaxScoring.toCorruptedClosingCharAndRemainingChars(input)

        assertAll(
            { assertEquals(' ', char) },
            { remainder.shouldBeEmpty() }
        )
    }

    @ParameterizedTest(name = "The first wrong closing char of {0} should be: {1}")
    @CsvSource(
        value = ["(] : ]", "{()()()> : >", "<([]){()}[{}]) : )", "{([(<{}[<>[]}>{[]{[(<()> : }"],
        delimiter = ':'
    )
    fun `Test find first wrong closing character when corrupted`(input: String, expected: Char) {
        val (closingTag, remainder) = Day10SyntaxScoring.toCorruptedClosingCharAndRemainingChars(input)

        assertAll(
            { assertEquals(expected, closingTag) },
            { assertTrue(remainder.isNotEmpty()) }
        )
    }

    @ParameterizedTest
    @ValueSource(strings = ["[[]", "{([])", "(<([{}])>", "<[{{()()()}", "[({(<(())[]>[[{[]{<()<>>"])
    fun `Test expect white space char and remainder when incomplete line`(input: String) {
        val (char, remainder) = Day10SyntaxScoring.toCorruptedClosingCharAndRemainingChars(input)

        assertAll(
            { assertEquals(' ', char) },
            { assertTrue(remainder.isNotEmpty()) }
        )
    }
}
