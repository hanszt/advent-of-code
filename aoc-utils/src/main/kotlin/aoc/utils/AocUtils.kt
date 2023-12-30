package aoc.utils

import java.util.LinkedList
import kotlin.math.abs

val oneOrMoreWhiteSpaces = "\\s+".toRegex()
val camelRegex = "(?<=[a-zA-Z0-9])[A-Z]".toRegex()

val <T> Iterable<T>.group: Map<T, List<T>> get() = groupBy { it }
val <T> Sequence<T>.group: Map<T, List<T>> get() = groupBy { it }
val CharSequence.group: Map<Char, List<Char>> get() = groupBy { it }

val <T> Iterable<T>.grouping: Grouping<T, T> get() = groupingBy { it }
val <T> Sequence<T>.grouping: Grouping<T, T> get() = groupingBy { it }
val CharSequence.grouping: Grouping<Char, Char> get() = groupingBy { it }

fun <T> linkedListOf(first: T): LinkedList<T> = LinkedList<T>().apply { add(first) }

fun sum(add: (Int) -> Int): Int {
    var initial = 0
    return add(initial)
}

fun <T, R, C : MutableCollection<in R>> Iterable<T>.zipWithNextTo(destination: C, mapper: (T, T) -> R): C {
    val iterator = iterator()
    if (iterator.hasNext()) {
        var cur = iterator.next()
        while (iterator.hasNext()) {
            val next = iterator.next()
            destination.add(mapper(cur, next))
            cur = next
        }
    }
    return destination
}

operator fun <K, V : Any> Map<K, V>.invoke(key: K): V = getValue(key)

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

fun <T> Array<T>.swap(sourceIndex: Int, destIndex: Int) {
    val temp = this[sourceIndex]
    this[sourceIndex] = this[destIndex]
    this[destIndex] = temp
}

fun CharArray.swap(sourceIndex: Int, destIndex: Int) {
    val temp = this[sourceIndex]
    this[sourceIndex] = this[destIndex]
    this[destIndex] = temp
}

fun <T : Comparable<T>> Iterable<T>.max() = maxOf { it }

fun <T : Comparable<T>> Iterable<T>.min() = minOf { it }


/**
 *
 * Calculates the greatest common divisor using Euclides algorithm
 *
 * @receiver long to do a gcd with
 * @param other the other long
 * @return The greatest common divisor
 *
 * @sample aoc.utils.AocUtilsKtTest.greatestCommonDivisorSample
 */
tailrec infix fun Long.gcd(other: Long): Long = if (other == 0L) this else other gcd this % other
tailrec infix fun Int.gcd(other: Int): Int = if (other == 0) this else other gcd this % other

/**
 * Calculates the least common multiple of two numbers
 *
 * [Finding the Least Common Multiple in Java](https://www.baeldung.com/java-least-common-multiple)
 *
 * @receiver the nr
 * @param other the other nr
 * @return The least common multiple
 *
 * @sample aoc.utils.AocUtilsKtTest.leastCommonMultipleSample
 */
infix fun Long.lcm(other: Long): Long = abs((this * other) / gcd(other))
infix fun Int.lcm(other: Int): Int = abs((this * other) / gcd(other))

fun sumNaturalNrs(start: Int = 1, bound: Int) = sumOfArithmeticSeries(start, bound, bound)

fun sumOfArithmeticSeries(first: Int, last: Int, termCount: Int) = (first + last) * termCount / 2

val BooleanArray.trueCount get() = count { it }

fun <T> self(value: T) = value
