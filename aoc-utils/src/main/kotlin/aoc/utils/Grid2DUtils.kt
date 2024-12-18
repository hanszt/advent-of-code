@file:Suppress("unused")

package aoc.utils

import aoc.utils.model.GridPoint2D
import aoc.utils.model.GridPoint2D.Companion.by
import aoc.utils.model.gridPoint2D

typealias CharGrid = Array<CharArray>
typealias IntGrid = Array<IntArray>
typealias Grid<T> = Array<Array<T>>

/**
 * from list of strings to grid converters
 */
fun List<String>.toIntGrid(regex: Regex): IntGrid =
    Array(size) { y -> this[y].trim().split(regex).map(String::toInt).toIntArray() }

fun List<String>.toIntGrid(transform: (Char) -> Int): IntGrid = Array(size) { y ->
    IntArray(this[y].length) { x -> transform(this[y][x]) }
}

fun List<String>.toCharGrid(): CharGrid = Array(size) { this[it].toCharArray() }
fun List<String>.toCharGrid(mapper: (Char) -> Char): CharGrid = Array(size) { y ->
    CharArray(this[y].length) { x -> mapper(this[y][x]) }
}

inline fun <reified T> List<String>.toGridOf(regex: Regex, mapper: (String) -> T): Grid<T> =
    map { it.split(regex).map(mapper).toTypedArray() }.toTypedArray()

inline fun <reified T> List<String>.toGridOf(mapper: (Char) -> T): Grid<T> =
    map { it.map(mapper).toTypedArray() }.toTypedArray()

/**
 * Grid converters
 */
inline fun <T, reified R> Grid<T>.toGridOf(transform: (T) -> R): Grid<R> =
    map { it.map(transform).toTypedArray() }.toTypedArray()

inline fun <reified R> IntGrid.toGridOf(transform: (Int) -> R): Grid<R> =
    map { it.map(transform).toTypedArray() }.toTypedArray()

inline fun IntGrid.toIntGridOf(transform: (Int) -> Int): IntGrid =
    map { it.map(transform).toIntArray() }.toTypedArray()

inline fun <reified T> Grid<T>.copyGrid() = Array(size) { this[it].copyOf() }
fun CharGrid.copyGrid(): CharGrid = Array(size) { this[it].copyOf() }

/**
 * mapping, filtering, actions and matching
 */
inline fun <T, reified R> Grid<T>.mapByPoint(transform: (Int, Int) -> R): Grid<R> = Array(size) { y ->
    val row = this[y]
    Array(row.size) { x -> transform(x, y) }
}

inline fun <T, reified R> Grid<T>.mapByPoint(transform: (GridPoint2D) -> R): Grid<R> = mapByPoint { x, y ->
    transform(gridPoint2D(x, y))
}

inline fun <reified R> IntGrid.mapByPoint(transform: (Int, Int) -> R): Grid<R> = Array(size) { y ->
    val row = this[y]
    Array(row.size) { x -> transform(x, y) }
}

inline fun <reified R> IntGrid.mapByPoint(transform: (GridPoint2D) -> R): Grid<R> = mapByPoint { x, y ->
    transform(gridPoint2D(x, y))
}

inline fun <reified R> IntGrid.flatMapByPoint(transform: (Int, Int) -> R): List<R> = buildList {
    this@flatMapByPoint.forEachIndexed { y, r ->
        r.indices.forEach { x -> add(transform(x, y)) }
    }
}

inline fun <reified R : Comparable<R>> IntGrid.gridMaxOf(transform: (Int, Int) -> R): R {
    getOrNull(GridPoint2D.ZERO) ?: throw NoSuchElementException()
    var max = transform(0, 0)
    forEachIndexed { y, r ->
        r.indices.forEach { x -> max = max.coerceAtLeast(transform(x, y)) }
    }
    return max
}

inline fun <reified R : Comparable<R>> IntGrid.gridMaxOf(transform: (GridPoint2D) -> R): R = gridMaxOf { x, y ->
    transform(gridPoint2D(x, y))
}

inline fun <reified R> IntGrid.flatMapByPoint(transform: (GridPoint2D) -> R): List<R> = flatMapByPoint { x, y ->
    transform(gridPoint2D(x, y))
}

