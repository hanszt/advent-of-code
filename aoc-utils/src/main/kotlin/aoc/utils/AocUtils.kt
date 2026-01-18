package aoc.utils

import java.util.*
import kotlin.math.abs

val oneOrMoreWhiteSpaces = "\\s+".toRegex()
val camelRegex = "(?<=[a-zA-Z0-9])[A-Z]".toRegex()

fun <T> linkedListOf(first: T): LinkedList<T> = LinkedList<T>().apply { add(first) }

/**
 * Requires that the input is sorted/grouped for the output sequence to emit unique elements.
 */
fun <T> Sequence<T>.distinctUntilChanged(): Sequence<T> = distinctUntilChanged { it }

/**
 * Requires that the input is sorted/grouped by the selector element for the output sequence to emit unique elements.
 *
 * Unix naming: distinctUntilChanged (the standard naming convention for the Unix uniq behavior).
 */
fun <T, K> Sequence<T>.distinctUntilChanged(selector: (T) -> K): Sequence<T> = Sequence {
    object : Iterator<T> {
        var prevSelected: K? = null;
        var hasNext = false
        var next: T? = null
        val iterator = this@distinctUntilChanged.iterator()

        override fun hasNext(): Boolean {
            if (hasNext) return true
            while (iterator.hasNext()) {
                val next = iterator.next()
                val selected = selector(next)
                val hasNextUniq = prevSelected != selected
                this.next = next
                prevSelected = selected
                if (hasNextUniq) {
                    hasNext = true
                    return true
                }
            }
            hasNext = false
            return false
        }

        override fun next(): T {
            if (hasNext()) {
                hasNext = false
                return next as T
            }
            throw NoSuchElementException()
        }
    }
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

inline fun <A, B, R> Pair<A, B>.mapFirst(transform: (A) -> R): Pair<R, B> = transform(first) to second

inline fun <A, B, R> Pair<A, B>.mapSecond(transform: (B) -> R): Pair<A, R> = first to transform(second)

inline fun <A, R> Pair<A, A>.mapBoth(transform: (A) -> R): Pair<R, R> = transform(first) to transform(second)

infix fun <A, B, C> Pair<A, B>.to(third: C): Triple<A, B, C> = Triple(first, second, third)

fun <T> Iterable<T>.toEnds() = first() to last()

inline fun <T, R> Iterable<T>.endsTo(transform: (T, T) -> R) = transform(first(), last())

fun CharSequence.isNaturalNumber() = "\\d+".toRegex().matches(this)

fun CharSequence.containsNoDigits() = "\\D+".toRegex().matches(this)

fun CharSequence.splitByBlankLine(): List<String> = split(Regex("(?m)^\\s*$")).map(String::trim)

/**
 * Splits into space-separate parts of input and maps each part.
 */
fun <R> List<String>.parts(map: (List<String>) -> R): List<R> = buildList {
    var cur = ArrayList<String>()
    for (s in this@parts) {
        if (s.isBlank()) {
            add(map(cur))
            cur = ArrayList()
            continue
        }
        cur.add(s)
    }
    if (cur.isNotEmpty()) add(map(cur))
}

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
            else -> buildSet(mapCapacity(size)) { this@toSetOf.forEach { add(mapper(it)) } }
        }
    }
    return mapTo(LinkedHashSet(), mapper).optimizeReadOnlySet()
}

fun <T> List<T>.rotated(amount: Int): List<T> {
    if (size <= 1) return this
    return List(size) {
        this[(it + amount) % this@rotated.size]
    }
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

fun CharArray.swap(i: Int, j: Int) {
    this[i] = this[j].also { this[j] = this[i] }
}

fun <T> Array<T>.swap(i: Int, j: Int) {
    this[i] = this[j].also { this[j] = this[i] }
}

fun IntArray.swap(i: Int, j: Int) {
    this[i] = this[j].also { this[j] = this[i] }
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
tailrec fun Long.gcd(other: Long): Long = if (other == 0L) this else other.gcd(this % other)
tailrec fun Int.gcd(other: Int): Int = if (other == 0) this else other.gcd(this % other)

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
fun Long.lcm(other: Long): Long = abs((this * other) / this.gcd(other))
fun Int.lcm(other: Int): Int = abs((this * other) / this.gcd(other))

fun sumNaturalNrs(start: Int = 1, bound: Int) = sumOfArithmeticSeries(start, bound, bound)

fun sumOfArithmeticSeries(first: Int, last: Int, termCount: Int) = (first + last) * termCount / 2

enum class Tag {
    RECURSIVE, PATH_SEARCH, GRAPH, FLOOD_FILL, INFINITY, THREE_D, AOC_MATH, MANY_WORLDS, MATRIX_OPTIMIZATION
}
