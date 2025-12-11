@file:Suppress("unused")

package aoc.utils.grid2d

/**
 * from list of strings to grid converters
 */
fun List<String>.toMutableIntGrid(regex: Regex): Array<IntArray> =
    Array(size) { y -> this[y].trim().split(regex).map(String::toInt).toIntArray() }

fun List<String>.toMutableIntGrid(transform: (Char) -> Int): Array<IntArray> = Array(size) { y ->
    IntArray(this[y].length) { x -> transform(this[y][x]) }
}

fun List<String>.toMutableCharGrid(): Array<CharArray> = Array(size) { this[it].toCharArray() }
fun List<String>.toMutableCharGrid(mapper: (Char) -> Char): Array<CharArray> = Array(size) { y ->
    CharArray(this[y].length) { x -> mapper(this[y][x]) }
}

inline fun <reified T> List<String>.toMutableGrid(regex: Regex, mapper: (String) -> T): Array<Array<T>> =
    map { it.split(regex).map(mapper).toTypedArray() }.toTypedArray()

inline fun <reified T> List<String>.toMutableGrid(mapper: (Char) -> T): Array<Array<T>> =
    map { it.map(mapper).toTypedArray() }.toTypedArray()

/**
 * Grid converters
 */
inline fun <T, reified R> Array<Array<T>>.toMutableGrid(transform: (T) -> R): Array<Array<R>> =
    map { it.map(transform).toTypedArray() }.toTypedArray()

inline fun <reified R> Array<IntArray>.toMutableGrid(transform: (Int) -> R): Array<Array<R>> =
    map { it.map(transform).toTypedArray() }.toTypedArray()

inline fun <reified R> Array<CharArray>.toMutableGrid(transform: (Char) -> R): Array<Array<R>> = Array(size) { y ->
    Array(this[y].size) { x -> transform(this[y][x]) }
}

inline fun Array<IntArray>.toMutableIntGrid(transform: (Int) -> Int): Array<IntArray> =
    map { it.map(transform).toIntArray() }.toTypedArray()

inline fun <reified T> Array<Array<T>>.copyGrid(): Array<Array<T>> = Array(size) { this[it].copyOf() }
fun Array<CharArray>.copyGrid(): Array<CharArray> = Array(size) { this[it].copyOf() }

fun <T, R> Array<Array<T>>.toGrid(transform: (T) -> R): Grid<R> = getOrNull(0)
    ?.let {
        buildGrid(it.size, size) { x, y -> transform(this[y][x]) }
    } ?: emptyGrid()

fun <T, R> Grid<T>.toGrid(transform: (T) -> R): Grid<R> = getOrNull(GridPoint2D.ZERO)
    ?.let {
        buildGrid(width, height) { x, y -> transform(this[x, y]) }
    } ?: emptyGrid()

inline fun <T, reified R> Grid<T>.toMutableGrid(transform: (T) -> R): Array<Array<R>> = Array(height) { y ->
    Array(width) { x -> transform(this[x, y]) }
}

inline fun <reified T> List<String>.toGrid(crossinline mapper: (Char) -> T): Grid<T> = getOrNull(0)?.let {
    buildGrid(it.length, size) { x, y -> mapper(this[y][x]) }
} ?: emptyGrid()

inline fun <reified T> Iterable<GridPoint2D>.toGrid(transform: (Int, Int, Boolean) -> T): Grid<T> =
    toMutableGrid(transform).toGrid { it }

inline fun <reified T> Iterable<GridPoint2D>.toMutableGrid(transform: (Int, Int, Boolean) -> T): Array<Array<T>> {
    val minX = minOf { it.x }
    val maxX = maxOf { it.x }
    val minY = minOf { it.y }
    val maxY = maxOf { it.y }
    val width = (maxX - minX) + 1
    val height = (maxY - minY) + 1
    var array = Array(height) { y ->
        Array(width) { x ->
            transform(x, y, false)
        }
    }
    for (p in this@toMutableGrid) {
        var x = p.x - minX
        var y = p.y - minY
        array[y][x] = transform(x, y, true)
    }
    return array
}

fun Iterable<GridPoint2D>.toClosedGridRange(): GridPoint2DRange {
    val minX = minOf { it.x }
    val maxX = maxOf { it.x }
    val minY = minOf { it.y }
    val maxY = maxOf { it.y }
    return GridPoint2D(minX, minY)..GridPoint2D(maxX, maxY)
}

