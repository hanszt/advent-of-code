package aoc.utils

val oneOrMoreWhiteSpaces = "\\s+".toRegex()
val camelRegex = "(?<=[a-zA-Z0-9])[A-Z]".toRegex()

inline fun <A, B, R> Pair<A, B>.mapFirst(transform: (A) -> R): Pair<R, B> = transform(first) to second

inline fun <A, B, R> Pair<A, B>.mapSecond(transform: (B) -> R): Pair<A, R> = first to transform(second)

inline fun <A, R> Pair<A, A>.mapBoth(transform: (A) -> R): Pair<R, R> = transform(first) to transform(second)

infix fun <A, B, C> Pair<A, B>.to(third: C): Triple<A, B, C> = Triple(first, second, third)

fun <T> Iterable<T>.toEnds() = first() to last()

inline fun <T, R> Iterable<T>.endsTo(transform: (T, T) -> R) = transform(first(), last())

fun CharSequence.isNaturalNumber() = "\\d+".toRegex().matches(this)

fun CharSequence.containsNoDigits() = "\\D+".toRegex().matches(this)

fun CharSequence.splitByBlankLine(): List<String> = split(Regex("(?m)^\\s*$")).map(String::trim)

fun Int.wrapBack(to: Int, after: Int): Int = (this - to) % (after - to + 1) + to

fun String.camelCaseToSentence(): String = camelRegex.replace(this) { " ${it.value}" }
    .lowercase().replaceFirstChar(Char::uppercase)

fun Long.nanoTimeToFormattedDuration(spacer: Int = 7, decimalPlaces: Int = 3): String {
    val timeSpacer: Int = spacer + decimalPlaces
    return when {
        this < 1e3 -> "$timeSpacer$this ns"
        this < 1e6 -> "%$timeSpacer.${decimalPlaces}f µs".format(this / 1e3)
        this < 1e9 -> "%$timeSpacer.${decimalPlaces}f ms".format(this / 1e6)
        else -> "%$timeSpacer.${decimalPlaces}f s".format(this / 1e9)
    }
}

fun <T : Comparable<T>> Iterable<T>.max() = maxOf { it }

fun <T : Comparable<T>> Iterable<T>.min() = minOf { it }

fun sumNaturalNrs(start: Int = 1, bound: Int) = sumOfArithmeticSeries(start, bound, bound)

fun sumOfArithmeticSeries(first: Int, last: Int, termCount: Int) = (first + last) * termCount / 2

fun <T> self(value: T) = value

fun <R> List<String>.parts(map: (List<String>) -> R): List<R> = buildList {
    var cur = ArrayList<String>()
    for (s in this@parts) {
        if (s == "") {
            add(map(cur))
            cur = ArrayList()
            continue
        }
        cur.add(s)
    }
    if (cur.isNotEmpty()) add(map(cur))
}
