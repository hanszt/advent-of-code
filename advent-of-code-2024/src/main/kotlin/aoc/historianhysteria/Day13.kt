package aoc.historianhysteria

import aoc.utils.ChallengeDay
import aoc.utils.parts
import java.nio.file.Path
import kotlin.io.path.readLines
import aoc.utils.grid2d.LongGridPoint2D as P2
import aoc.utils.grid2d.longGridPoint2D as P2

class Day13(private val input: List<String>) : ChallengeDay {
    constructor(path: Path) : this(path.readLines())

    private val machines by lazy {
        input.parts {
            val (a, b, prize) = it
            Machine(
                buttonADiff = buttonRegex.matchEntire(a)?.groupValues?.drop(1)
                    ?.let { (x, y) -> P2(x.toLong(), y.toLong()) } ?: error("Invalid button '$a'"),
                buttonBDiff = buttonRegex.matchEntire(b)?.groupValues?.drop(1)
                    ?.let { (x, y) -> P2(x.toLong(), y.toLong()) } ?: error("Invalid button '$b'"),
                prizeLocation = prizeRegex.matchEntire(prize)?.groupValues?.drop(1)
                    ?.let { (x, y) -> P2(x.toLong(), y.toLong()) } ?: error("!"))
        }
    }

    data class Machine(val buttonADiff: P2, val buttonBDiff: P2, val prizeLocation: P2)

    /**
     * What is the fewest tokens you would have to spend to win all possible prizes?
     */
    override fun part1(): Long = machines.sumOf(::priceOf)

    override fun part2(): Long = machines
        .map { it.copy(prizeLocation = it.prizeLocation + (P2(1, 1) * 10000000000000L)) }
        .sumOf(::priceOf)

    /**
     *  Calculates price by equation.
     *
     *  where:
     *  * __a__ is the number of times _a_ was pushed
     *  * __b__ is the number of times _b_ was pushed
     *  * __x1__ is the move on x-axis by _a_ push
     *  * __y1__ is the move on y-axis by _a_ push
     *  * __x2__ is the move on x-axis by _b_ push
     *  * __y2__ is the move on y-axis by _b_ push
     *  * __px__ is the prize's x coordinate
     *  * __py__ is the prize's y coordinate
     *
     *  ```
     *               ax1+bx2 = px -->
     *                     a = (px-bx2)/x1
     *               ay1+by2 = py --> ((px-bx2)/x1)y1+by2 = py
     * ((px-bx2)/x1)y1+by2 = py
     *
     * (pxy1-bx2y1)/x1+by2 = py
     *  pxy1 - bx2y1 + by2x1 = pyx1
     *          b(y2x1-x2y1) = pyx1 - pxy1
     *
     *  b = (pyx1 - pxy1) / (y2x1 - x2y1)
     *  a = (px-((pyx1-pxy1)/(y2x1-x2y1))*x2)/x1
     *  or
     *  a = (px - bx2) / x1
     * ```
     */
    fun priceOf(m: Machine): Long {
        val (aB, bB, p) = m
        val (x1, y1) = aB
        val (x2, y2) = bB
        val (px, py) = p
        val b = (py * x1 - px * y1) / (y2 * x1 - x2 * y1)
        val a = (px - b * x2) / x1
        val location = P2(a * x1 + b * x2, a * y1 + b * y2)
        return if (location == m.prizeLocation) a * BUTTON_A_COST + b else 0
    }

    internal companion object {
        private const val BUTTON_A_COST = 3L
        val buttonRegex = Regex("""Button [A-B]: X\+(\d+), Y\+(\d+)""")
        val prizeRegex = Regex("""Prize: X=(\d+), Y=(\d+)""")
    }
}
