package aoc.utils

val oneOrMoreWhiteSpaces = "\\s+".toRegex()
val camelRegex = "(?<=[a-zA-Z0-9])[A-Z]".toRegex()

val <T> Iterable<T>.group: Map<T, List<T>> get() = groupBy { it }
val CharSequence.group: Map<Char, List<Char>> get() = groupBy { it }

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

/**
 * Combines the map().toSet() operation. Reduces two iterations to one
 *
 * Inspired from kotlin.collections.Collections.toSet. The helper methods are also copied from that file
 */
fun <T, R> Iterable<T>.toSetOf(mapper: (T) -> R): Set<R> {
    if (this is Collection) {
        return when (size) {
            0 -> emptySet()
            1 -> setOf(mapper(first()))
            else -> mapTo(LinkedHashSet(mapCapacity(size)), mapper)
        }
    }
    return mapTo(LinkedHashSet(), mapper).optimizeReadOnlySet()
}

private fun <T> Set<T>.optimizeReadOnlySet() = when (size) {
    0 -> emptySet()
    1 -> setOf(first())
    else -> this
}

private const val INT_MAX_POWER_OF_TWO: Int = 1 shl (Int.SIZE_BITS - 2)

private fun mapCapacity(expectedSize: Int): Int = when {
    // We are not coercing the value to a valid one and not throwing an exception. It is up to the caller to
    // properly handle negative values.
    expectedSize < 0 -> expectedSize
    expectedSize < 3 -> expectedSize + 1
    expectedSize < INT_MAX_POWER_OF_TWO -> ((expectedSize / 0.75F) + 1.0F).toInt()
    // any large value
    else -> Int.MAX_VALUE
}


fun <T : Comparable<T>> Iterable<T>.max() = maxOf { it }

fun <T : Comparable<T>> Iterable<T>.min() = minOf { it }

fun sumNaturalNrs(start: Int = 1, bound: Int) = sumOfArithmeticSeries(start, bound, bound)

fun sumOfArithmeticSeries(first: Int, last: Int, termCount: Int) = (first + last) * termCount / 2

fun <T> self(value: T) = value

fun <R> List<String>.parts(toResult: (List<String>) -> R): List<R> = buildList {
    var cur = ArrayList<String>()
    for (s in this@parts) {
        if (s.isEmpty()) {
            add(toResult(cur))
            cur = ArrayList()
            continue
        }
        cur.add(s)
    }
    if (cur.isNotEmpty()) add(toResult(cur))
}