/**
 * mapping, filtering, actions and matching
 */
fun Array<CharArray>.mapByPoint(transform: (Int, Int) -> Char): Array<CharArray> = Array(size) { y ->
    val row = this[y]
    CharArray(row.size) { x -> transform(x, y) }
}

fun Array<CharArray>.mapByPoint(transform: (GridPoint2D) -> Char): Array<CharArray> = mapByPoint { x, y ->
    transform(GridPoint2D(x, y))
}

inline fun <reified R> Array<IntArray>.mapByPoint(transform: (Int, Int) -> R): Array<Array<R>> = Array(size) { y ->
    val row = this[y]
    Array(row.size) { x -> transform(x, y) }
}

inline fun <reified R> Array<IntArray>.mapByPoint(transform: (GridPoint2D) -> R): Array<Array<R>> = mapByPoint { x, y ->
    transform(GridPoint2D(x, y))
}

inline fun <reified R> Array<IntArray>.flatMapByPoint(transform: (Int, Int) -> R): List<R> = buildList {
    this@flatMapByPoint.forEachIndexed { y, r ->
        r.indices.forEach { x -> add(transform(x, y)) }
    }
}

inline fun <reified R : Comparable<R>> Array<IntArray>.gridMaxOf(transform: (Int, Int) -> R): R {
    getOrNull(GridPoint2D.ZERO) ?: throw NoSuchElementException()
    var max = transform(0, 0)
    forEachIndexed { y, r ->
        r.indices.forEach { x -> max = max.coerceAtLeast(transform(x, y)) }
    }
    return max
}

inline fun <reified R : Comparable<R>> Array<IntArray>.gridMaxOf(transform: (GridPoint2D) -> R): R = gridMaxOf { x, y ->
    transform(GridPoint2D(x, y))
}

inline fun <reified R> Array<IntArray>.flatMapByPoint(transform: (GridPoint2D) -> R): List<R> = flatMapByPoint { x, y ->
    transform(GridPoint2D(x, y))
}

inline fun <T> Grid<T>.anyInGrid(predicate: (T) -> Boolean) = any { it.any(predicate) }

inline fun Array<IntArray>.allInGrid(predicate: (Int) -> Boolean) = all { it.all(predicate) }

inline fun Array<IntArray>.gridCount(predicate: (Int, Int) -> Boolean): Int =
    indices.sumOf { y -> this[y].indices.count { x -> predicate(x, y) } }

inline fun List<String>.gridCount(predicate: (GridPoint2D) -> Boolean): Int =
    indices.sumOf { y -> this[y].indices.count { x -> predicate(GridPoint2D(x, y)) } }

inline fun Array<IntArray>.gridCount(predicate: (GridPoint2D) -> Boolean): Int =
    indices.sumOf { y -> this[y].indices.count { x -> predicate(GridPoint2D(x, y)) } }

inline fun <T> Array<Array<T>>.forEachPointAndValue(action: (Int, Int, T) -> Unit) =
    withIndex().forEach { (y, row) -> row.withIndex().forEach { (x, value) -> action(x, y, value) } }

inline fun <T> Array<Array<T>>.forEachPoint(action: (Int, Int) -> Unit) =
    indices.forEach { y -> first().indices.forEach { x -> action(x, y) } }

inline fun <T> Array<Array<T>>.forEachPoint(action: (GridPoint2D) -> Unit) =
    forEachPoint { x, y -> action(GridPoint2D(x, y)) }

fun Dimension2D.toMutableCharGrid(transform: (Int, Int) -> Char): Array<CharArray> = Array(height) { y ->
    CharArray(width) { x -> transform(x, y) }
}

fun Dimension2D.toMutableCharGrid(c: Char): Array<CharArray> = toMutableCharGrid { _, _ -> c }

fun Dimension2D.toMutableIntGrid(transform: (Int, Int) -> Int): Array<IntArray> = Array(height) { y ->
    IntArray(width) { x -> transform(x, y) }
}

fun Dimension2D.toMutableIntGrid(i: Int): Array<IntArray> = toMutableIntGrid { _, _ -> i }

inline fun List<String>.forEachPoint(action: (Int, Int) -> Unit) =
    withIndex().forEach { (y, row) -> row.indices.forEach { x -> action(x, y) } }

