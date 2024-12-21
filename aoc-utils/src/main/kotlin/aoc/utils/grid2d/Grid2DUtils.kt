@file:Suppress("unused")

package aoc.utils.grid2d

import aoc.utils.grid2d.GridPoint2D.Companion.by

typealias MutableCharGrid = Array<CharArray>
typealias MutableIntGrid = Array<IntArray>
typealias MutableBooleanGrid = Array<BooleanArray>
typealias MutableGrid<T> = Array<Array<T>>

/**
 * from list of strings to grid converters
 */
fun List<String>.toMutableIntGrid(regex: Regex): MutableIntGrid =
    Array(size) { y -> this[y].trim().split(regex).map(String::toInt).toIntArray() }

fun List<String>.toMutableIntGrid(transform: (Char) -> Int): MutableIntGrid = Array(size) { y ->
    IntArray(this[y].length) { x -> transform(this[y][x]) }
}

fun List<String>.toIntGrid(transform: (Char) -> Int): IntGrid = getOrNull(0)?.let {
    buildIntGrid(it.length, this.size) { x, y -> transform(this[y][x]) }
} ?: emptyIntGrid()

fun List<String>.toMutableCharGrid(): MutableCharGrid = Array(size) { this[it].toCharArray() }
fun List<String>.toMutableCharGrid(mapper: (Char) -> Char): MutableCharGrid = Array(size) { y ->
    CharArray(this[y].length) { x -> mapper(this[y][x]) }
}

inline fun <reified T> List<String>.toMutableGrid(regex: Regex, mapper: (String) -> T): MutableGrid<T> =
    map { it.split(regex).map(mapper).toTypedArray() }.toTypedArray()

inline fun <reified T> List<String>.toMutableGrid(mapper: (Char) -> T): MutableGrid<T> =
    map { it.map(mapper).toTypedArray() }.toTypedArray()

/**
 * Grid converters
 */
inline fun <T, reified R> MutableGrid<T>.toMutableGrid(transform: (T) -> R): MutableGrid<R> =
    map { it.map(transform).toTypedArray() }.toTypedArray()

inline fun <reified R> MutableIntGrid.toMutableGrid(transform: (Int) -> R): MutableGrid<R> =
    map { it.map(transform).toTypedArray() }.toTypedArray()

inline fun <reified R> MutableCharGrid.toMutableGrid(transform: (Char) -> R): MutableGrid<R> = Array(size) { y ->
    Array(this[y].size) { x -> transform(this[y][x]) }
}

inline fun MutableIntGrid.toMutableIntGrid(transform: (Int) -> Int): MutableIntGrid =
    map { it.map(transform).toIntArray() }.toTypedArray()

inline fun <reified T> MutableGrid<T>.copyGrid(): MutableGrid<T> = Array(size) { this[it].copyOf() }
fun MutableCharGrid.copyGrid(): MutableCharGrid = Array(size) { this[it].copyOf() }

fun <T> MutableGrid<T>.toGrid(transform: (T) -> T): Grid<T> = getOrNull(0)
    ?.let {
        buildGrid(it.size, size) { x, y -> transform(this[y][x]) }
    } ?: emptyGrid()

/**
 * mapping, filtering, actions and matching
 */
inline fun <T, reified R> MutableGrid<T>.mapByPoint(transform: (Int, Int) -> R): MutableGrid<R> = Array(size) { y ->
    val row = this[y]
    Array(row.size) { x -> transform(x, y) }
}

inline fun <T, reified R> MutableGrid<T>.mapByPoint(transform: (GridPoint2D) -> R): MutableGrid<R> = mapByPoint { x, y ->
    transform(gridPoint2D(x, y))
}

inline fun <reified R> MutableIntGrid.mapByPoint(transform: (Int, Int) -> R): MutableGrid<R> = Array(size) { y ->
    val row = this[y]
    Array(row.size) { x -> transform(x, y) }
}

inline fun <reified R> MutableIntGrid.mapByPoint(transform: (GridPoint2D) -> R): MutableGrid<R> = mapByPoint { x, y ->
    transform(gridPoint2D(x, y))
}

inline fun <reified R> MutableIntGrid.flatMapByPoint(transform: (Int, Int) -> R): List<R> = buildList {
    this@flatMapByPoint.forEachIndexed { y, r ->
        r.indices.forEach { x -> add(transform(x, y)) }
    }
}

