package aoc.utils

import aoc.utils.Colors.RESET
import aoc.utils.TextColor.Companion.BRIGHT_BLUE
import aoc.utils.TextColor.Companion.CYAN
import aoc.utils.TextColor.Companion.GREEN
import aoc.utils.TextColor.Companion.RED
import aoc.utils.TextColor.Companion.YELLOW


private val ansiColors = listOf(BRIGHT_BLUE, RESET, GREEN, RESET, YELLOW, RESET, CYAN, RESET)

data class AocResult(val name: String, val result: Result<String>, val solveTimeNanos: Long) {

    private val dayNr = name.filter(Char::isDigit).take(2).toInt()

    private val color = dayNr.toColor(ansiColors)

    private fun Int.toColor(colors: List<Color>) = if (result.isSuccess) colors[this % colors.size] else RED

    override fun toString(): String = "%-40s Result: %-50s Solve time: %-7s"
        .format(name, result.getOrElse { "Failure: ${it.message}" }, solveTimeNanos.nanoTimeToFormattedDuration())
        .withColor(color)
}