inline fun <T> Grid<T>.anyInGrid(predicate: (T) -> Boolean) = any { it.any(predicate) }

inline fun IntGrid.allInGrid(predicate: (Int) -> Boolean) = all { it.all(predicate) }

inline fun IntGrid.gridCount(predicate: (Int, Int) -> Boolean): Int =
    indices.sumOf { y -> this[y].indices.count { x -> predicate(x, y) } }

inline fun IntGrid.gridCount(predicate: (GridPoint2D) -> Boolean): Int =
    indices.sumOf { y -> this[y].indices.count { x -> predicate(x by y) } }

inline fun <T> Grid<T>.forEachInGrid(action: (T) -> Unit) = forEach { it.forEach(action) }

inline fun <T> Grid<T>.forEachPointAndValue(action: (Int, Int, T) -> Unit) =
    withIndex().forEach { (y, row) -> row.withIndex().forEach { (x, value) -> action(x, y, value) } }

inline fun <T> Grid<T>.forEachPoint(action: (Int, Int) -> Unit) =
    indices.forEach { y -> first().indices.forEach { x -> action(x, y) } }

fun ClosedGridPoint2DRange.toCharGrid(transform: (GridPoint2D) -> Char): CharGrid = Array(endInclusive.y - start.y) { y ->
    CharArray(endInclusive.x - start.x) { x -> transform(gridPoint2D(x, y)) }
}

inline fun List<String>.forEachPoint(action: (Int, Int) -> Unit) =
    withIndex().forEach { (y, row) -> row.indices.forEach { x -> action(x, y) } }

inline fun List<String>.forEachPoint(action: (GridPoint2D) -> Unit) =
    withIndex().forEach { (y, row) -> row.indices.forEach { x -> action(gridPoint2D(x, y)) } }

inline fun <R> List<String>.foldByPoint(initial: R, action: (R, Int, Int) -> R): R {
    var acc = initial
    forEachPoint { x, y -> acc = action(acc, x, y) }
    return acc
}

inline fun IntGrid.forEachPoint(action: (Int, Int) -> Unit) =
    indices.forEach { y -> first().indices.forEach { x -> action(x, y) } }

inline fun IntGrid.forEachPointAndValue(action: (Int, Int, Int) -> Unit) =
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
 * Getters
 */
operator fun List<String>.get(point: GridPoint2D): Char = this[point.y][point.x]
operator fun CharGrid.get(point: GridPoint2D): Char = this[point.y][point.x]
operator fun IntGrid.get(point: GridPoint2D): Int = this[point.y][point.x]
fun List<String>.getOrNull(point: GridPoint2D): Char? = getOrNull(point.y)?.getOrNull(point.x)
fun CharGrid.getOrNull(point: GridPoint2D): Char? = getOrNull(point.y)?.getOrNull(point.x)
fun IntGrid.getOrNull(point: GridPoint2D): Int? = getOrNull(point.y)?.getOrNull(point.x)
fun <T> Grid<T>.getOrNull(point: GridPoint2D): T? = getOrNull(point.y)?.getOrNull(point.x)

/**
 * Setters
 */
operator fun CharGrid.set(point: GridPoint2D, char: Char) {
    this[point.y][point.x] = char
}

operator fun IntGrid.set(point: GridPoint2D, int: Int) {
    this[point.y][point.x] = int
}

/**
 * Ranges
 */
operator fun GridPoint2D.rangeTo(other: GridPoint2D): GridPoint2DRange = GridPoint2DRange(
    start = this,
    endInclusive = other,
)