inline fun <reified R : Comparable<R>> MutableIntGrid.gridMaxOf(transform: (Int, Int) -> R): R {
    getOrNull(GridPoint2D.ZERO) ?: throw NoSuchElementException()
    var max = transform(0, 0)
    forEachIndexed { y, r ->
        r.indices.forEach { x -> max = max.coerceAtLeast(transform(x, y)) }
    }
    return max
}

inline fun <reified R : Comparable<R>> MutableIntGrid.gridMaxOf(transform: (GridPoint2D) -> R): R = gridMaxOf { x, y ->
    transform(gridPoint2D(x, y))
}

inline fun <reified R> MutableIntGrid.flatMapByPoint(transform: (GridPoint2D) -> R): List<R> = flatMapByPoint { x, y ->
    transform(gridPoint2D(x, y))
}

inline fun <T> MutableGrid<T>.anyInGrid(predicate: (T) -> Boolean) = any { it.any(predicate) }

inline fun MutableIntGrid.allInGrid(predicate: (Int) -> Boolean) = all { it.all(predicate) }

inline fun MutableIntGrid.gridCount(predicate: (Int, Int) -> Boolean): Int =
    indices.sumOf { y -> this[y].indices.count { x -> predicate(x, y) } }

inline fun MutableIntGrid.gridCount(predicate: (GridPoint2D) -> Boolean): Int =
    indices.sumOf { y -> this[y].indices.count { x -> predicate(x by y) } }

inline fun <T> MutableGrid<T>.forEachInGrid(action: (T) -> Unit) = forEach { it.forEach(action) }

inline fun <T> MutableGrid<T>.forEachPointAndValue(action: (Int, Int, T) -> Unit) =
    withIndex().forEach { (y, row) -> row.withIndex().forEach { (x, value) -> action(x, y, value) } }

inline fun <T> MutableGrid<T>.forEachPoint(action: (Int, Int) -> Unit) =
    indices.forEach { y -> first().indices.forEach { x -> action(x, y) } }

