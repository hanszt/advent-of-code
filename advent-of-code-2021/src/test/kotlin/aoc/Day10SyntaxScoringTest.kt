package aoc

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertTrue

internal class Day10SyntaxScoringTest {

    @Test
    fun `part 1 test input`() = assertEquals(26_397, Day10SyntaxScoring.part1("input/day10test.txt"))

    @Test
    fun `part 1 result`() = assertEquals(290_691, Day10SyntaxScoring.part1().also(::println))

    @Test
    fun `part 2 test input`() = assertEquals(288_957, Day10SyntaxScoring.part2("input/day10test.txt"))

    @Test
    fun `part 2 result`() = assertEquals(2_768_166_558, Day10SyntaxScoring.part2().also(::println))

    @ParameterizedTest
    @ValueSource( strings = ["[]", "([])", "<([{}])>", "{()()()}"])
    fun `Test expect white space char and empty remainder when valid line`(input: String) {
        val (char, remainder) = Day10SyntaxScoring.toCorruptedClosingCharAndRemainingChars(input)
        assertEquals(' ', char)
        assertTrue(remainder.isEmpty())
    }

    @ParameterizedTest
    @ValueSource( strings = ["(] : ]", "{()()()> : >", "<([]){()}[{}]) : )", "{([(<{}[<>[]}>{[]{[(<()> : }"])
    fun `Test find first wrong closing character when corrupted`(inputToExpectedWrongTag: String) {
        val (input, expectedWrongTag) = inputToExpectedWrongTag.split(" : ")
        val (closingTag, remainder) = Day10SyntaxScoring.toCorruptedClosingCharAndRemainingChars(input)
        assertEquals(expectedWrongTag.single(), closingTag)
        assertTrue(remainder.isNotEmpty())
    }

    @ParameterizedTest
    @ValueSource( strings = ["[[]", "{([])", "(<([{}])>", "<[{{()()()}", "[({(<(())[]>[[{[]{<()<>>"])
    fun `Test expect white space char and remainder when incomplete line`(input: String) {
        val (char, remainder) = Day10SyntaxScoring.toCorruptedClosingCharAndRemainingChars(input)
        assertEquals(' ', char)
        assertTrue(remainder.isNotEmpty())
    }
}
