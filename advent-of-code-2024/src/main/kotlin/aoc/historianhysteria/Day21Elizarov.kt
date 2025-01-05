package aoc.historianhysteria

import aoc.utils.grid2d.firstPoint
import kotlin.math.abs
import aoc.utils.grid2d.GridPoint2D as P2

/**
 * https://github.com/elizarov/AdventOfCode2024/blob/main/src/Day21_2.kt
 *
 * [aoc.utils.Tag.RECURSIVE]
 */
internal fun day21Elizarov(input: List<String>, k: Int): Long {
    data class Pad(val a: List<String>) {
        val activate = a.firstPoint { it == 'A' }
        val gap = a.firstPoint { it == ' ' }
    }

    val numPad = Pad(
        listOf(
            "789",
            "456",
            "123",
            " 0A"
        )
    )
    val dirPad = Pad(
        listOf(
            " ^A",
            "<v>"
        )
    )
    val pads = buildList {
        add(numPad)
        repeat(k) { add(dirPad) }
    }
    val cache = List(pads.size) { HashMap<String, Long>() }

    fun find(code: String, p: Int): Long {
        check(code.endsWith('A'))
        if (p == pads.size) return code.length.toLong()
        cache[p][code]?.let { return it }
        val pad = pads[p]
        var len = 0L
        var start = pad.activate
        for (c in code) {
            val other = pad.a.firstPoint { it == c }
            val diff = other - start
            val lr = (if (diff.x > 0) ">" else "<").repeat(abs(diff.x))
            val ud = (if (diff.y > 0) "v" else "^").repeat(abs(diff.y))
            val best = minOf(
                if (P2(start.x, other.y) != pad.gap) find(code = ud + lr + 'A', p = p + 1) else Long.MAX_VALUE,
                if (P2(other.x, start.y) != pad.gap) find(code = lr + ud + 'A', p = p + 1) else Long.MAX_VALUE,
            )
            start = other
            len += best
        }
        cache[p][code] = len
        return len
    }
    return input.sumOf { code -> find(code, 0) * code.dropLast(1).toInt() }
}
