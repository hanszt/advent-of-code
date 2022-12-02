package utils

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class ColorUtilsKtTest {

    @Test
    fun `print ansi Color table`() = printColorTabel()

    @Test
    fun `print ansi background color table`() = printColorTabel(bgColors = true)

    private fun printColorTabel(bgColors: Boolean = false) {
        val to16bitAnsiColorTabel = to16bitAnsiColorTabel(bgColors)
        to16bitAnsiColorTabel.forEach { (nr, color) ->
            print("${color}%4s$RESET".format(nr))
            if ((nr + 1) % SIXTEEN_BIT == 0) println()
        }
        assertEquals(256, to16bitAnsiColorTabel.size)
    }
}
