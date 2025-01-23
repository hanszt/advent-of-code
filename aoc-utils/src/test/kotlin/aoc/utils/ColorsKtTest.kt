package aoc.utils

import aoc.utils.BgColor.Companion.BROWN
import aoc.utils.BgColor.Companion.ICY
import aoc.utils.Colors.bgColorTable16Bit
import aoc.utils.Colors.colorTable16Bit
import aoc.utils.TextColor.Companion.BRIGHT_BLUE
import aoc.utils.TextColor.Companion.BRIGHT_GREEN
import aoc.utils.TextColor.Companion.BRIGHT_YELLOW
import aoc.utils.TextColor.Companion.RED
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
    fun `Print rainbow table`() {
        println(rainbowTable.joinToString("\n") {
            it.joinToString(" ") { it.withColor(TextColor(it.trim().toInt())) }
        })
    }

    @Test
    fun `print text with background color`() {
        val text = "Hello AOC!".withColors(
            textColor = TextColor(2),
            backgroundColor = BgColor(160)
        )
        println(text)
        text shouldHaveLength 34
    }

    /**
     * Also known as hsl (Hue saturation lightness)
     */
    @Test
    fun `test hsb to ansi`() {
        (0..360).forEach { h ->
            setOf(1.0, 0.5, 0.0).forEach { sat ->
                var b = 0.0
                while (b <= 1.0) {
                    print("%03d, %.1f, %.1f; ".format(h, sat, b).withColor(TextColor.hsb(h, sat, b)))
                    b += 0.1
                }
                print(" | ")
            }
            println()
        }
    }

    @Test
    fun `test rgb to ansi`() {
        setOf('r', 'g', 'b').forEach { s ->
            (0..255).forEach { v ->
                print(
                    "%02d ".format(v).withColor(
                        TextColor.rgb(
                            r = v.takeIf { s == 'r' } ?: 0,
                            g = v.takeIf { s == 'g' } ?: 0,
                            b = v.takeIf { s == 'b' } ?: 0)
                    )
                )
            }
            println()
        }
    }

    @Test
    fun `test gray scale to ansi by rgb`() {
        (0..255).forEach { v ->
            print("%02d ".format(v).withColor(TextColor.rgb(r = v, g = v, b = v)))
        }
    }

    @Test
    fun `test rgb custom`() {
        println("Hello".withColor(TextColor.rgb(0, 255, 255)))
    }

    @TestFactory
    fun `color tests`(): List<DynamicTest> {
        fun test(color: Color) = dynamicTest("aoc.utils.Color: $color") {
            val coloredText = "Colored text".withColor(color)
            println("coloredText = $coloredText")
            coloredText shouldStartWith "\u001B["
        }
        return listOf(RED, BRIGHT_BLUE, BRIGHT_GREEN, BRIGHT_YELLOW, BROWN, TextColor.YELLOW, BgColor.YELLOW, ICY).map(::test)
    }
}
