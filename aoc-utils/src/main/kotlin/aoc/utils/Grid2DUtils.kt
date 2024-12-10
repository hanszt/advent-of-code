@file:Suppress("unused")

package aoc.utils

import aoc.utils.model.GridPoint2D
import aoc.utils.model.gridPoint2D

/**
 * from list of strings to grid converters
 */
typealias CharGrid = Array<CharArray>

fun List<String>.toIntGrid(regex: Regex): Array<IntArray> =
    map { it.trim().split(regex).map(String::toInt).toIntArray() }.toTypedArray()

fun List<String>.toIntGrid(transform: (Char) -> Int): Array<IntArray> =
    map { it.map(transform).toIntArray() }.toTypedArray()

fun List<String>.toCharGrid(): CharGrid = map { it.toCharArray() }.toTypedArray()

inline fun <reified T> List<String>.toGridOf(regex: Regex, mapper: (String) -> T): Array<Array<T>> =
    map { it.split(regex).map(mapper).toTypedArray() }.toTypedArray()

inline fun <reified T> List<String>.toGridOf(mapper: (Char) -> T): Array<Array<T>> =
    map { it.map(mapper).toTypedArray() }.toTypedArray()

/**
 * Grid converters
 */
inline fun <T, reified R> Array<Array<T>>.toGridOf(transform: (T) -> R): Array<Array<R>> =
    map { it.map(transform).toTypedArray() }.toTypedArray()

inline fun <reified R> Array<IntArray>.toGridOf(transform: (Int) -> R): Array<Array<R>> =
    map { it.map(transform).toTypedArray() }.toTypedArray()

inline fun Array<IntArray>.toIntGridOf(transform: (Int) -> Int): Array<IntArray> =
    map { it.map(transform).toIntArray() }.toTypedArray()

inline fun <reified T> Array<Array<T>>.copyGrid() = map(Array<T>::copyOf).toTypedArray()

/**
 * mapping, filtering, actions and matching
 */
inline fun <T, reified R> Array<Array<T>>.mapByPoint(transform: (Int, Int) -> R): Array<Array<R>> =
    indices.map { y -> this[y].indices.map { x -> transform(x, y) }.toTypedArray() }.toTypedArray()

inline fun <reified R> Array<IntArray>.mapByPoint(transform: (Int, Int) -> R): Array<Array<R>> =
    indices.map { y -> this[y].indices.map { x -> transform(x, y) }.toTypedArray() }.toTypedArray()

inline fun <T> Array<Array<T>>.anyInGrid(predicate: (T) -> Boolean) = any { it.any(predicate) }

inline fun Array<IntArray>.allInGrid(predicate: (Int) -> Boolean) = all { it.all(predicate) }

inline fun Array<IntArray>.gridCount(transform: (Int, Int) -> Boolean): Int =
    indices.sumOf { y -> this[y].indices.count { x -> transform(x, y) } }

inline fun <T> Array<Array<T>>.forEachInGrid(action: (T) -> Unit) = forEach { it.forEach(action) }

inline fun <T> Array<Array<T>>.forEachPointAndValue(action: (Int, Int, T) -> Unit) =
    withIndex().forEach { (y, row) -> row.withIndex().forEach { (x, value) -> action(x, y, value) } }

inline fun <T> Array<Array<T>>.forEachPoint(action: (Int, Int) -> Unit) =
    indices.forEach { y -> first().indices.forEach { x -> action(x, y) } }

inline fun List<String>.forEachPoint(action: (Int, Int) -> Unit) =
    withIndex().forEach { (y, row) -> row.indices.forEach { x -> action(x, y) } }

inline fun <R> List<String>.foldByPoint(initial: R, action: (R, Int, Int) -> R): R {
    var acc = initial
    forEachPoint { x, y -> acc = action(acc, x, y) }
    return acc
}

inline fun Array<IntArray>.forEachPoint(action: (Int, Int) -> Unit) =
    indices.forEach { y -> first().indices.forEach { x -> action(x, y) } }

inline fun Array<IntArray>.forEachPointAndValue(action: (Int, Int, Int) -> Unit) =
    withIndex().forEach { (y, row) -> row.withIndex().forEach { (x, value) -> action(x, y, value) } }

/**
 * Find first point matching the predicate
 */
fun List<String>.findPoint(predicate: (Char) -> Boolean): GridPoint2D? {
    for ((y, line) in this.withIndex()) {
        for ((x, c) in line.withIndex()) {
            if (predicate(c)) {
                return gridPoint2D(x, y)
            }
        }
    }
    return null
}

/**
 * Rotation and mirroring
 */
