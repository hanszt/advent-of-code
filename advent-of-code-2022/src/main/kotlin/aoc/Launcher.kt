package aoc

import utils.*

fun main(vararg args: String) {
    println(readTextFromResource("/title.txt").withColor(RED))

    val inputDir = args.firstOrNull() ?: "input"

    val results = sequenceOf(
        Day01CalorieCounting("$inputDir/day01.txt"),
        Day02RockPaperScissors("$inputDir/day02.txt")
    ).flatMap(ChallengeDay::runParts)
        .onEach(::println)
        .toList()

    println("%nTotal solve time: %2.3f seconds%n".format(results.sumOf(AocResult::solveTimeNanos) / 1e9))
    println(readTextFromResource("/banner.txt").withColor(GREEN))
}

private val ansiColors = listOf(RESET, BRIGHT_BLUE, GREEN, RESET, YELLOW, RESET, CYAN, RESET)

data class AocResult(val name: String, val result: Result<String>, val solveTimeNanos: Long) {

    private val dayNr = name.slice(3..4).toInt()

    private val color = dayNr.toColor(ansiColors)

    private fun Int.toColor(colors: List<String>) = if (result.isSuccess) colors[this % colors.size] else RED

    override fun toString(): String = "%-40s Result: %-50s Solve time: %-7s"
        .format(name, result.getOrElse { "Failure: ${it.message}" }, solveTimeNanos.nanoTimeToFormattedDuration())
        .withColor(color)
}
