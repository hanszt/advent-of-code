package aoc.utils.grid2d

import aoc.utils.grid2d.GridPoint2D.Companion.by
import kotlin.math.abs
import kotlin.math.sign

interface LongGridPoint2D {

    val x: Long
    val y: Long

    operator fun component1(): Long = x
    operator fun component2(): Long = y
    operator fun plus(other: LongGridPoint2D) = x + other.x by y + other.y
    operator fun minus(other: LongGridPoint2D) = x - other.x by y - other.y
    operator fun times(factor: Long) = x * factor by y * factor
    operator fun unaryMinus() = longGridPoint2D(-x, -y)
    infix fun dotProduct(other: LongGridPoint2D): Long = x * other.x + y * other.y

    fun add(other: LongGridPoint2D) = plus(other)
    fun add(x: Long, y: Long) = this.x + x by this.y + y
    fun plusX(x: Long) = this.x + x by y
    fun plusY(y: Long) = x by this.y + y
    fun toSignVector() = x.sign by y.sign

    fun rot90L(times: Int): LongGridPoint2D = generateSequence(this) { longGridPoint2D(it.y, -it.x) }
        .take(times)
        .last()

    fun rot90R(times: Int): LongGridPoint2D = generateSequence(this) { longGridPoint2D(-it.y, it.x) }
        .take(times)
        .last()

    fun manhattanDistance(other: LongGridPoint2D) = abs(x - other.x) + abs(y - other.y)

    companion object {
        @JvmField
        val ZERO = 0 by 0

        infix fun Long.by(y: Long): LongGridPoint2D = StandardLongGridPoint2D(this, y)

        @JvmStatic
        fun of(x: Long, y: Long): LongGridPoint2D = x by y
    }
}

fun longGridPoint2D(x: Long, y: Long) = LongGridPoint2D.of(x, y)

private data class StandardLongGridPoint2D(override val x: Long, override val y: Long) : LongGridPoint2D {
    override fun toString(): String = "LongGridPoint2D(x=$x, y=$y)"
}
