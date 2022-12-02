package aoc.utils

const val camelRegex = "(?<=[a-zA-Z0-9])[A-Z]"

fun String.camelCaseToSentence(): String = camelRegex.toRegex().replace(this) { " ${it.value}" }
    .lowercase().replaceFirstChar(Char::uppercase)

fun CharSequence.splitByBlankLine(): List<String> = split(Regex("(?m)^\\s*$")).map(String::trim)

fun Long.nanoTimeToFormattedDuration(spacer: Int = 7, decimalPlaces: Int = 3): String {
    val timeSpacer: Int = spacer + decimalPlaces
    return when {
        this < 1e3 -> "$timeSpacer$this ns"
        this < 1e6 -> "%$timeSpacer.${decimalPlaces}f µs".format(this / 1e3)
        this < 1e9 -> "%$timeSpacer.${decimalPlaces}f ms".format(this / 1e6)
        else -> "%$timeSpacer.${decimalPlaces}f s".format(this / 1e9)
    }
}