inline fun List<String>.forEachPoint(action: (GridPoint2D) -> Unit) =
    withIndex().forEach { (y, row) -> row.indices.forEach { x -> action(GridPoint2D(x, y)) } }

inline fun <R> List<String>.foldByPoint(initial: R, action: (R, Int, Int) -> R): R {
    var acc = initial
    forEachPoint { x, y -> acc = action(acc, x, y) }
    return acc
}

inline fun <R> List<String>.foldByPoint(initial: R, action: (R, GridPoint2D) -> R): R {
    var acc = initial
    forEachPoint { acc = action(acc, it) }
    return acc
}

inline fun Array<IntArray>.forEachPoint(action: (Int, Int) -> Unit) =
    indices.forEach { y -> first().indices.forEach { x -> action(x, y) } }

inline fun Array<IntArray>.forEachPoint(action: (GridPoint2D) -> Unit) =
    indices.forEach { y -> first().indices.forEach { x -> action(GridPoint2D(x, y)) } }

inline fun Array<CharArray>.forEachPoint(action: (Int, Int) -> Unit) =
    indices.forEach { y -> first().indices.forEach { x -> action(x, y) } }

inline fun Array<CharArray>.forEachPoint(action: (GridPoint2D) -> Unit) =
    indices.forEach { y -> first().indices.forEach { x -> action(GridPoint2D(x, y)) } }

inline fun Array<IntArray>.forEachPointAndValue(action: (Int, Int, Int) -> Unit) =
    withIndex().forEach { (y, row) -> row.withIndex().forEach { (x, value) -> action(x, y, value) } }

inline fun Array<CharArray>.forEachPointAndValue(action: (Int, Int, Char) -> Unit) =
    withIndex().forEach { (y, row) -> row.withIndex().forEach { (x, value) -> action(x, y, value) } }

/**
 * Find the first point matching the predicate
 */
fun List<String>.findPoint(predicate: (Char) -> Boolean): GridPoint2D? {
    for ((y, line) in this.withIndex()) {
        for ((x, c) in line.withIndex()) {
            if (predicate(c)) {
                return GridPoint2D(x, y)
            }
        }
    }
    return null
}

fun Array<CharArray>.findPoint(predicate: (Char) -> Boolean): GridPoint2D? {
    for ((y, line) in this.withIndex()) {
        for ((x, c) in line.withIndex()) {
            if (predicate(c)) {
                return GridPoint2D(x, y)
            }
        }
    }
    return null
}

fun List<String>.firstPoint(predicate: (Char) -> Boolean): GridPoint2D = findPoint(predicate) ?: error("No point found")
fun Array<CharArray>.firstPoint(predicate: (Char) -> Boolean): GridPoint2D =
    findPoint(predicate) ?: error("No point found")

/**
 * Dimension2D
 */
fun List<String>.dimension2D(): Dimension2D = getOrNull(0)
    ?.let { dimension2D(width = it.length, height = size) }
    ?: dimension2D(0, 0)

fun Array<CharArray>.dimension2D(): Dimension2D = getOrNull(0)
    ?.let { dimension2D(width = it.size, height = size) }
    ?: dimension2D(0, 0)

fun Array<IntArray>.dimension2D(): Dimension2D = getOrNull(0)
    ?.let { dimension2D(width = it.size, height = size) }
    ?: dimension2D(0, 0)

fun Array<BooleanArray>.dimension2D(): Dimension2D = getOrNull(0)
    ?.let { dimension2D(width = it.size, height = size) }
    ?: dimension2D(0, 0)

fun <T> Array<Array<T>>.dimension2D(): Dimension2D = getOrNull(0)
    ?.let { dimension2D(width = it.size, height = size) }
    ?: dimension2D(0, 0)

/**
 * Getters
 */
operator fun List<String>.get(point: GridPoint2D): Char = getOrNull(point) ?: dimension2D().pointOutOfBound(point)
operator fun Array<CharArray>.get(point: GridPoint2D): Char = getOrNull(point) ?: dimension2D().pointOutOfBound(point)
operator fun Array<IntArray>.get(point: GridPoint2D): Int = getOrNull(point) ?: dimension2D().pointOutOfBound(point)
operator fun Array<BooleanArray>.get(point: GridPoint2D): Boolean = getOrNull(point) ?: dimension2D().pointOutOfBound(point)
operator fun <T> Array<Array<T>>.get(point: GridPoint2D): T = getOrNull(point) ?: dimension2D().pointOutOfBound(point)