fun Dimension2D.toMutableCharGrid(transform: (GridPoint2D) -> Char): MutableCharGrid = Array(height) { y ->
    CharArray(width) { x -> transform(gridPoint2D(x, y)) }
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

inline fun MutableIntGrid.forEachPoint(action: (Int, Int) -> Unit) =
    indices.forEach { y -> first().indices.forEach { x -> action(x, y) } }

inline fun MutableIntGrid.forEachPoint(action: (GridPoint2D) -> Unit) =
    indices.forEach { y -> first().indices.forEach { x -> action(gridPoint2D(x, y)) } }

inline fun MutableCharGrid.forEachPoint(action: (Int, Int) -> Unit) =
    indices.forEach { y -> first().indices.forEach { x -> action(x, y) } }

inline fun MutableCharGrid.forEachPoint(action: (GridPoint2D) -> Unit) =
    indices.forEach { y -> first().indices.forEach { x -> action(gridPoint2D(x, y)) } }

inline fun MutableIntGrid.forEachPointAndValue(action: (Int, Int, Int) -> Unit) =
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
 * Dimension2D
 */
fun List<String>.dimension2D(): Dimension2D = getOrNull(0)
    ?.let { dimension2D(width = it.length, height = size) }
    ?: dimension2D(0, 0)

fun MutableCharGrid.dimension2D(): Dimension2D = getOrNull(0)
    ?.let { dimension2D(width = it.size, height = size) }
    ?: dimension2D(0, 0)

/**
 * Getters
 */
operator fun List<String>.get(point: GridPoint2D): Char = this[point.y][point.x]
operator fun MutableCharGrid.get(point: GridPoint2D): Char = this[point.y][point.x]
operator fun MutableIntGrid.get(point: GridPoint2D): Int = this[point.y][point.x]
operator fun MutableBooleanGrid.get(point: GridPoint2D): Boolean = this[point.y][point.x]
operator fun <T> MutableGrid<T>.get(point: GridPoint2D): T = this[point.y][point.x]

fun List<String>.getOrNull(point: GridPoint2D): Char? = getOrNull(point.y)?.getOrNull(point.x)
fun MutableCharGrid.getOrNull(point: GridPoint2D): Char? = getOrNull(point.y)?.getOrNull(point.x)
fun MutableIntGrid.getOrNull(point: GridPoint2D): Int? = getOrNull(point.y)?.getOrNull(point.x)
fun MutableBooleanGrid.getOrNull(point: GridPoint2D): Boolean? = getOrNull(point.y)?.getOrNull(point.x)
fun <T> MutableGrid<T>.getOrNull(point: GridPoint2D): T? = getOrNull(point.y)?.getOrNull(point.x)

/**
 * Setters
 */
operator fun <T> MutableGrid<T>.set(point: GridPoint2D, t: T) {
    this[point.y][point.x] = t
}

operator fun MutableCharGrid.set(point: GridPoint2D, char: Char) {
    this[point.y][point.x] = char
}

operator fun MutableIntGrid.set(point: GridPoint2D, int: Int) {
    this[point.y][point.x] = int
}

operator fun MutableBooleanGrid.set(point: GridPoint2D, bool: Boolean) {
    this[point.y][point.x] = bool
}

/**
 * Edges
 */
val MutableIntGrid.upperLeft get() = GridPoint2D.ZERO
val MutableIntGrid.lowerLeft get() = gridPoint2D(0, lastIndex)
val MutableIntGrid.upperRight get() = gridPoint2D(this[0].lastIndex, 0)
val MutableIntGrid.lowerRight get() = gridPoint2D(this[0].lastIndex, lastIndex)

/**
 * Ranges
 */
operator fun GridPoint2D.rangeTo(other: GridPoint2D): GridPoint2DRange = GridPoint2DRange(
    start = this,
    endInclusive = other,
)

operator fun GridPoint2D.rangeUntil(other: GridPoint2D): OpenEndedGridPoint2DRange = object : OpenEndedGridPoint2DRange {
    override val start: GridPoint2D = this@rangeUntil
    override val endExclusive: GridPoint2D = other
}

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

inline fun <reified T> MutableGrid<T>.rotated(): MutableGrid<T> =
    first().indices.map { col -> indices.reversed().map { row -> this[row][col] }.toTypedArray() }.toTypedArray()

inline fun <reified T> MutableGrid<T>.rotatedCc(): MutableGrid<T> =
    first().indices.reversed().map { col -> indices.map { row -> this[row][col] }.toTypedArray() }.toTypedArray()

fun MutableIntGrid.rotated(): MutableIntGrid = first().indices.map { col ->
    indices.reversed().map { row -> this[row][col] }.toIntArray()
}.toTypedArray()

fun MutableIntGrid.rotatedCc(): MutableIntGrid = first().indices.reversed()
    .map { col -> indices.map { row -> this[row][col] }.toIntArray() }
    .toTypedArray()

fun MutableIntGrid.mirroredHorizontally(): MutableIntGrid = Array(size) { y ->
    val row = this[y]
    IntArray(row.size) { x -> row[row.lastIndex - x] }
}

inline fun <reified T> MutableGrid<T>.mirroredHorizontally(): MutableGrid<T> = Array(size) { y ->
    val row = this[y]
    Array<T>(row.size) { x -> row[row.lastIndex - x] }
}

fun MutableIntGrid.mirroredVertically(): MutableIntGrid = Array(size) { y -> this[lastIndex - y] }
inline fun <reified T> MutableGrid<T>.mirroredVertically(): MutableGrid<T> = Array(size) { y -> this[lastIndex - y] }

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

fun MutableIntGrid.gridAsString(spacing: Int = 2, separator: String = "") =
    map { row -> row.joinToString(separator) { "%${spacing}d".format(it) } }.joinToString("\n") { it }

fun <T> MutableIntGrid.gridAsString(spacing: Int = 2, separator: String = "", selector: (Int) -> T) =
    map { row -> row.joinToString(separator) { "%${spacing}s".format(selector(it)) } }
        .joinToString("\n") { it }

inline fun <T, R> MutableGrid<T>.gridAsString(
    spacing: Int = 2,
    separator: String = "",
    crossinline selector: (T) -> R
) = map { row -> row.joinToString(separator) { "%${spacing}s".format(selector(it)) } }.joinToString("\n") { it }

fun <T> MutableGrid<T>.gridAsString(spacing: Int = 2, separator: String = "") =
    gridAsString(spacing, separator) { it }

@JvmOverloads
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
fun MutableCharGrid.swap(p1x: Int, p1y: Int, p2x: Int, p2y: Int) {
    val tmp = this[p1y][p1x]
    this[p1y][p1x] = this[p2y][p2x]
    this[p2y][p2x] = tmp
    1..<3
}

fun MutableCharGrid.swap(point1: GridPoint2D, point2: GridPoint2D) = swap(point1.x, point1.y, point2.x, point2.y)

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
