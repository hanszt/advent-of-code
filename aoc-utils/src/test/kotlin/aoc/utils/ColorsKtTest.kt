package aoc.utils

import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldHaveLength
import io.kotest.matchers.string.shouldStartWith
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

internal class ColorsKtTest {

    @Test
    fun `print ansi Color table`() = colorTable16Bit.also { it.size shouldBe 256 }.printTable()

    @Test
    fun `print ansi background color table`() = bgColorTable16Bit.also { it.size shouldBe 256 }.printTable()

    @Test
    fun `print text with background color`() {
        val text = "Hello AOC!".withColors(
            textColor = colorWithAnsiTableNr(2),
            backgroundColor = bgColorWithAnsiTableNr(160)
        )
        println(text)
        text shouldHaveLength 34
    }

    @TestFactory
    fun `color tests`(): List<DynamicTest> {
        fun test(color: Color) = dynamicTest("aoc.utils.Color: $color") {
            val coloredText = "Colored text".withColor(color)
            println("coloredText = $coloredText")
            coloredText shouldStartWith "\u001B["
        }
        return listOf(RED, BRIGHT_BLUE, BRIGHT_GREEN, BRIGHT_YELLOW, BROWN_BG, YELLOW, YELLOW_BG, ICY_BG).map(::test)
    }
}