private fun Dimension2D.pointOutOfBound(point: GridPoint2D): Nothing {
    error("Point $point out of bounds for grid dimensions $this")
}

fun List<String>.getOrNull(point: GridPoint2D): Char? = getOrNull(point.y)?.getOrNull(point.x)
fun Array<CharArray>.getOrNull(point: GridPoint2D): Char? = getOrNull(point.y)?.getOrNull(point.x)
fun Array<IntArray>.getOrNull(point: GridPoint2D): Int? = getOrNull(point.y)?.getOrNull(point.x)
fun Array<BooleanArray>.getOrNull(point: GridPoint2D): Boolean? = getOrNull(point.y)?.getOrNull(point.x)
fun <T> Array<Array<T>>.getOrNull(point: GridPoint2D): T? = getOrNull(point.y)?.getOrNull(point.x)

/**
 * Setters
 */
operator fun <T> Array<Array<T>>.set(point: GridPoint2D, t: T) {
    this[point.y][point.x] = t
}

operator fun Array<CharArray>.set(point: GridPoint2D, char: Char) {
    this[point.y][point.x] = char
}

operator fun Array<IntArray>.set(point: GridPoint2D, int: Int) {
    this[point.y][point.x] = int
}

operator fun Array<BooleanArray>.set(point: GridPoint2D, bool: Boolean) {
    this[point.y][point.x] = bool
}

/**
 * Corners
 */
val List<String>.upperLeft get(): GridPoint2D = GridPoint2D.ZERO
val List<String>.lowerLeft get(): GridPoint2D = GridPoint2D(0, lastIndex)
val List<String>.upperRight get(): GridPoint2D = GridPoint2D(this[0].lastIndex, 0)
val List<String>.lowerRight get(): GridPoint2D = GridPoint2D(this[0].lastIndex, lastIndex)

/**
 * Ranges
 */
operator fun GridPoint2D.rangeTo(other: GridPoint2D): GridPoint2DRange = GridPoint2DRange(
    start = this,
    endInclusive = other,
)

operator fun GridPoint2D.rangeUntil(other: GridPoint2D): OpenEndedGridPoint2DRange =
    object : OpenEndedGridPoint2DRange {
        override val start: GridPoint2D = this@rangeUntil
        override val endExclusive: GridPoint2D = other
    }

infix fun IntRange.by(other: IntRange): GridPoint2DRange {
    return GridPoint2DRange(GridPoint2D(start, other.start), GridPoint2D(endInclusive, other.endInclusive))
}

/**
 * Rotation and mirroring
 */
fun List<String>.rotated(): List<String> {
    if (isEmpty()) return emptyList()
    val s = this@rotated
    val m = s[0].length
    val n = s.size
    return List(m) { y ->
        buildString(n) {
            for (x in n - 1 downTo 0) {
                append(s[x][y])
            }
        }
    }
}

fun Array<CharArray>.rotated(): Array<CharArray> {
    if (isEmpty()) return emptyArray()
    val s = this@rotated
    val m = s[0].size
    val n = s.size
    return Array(m) { y ->
        CharArray(n) { x -> s[n - x - 1][y] }
    }
}

fun Array<IntArray>.rotated(): Array<IntArray> {
    if (isEmpty()) return emptyArray()
    val s = this@rotated
    val m = s[0].size
    val n = s.size
    return Array(m) { y ->
        IntArray(n) { x -> s[n - x - 1][y] }
    }
}

fun Array<CharArray>.rotatedCc(): Array<CharArray> {
    if (isEmpty()) return emptyArray()
    val s = this@rotatedCc
    val m = s[0].size
    val n = s.size
    return Array(m) { y ->
        CharArray(n) { x -> s[x][m - y - 1] }
    }
}

fun Array<IntArray>.rotatedCc(): Array<IntArray> {
    if (isEmpty()) return emptyArray()
    val s = this@rotatedCc
    val m = s[0].size
    val n = s.size
    return Array(m) { y ->
        IntArray(n) { x -> s[x][m - y - 1] }
    }
}

fun Array<IntArray>.mirroredHorizontally(): Array<IntArray> = Array(size) { y ->
    val row = this[y]
    IntArray(row.size) { x -> row[row.lastIndex - x] }
}