@JvmName("gridRotated")
fun <T> List<List<T>>.rotated(): List<List<T>> =
    first().indices.map { col -> indices.reversed().map { row -> this[row][col] } }

@JvmName("gridRotatedCc")
fun <T> List<List<T>>.rotatedCc(): List<List<T>> =
    first().indices.reversed().map { col -> indices.map { row -> this[row][col] } }

fun List<String>.rotated(): List<String> =
    first().indices.map { col -> indices.reversed().map { row -> this[row][col] }.joinToString("") }

fun List<String>.rotatedCc(): List<String> =
    first().indices.reversed().map { col -> indices.map { row -> this[row][col] }.joinToString("") }

inline fun <reified T> Array<Array<T>>.rotated(): Array<Array<T>> =
    first().indices.map { col -> indices.reversed().map { row -> this[row][col] }.toTypedArray() }.toTypedArray()

inline fun <reified T> Array<Array<T>>.rotatedCc(): Array<Array<T>> =
    first().indices.reversed().map { col -> indices.map { row -> this[row][col] }.toTypedArray() }.toTypedArray()

fun Array<IntArray>.rotated(): Array<IntArray> =
    first().indices.map { col -> indices.reversed().map { row -> this[row][col] }.toIntArray() }.toTypedArray()

fun Array<IntArray>.rotatedCc(): Array<IntArray> =
    first().indices.reversed().map { col -> indices.map { row -> this[row][col] }.toIntArray() }.toTypedArray()

fun Array<IntArray>.mirroredHorizontally(): Array<IntArray> =
    indices.reversed().map { row -> this[row] }.toTypedArray()

fun Array<IntArray>.mirroredVertically(): Array<IntArray> =
    map { row -> row.indices.reversed().map { col -> row[col] }.toIntArray() }.toTypedArray()

inline fun <reified T> Array<Array<T>>.mirroredHorizontally(): Array<Array<T>> =
    map { row -> row.indices.reversed().map { col -> row[col] }.toTypedArray() }.toTypedArray()

inline fun <reified T> Array<Array<T>>.mirroredVertically(): Array<Array<T>> =
    indices.reversed().map { row -> this[row] }.toTypedArray()

/**
 * Grid as string
 *
 * Functions to convert a grid to a string representation
 */
fun Array<CharArray>.gridAsString(spacing: Int = 2, separator: String = "") =
    map { row -> row.joinToString(separator) { "%${spacing}c".format(it) } }.joinToString("\n") { it }

fun <T> Array<CharArray>.gridAsString(spacing: Int = 2, separator: String = "", selector: (Char) -> T) =
    map { row -> row.joinToString(separator) { "%${spacing}s".format(selector(it)) } }
        .joinToString("\n") { it }

fun Array<IntArray>.gridAsString(spacing: Int = 2, separator: String = "") =
    map { row -> row.joinToString(separator) { "%${spacing}d".format(it) } }.joinToString("\n") { it }

fun <T> Array<IntArray>.gridAsString(spacing: Int = 2, separator: String = "", selector: (Int) -> T) =
    map { row -> row.joinToString(separator) { "%${spacing}s".format(selector(it)) } }
        .joinToString("\n") { it }

inline fun <T, R> Array<Array<T>>.gridAsString(
    spacing: Int = 2,
    separator: String = "",
    crossinline selector: (T) -> R
) = map { row -> row.joinToString(separator) { "%${spacing}s".format(selector(it)) } }.joinToString("\n") { it }

fun <T> Array<Array<T>>.gridAsString(spacing: Int = 2, separator: String = "") =
    gridAsString(spacing, separator) { it }

fun <T> List<List<T>>.gridAsString(spacing: Int = 2, separator: String = "") =
    map { row -> row.joinToString(separator) { "%${spacing}s".format(it) } }.joinToString("\n") { it }

fun <T, R> List<List<T>>.gridAsString(spacing: Int = 2, separator: String = "", selector: (T) -> R) =
    map { row -> row.joinToString(separator) { "%${spacing}s".format(selector(it)) } }
        .joinToString("\n") { it }

@JvmName("stringListAsGrid")
fun List<String>.gridAsString(spacing: Int = 2, separator: String = "") =
    map { row -> row.asSequence().joinToString(separator) { "%${spacing}c".format(it) } }.joinToString("\n") { it }

@JvmName("stringListAsGrid")
fun <T> List<String>.gridAsString(spacing: Int = 2, separator: String = "", selector: (Char) -> T) =
    map { row -> row.asSequence().joinToString(separator) { "%${spacing}s".format(selector(it)) } }
        .joinToString("\n") { it }