infix fun IntRange.by(other: IntRange): GridPoint2DRange {
    return GridPoint2DRange(gridPoint2D(start, other.start), gridPoint2D(endInclusive, other.endInclusive))
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

inline fun <reified T> Grid<T>.rotated(): Grid<T> =
    first().indices.map { col -> indices.reversed().map { row -> this[row][col] }.toTypedArray() }.toTypedArray()

inline fun <reified T> Grid<T>.rotatedCc(): Grid<T> =
    first().indices.reversed().map { col -> indices.map { row -> this[row][col] }.toTypedArray() }.toTypedArray()

fun IntGrid.rotated(): IntGrid = first().indices.map { col ->
    indices.reversed().map { row -> this[row][col] }.toIntArray()
}.toTypedArray()

fun IntGrid.rotatedCc(): IntGrid = first().indices.reversed()
    .map { col -> indices.map { row -> this[row][col] }.toIntArray() }
    .toTypedArray()

fun IntGrid.mirroredHorizontally(): IntGrid = Array(size) { y ->
    val row = this[y]
    IntArray(row.size) { x -> row[row.lastIndex - x] }
}

inline fun <reified T> Grid<T>.mirroredHorizontally(): Grid<T> = Array(size) { y ->
    val row = this[y]
    Array<T>(row.size) { x -> row[row.lastIndex - x] }
}

fun IntGrid.mirroredVertically(): IntGrid = Array(size) { y -> this[lastIndex - y] }
inline fun <reified T> Grid<T>.mirroredVertically(): Grid<T> = Array(size) { y -> this[lastIndex - y] }

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

fun IntGrid.gridAsString(spacing: Int = 2, separator: String = "") =
    map { row -> row.joinToString(separator) { "%${spacing}d".format(it) } }.joinToString("\n") { it }

fun <T> IntGrid.gridAsString(spacing: Int = 2, separator: String = "", selector: (Int) -> T) =
    map { row -> row.joinToString(separator) { "%${spacing}s".format(selector(it)) } }
        .joinToString("\n") { it }

inline fun <T, R> Grid<T>.gridAsString(
    spacing: Int = 2,
    separator: String = "",
    crossinline selector: (T) -> R
) = map { row -> row.joinToString(separator) { "%${spacing}s".format(selector(it)) } }.joinToString("\n") { it }

fun <T> Grid<T>.gridAsString(spacing: Int = 2, separator: String = "") =
    gridAsString(spacing, separator) { it }

fun <T> List<List<T>>.gridAsString(spacing: Int = 2, separator: String = "") =
    map { row -> row.joinToString(separator) { "%${spacing}s".format(it) } }.joinToString("\n") { it }

fun <T, R> List<List<T>>.gridAsString(spacing: Int = 2, separator: String = "", selector: (T) -> R) =
    map { row -> row.joinToString(separator) { "%${spacing}s".format(selector(it)) } }
        .joinToString("\n") { it }

@JvmName("stringListAsGrid")
fun List<String>.gridAsString(spacing: Int = 2, separator: String = "") = map { row ->
    row.asSequence().joinToString(separator) { "%${spacing}c".format(it) }
}.joinToString("\n") { it }

@JvmName("stringListAsGrid")
fun <T> List<String>.gridAsString(spacing: Int = 2, separator: String = "", selector: (Char) -> T) = map { row ->
    row.asSequence().joinToString(separator) { "%${spacing}s".format(selector(it)) }
}.joinToString("\n") { it }

/**
 * Swapping points in a grid
 */
fun CharGrid.swap(p1x: Int, p1y: Int, p2x: Int, p2y: Int) {
    val tmp = this[p1y][p1x]
    this[p1y][p1x] = this[p2y][p2x]
    this[p2y][p2x] = tmp
    1..<3
}

fun CharGrid.swap(point1: GridPoint2D, point2: GridPoint2D) = swap(point1.x, point1.y, point2.x, point2.y)

interface ClosedGridPoint2DRange {
    val start: GridPoint2D
    val endInclusive: GridPoint2D

    operator fun contains(point: GridPoint2D): Boolean = point.x in start.x..endInclusive.x && point.y in start.y..endInclusive.y
    fun isEmpty(): Boolean = start.x > endInclusive.x || start.y > endInclusive.y
}

interface OpenEndedGridPoint2DRange {
    val start: GridPoint2D
    val endExclusive: GridPoint2D

    operator fun contains(point: GridPoint2D): Boolean = point.x in start.x..endExclusive.x && point.y in start.y..endExclusive.y
}

class GridPoint2DRange internal constructor(
    override val start: GridPoint2D,
    override val endInclusive: GridPoint2D
) : GridPoint2DProgression(start, endInclusive), ClosedGridPoint2DRange
