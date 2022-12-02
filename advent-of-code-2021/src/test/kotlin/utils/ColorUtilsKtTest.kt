package utils

import org.junit.jupiter.api.Test

internal class ColorUtilsKtTest {

    @Test
    fun `print ansi Color table`() = printColorTabel()

    @Test
    fun `print ansi background color table`() = printColorTabel(bgColors = true)

    private fun printColorTabel(bgColors: Boolean = false) =  to16bitAnsiColorTabel(bgColors).forEach { (nr, color) ->
        print("${color}%4s$RESET".format(nr))
        if ((nr + 1) % SIXTEEN_BIT == 0) println()
    }
}
