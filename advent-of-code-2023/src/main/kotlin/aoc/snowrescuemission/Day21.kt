package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import aoc.utils.grid2d.*
import java.nio.file.Path
import kotlin.io.path.readLines
import kotlin.math.abs

private const val MAX_STEPS_PART_2 = 26501365

class Day21(private val grid: List<String>, private val availableSteps: Int = 64) : ChallengeDay {
    constructor(path: Path) : this(path.readLines())

    /**
     * Solutions Elizarov
     *
     * Tags [aoc.utils.Tag.PATH_SEARCH]
     */
    override fun part1(): Int {
        val q = ArrayDeque<Grid2DNode>()
        val u = Array(grid.size) { IntArray(grid[it].length) { -1 } }
        fun enq(node: Grid2DNode) {
            val p = node.position
            grid.getOrNull(p)?.takeUnless { it == '#' } ?: return
            if (u[p] == node.cost) return
            u[p] = node.cost
            q.add(node)
        }
        enq(Grid2DNode(grid.firstPoint { it == 'S' }))
        while (true) {
            val cur = q.removeFirst()
            if (cur.cost == availableSteps) return q.size + 1
            for (n in cur.position.orthoNeighbors()) {
                enq(Grid2DNode(position = n, cost = cur.cost + 1, prev = cur))
            }
        }
    }

    /**
     * However, the step count the Elf needs is much larger!
     * Starting from the garden plot marked S on your infinite map, how many garden plots could the Elf reach in exactly 26501365 steps?
     *
     * Tags [aoc.utils.Tag.PATH_SEARCH], [aoc.utils.Tag.INFINITY]
     */
    override fun part2(): Long = part2Elizarov(MAX_STEPS_PART_2)

    fun part2Zebalu(maxSteps: Int = MAX_STEPS_PART_2): Long = Day21Zebalu(grid).part2(maxSteps)

    fun part2Elizarov(limitFull: Int): Long {
        val (n, m) = grid.dimension2D()
        val limitRep = limitFull / m
        val limitRem = limitFull.mod(m)
        println("$n x $m")
        println("$limitRep repeats")
        println("$limitRem remainder")
        check(limitRep % 2 == 0)
        check(limitRem * 2 + 1 == m)
        val di = intArrayOf(0, 1, 0, -1)
        val dj = intArrayOf(1, 0, -1, 0)
        var q0 = ArrayDeque<GridPoint2D>()
        var q1 = ArrayDeque<GridPoint2D>()
        val u = HashSet<GridPoint2D>()
        fun enq(i: Int, j: Int) {
            if (grid[i.mod(n)][j.mod(m)] == '#') return
            val p = GridPoint2D(i, j)
            if (p in u) return
            u += p
            q1.add(GridPoint2D(i, j))
        }
        val (i0, j0) = grid.firstPoint { it == 'S' }
        enq(i0, j0)

        fun sumDiamond(ii: Int, jj: Int, sh: Int): Long {
            var sum = 0L
            val i1 = i0 + ii * m + sh * limitRem
            val j1 = j0 + jj * m + sh * limitRem
            when (sh) {
                0 -> for (i in i1 - limitRem..i1 + limitRem + limitRem) {
                    val w = limitRem - abs(i - i1)
                    for (j in j1 - w..j1 + w) if (GridPoint2D(i, j) in u) sum++
                }

                1 -> for (i in i1 - limitRem + 1..i1 + limitRem) {
                    val w = if (i <= i1) limitRem - (i1 - i) else limitRem - (i - i1 - 1)
                    for (j in j1 - w + 1..j1 + w) if (GridPoint2D(i, j) in u) sum++
                }
            }
            return sum
        }

        val rc = Array(2) { LongArray(2) }
        fun computed(dw: Int): Long {
            var sum = 0L
            for (i in -dw..dw) {
                val w = dw - abs(i)
                val t0 = (abs(i) + w + dw) % 2
                sum += rc[t0][0] * (w + 1) + rc[1 - t0][0] * w
            }
            for (i in -dw..dw - 1) {
                val w = if (i < 0) dw + i + 1 else dw - i
                sum += rc[0][1] * w + rc[1][1] * w
            }
            return sum
        }

        var cur = 0
        while (true) {
            if (q0.isEmpty()) {
                q0 = q1.also { q1 = q0 }
                if (cur % m == limitRem) {
                    val rep = cur / m
                    for (sh in 0..1) for (ii in -1..0) {
                        val mod = (ii + rep).mod(2)
                        if (rc[mod][sh] == 0L) rc[mod][sh] = sumDiamond(ii, 0, sh) else /*check(*/ rc[mod][sh] == sumDiamond(ii, 0, sh)/*)*/
                    }
                    val all = q0.size.toLong()
                    val computed = computed(rep)
                    println("repeat $rep: all = $all; computed = $computed")
                    check(all == computed)
                    var td = 0L
                    for (ii in -rep..rep) {
                        for (jj in -rep..rep) {
                            val sd = sumDiamond(ii, jj, 0)
                            td += sd
                            print(sd.toString().padStart(8))
                        }
                        println()
                        print("    ")
                        for (jj in -rep..rep) {
                            val sd = sumDiamond(ii, jj, 1)
                            td += sd
                            print(sd.toString().padStart(8))
                        }
                        println()
                    }
                    println("  td = $td")
                    check(all == td)
                    if (rep == 2) {
                        return computed(limitRep)
                    }
                }
                u.clear()
                cur++
            }
            val (i, j) = q0.removeFirst()
            for (d in 0..3) enq(i + di[d], j + dj[d])
        }
    }
}