fun Array<CharArray>.mirroredHorizontally(): Array<CharArray> = Array(size) { y ->
    val row = this[y]
    CharArray(row.size) { x -> row[row.lastIndex - x] }
}

fun Array<IntArray>.mirroredVertically(): Array<IntArray> = Array(size) { y -> this[lastIndex - y] }
fun Array<CharArray>.mirroredVertically(): Array<CharArray> = Array(size) { y -> this[lastIndex - y] }

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

inline fun <T, R> Grid<T>.gridAsString(
    spacing: Int = 2,
    separator: String = "",
    crossinline selector: (T) -> R
) = buildString {
    for (y in 0..<height) {
        for (x in 0..<width) {
            append("%${spacing}s".format(selector(this@gridAsString[x, y])))
            if (x < width - 1) {
                append(separator)
            }
        }
        if (y < height - 1) {
            append("\n")
        }
    }
}

@JvmOverloads
fun <T> Array<Array<T>>.gridAsString(spacing: Int = 2, separator: String = "") = gridAsString(spacing, separator) { it }

@JvmOverloads
fun <T> List<List<T>>.gridAsString(spacing: Int = 2, separator: String = "") = gridAsString(spacing, separator) { it }

@JvmName("stringListAsGrid")
@JvmOverloads
fun List<String>.gridAsString(spacing: Int = 2, separator: String = "") = gridAsString(spacing, separator) { it }

fun <T, R> List<List<T>>.gridAsString(spacing: Int = 2, separator: String = "", selector: (T) -> R) = map { row ->
    row.joinToString(separator) { "%${spacing}s".format(selector(it)) }
}.joinToString("\n") { it }

@JvmName("stringListAsGrid")
fun <T> List<String>.gridAsString(spacing: Int = 2, separator: String = "", selector: (Char) -> T) = map { row ->
    row.asSequence().joinToString(separator) { "%${spacing}s".format(selector(it)) }
}.joinToString("\n") { it }

/**
 * Swapping points in a grid
 */
fun Array<CharArray>.swap(p1x: Int, p1y: Int, p2x: Int, p2y: Int) {
    val tmp = this[p1y][p1x]
    this[p1y][p1x] = this[p2y][p2x]
    this[p2y][p2x] = tmp
}

fun Array<CharArray>.swap(point1: GridPoint2D, point2: GridPoint2D) = swap(point1.x, point1.y, point2.x, point2.y)

interface ClosedGridPoint2DRange {
    val start: GridPoint2D
    val endInclusive: GridPoint2D

    operator fun component1() = start
    operator fun component2() = endInclusive

    operator fun contains(point: GridPoint2D): Boolean {
        return point.x in (if (start.x <= endInclusive.x) start.x..endInclusive.x else endInclusive.x..start.y) &&
                point.y in (if (start.y <= endInclusive.y) start.y..endInclusive.y else endInclusive.y..start.y)
    }
}

interface OpenEndedGridPoint2DRange {
    val start: GridPoint2D
    val endExclusive: GridPoint2D

    operator fun contains(point: GridPoint2D): Boolean {
        if (isEmpty()) return false
        return point.x in (if (start.x < endExclusive.x) start.x..endExclusive.x else endExclusive.x..start.y) &&
                point.y in (if (start.y < endExclusive.y) start.y..endExclusive.y else endExclusive.y..start.y)
    }

    fun isEmpty(): Boolean = start.x == endExclusive.x || start.y == endExclusive.y
}

class GridPoint2DRange internal constructor(
    override val start: GridPoint2D,
    override val endInclusive: GridPoint2D
) : GridPoint2DProgression(start, endInclusive), ClosedGridPoint2DRange

fun Iterable<GridPoint2D>.gridPoint2DRangeOrNull(): GridPoint2DRange? {
    if (none()) return null
    var minx = Integer.MAX_VALUE
    var miny = Integer.MAX_VALUE
    var maxx = Integer.MIN_VALUE
    var maxy = Integer.MIN_VALUE
    for (p in this) {
        minx = minOf(minx, p.x)
        miny = minOf(miny, p.y)
        maxx = maxOf(maxx, p.x)
        maxy = maxOf(maxy, p.y)
    }
    return GridPoint2DRange(GridPoint2D(minx, miny), GridPoint2D(maxx, maxy))
}
