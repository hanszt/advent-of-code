package aoc.snowrescuemission

import aoc.utils.Heap
import aoc.utils.graph.Node
import aoc.utils.grid2d.GridPoint2D
import aoc.utils.grid2d.GridPoint2D.Companion.towerDirs
import aoc.utils.grid2d.dimension2D
import aoc.utils.grid2d.getOrNull

/**
 * https://github.com/elizarov/AdventOfCode2023/blob/main/src/Day17_1.kt
 */
fun day17Part1(input: List<String>): Int {
    val maxStraightLength = 3

    val heap = Heap<Crucible, Int>()
    val visited = HashSet<Crucible>()
    fun enq(pos: GridPoint2D, dir: Int, straightLength: Int, heatLoss: Int, prev: Crucible? = null) {
        val crucible = Crucible(pos, dir, straightLength, heatLoss, prev)
        if (crucible in visited) return
        heap.putBetter(crucible, heatLoss)
    }
    enq(GridPoint2D.ZERO, 0, 0, 0)
    val d = input.dimension2D()
    while (heap.isNotEmpty()) {
        val (cur, minHeatLoss) = heap.removeMin()
        val p = cur.pos
        visited += cur
        if (p == d.endExclusive) {
            return minHeatLoss
        }
        for (dir in -1..1) {
            if (dir == 0 && cur.straightLength == maxStraightLength) continue
            val nextDir = (cur.dir + dir).mod(towerDirs.size)
            val np = p + towerDirs[nextDir]
            input.getOrNull(np)?.let {
                enq(
                    pos = np,
                    dir = nextDir,
                    straightLength = if (dir == 0) cur.straightLength + 1 else 1,
                    heatLoss = minHeatLoss + (it - '0'),
                    prev = cur
                )
            }
        }
    }
    error("No solution found")
}

/**
 * https://github.com/elizarov/AdventOfCode2023/blob/main/src/Day17_2.kt
 *
 * Not bug free
 *
 * The stopping distance of 4 is not taken into account yet
 */
fun day17Part2(input: List<String>): Int {
    val d = input.dimension2D()

    val heap = Heap<Crucible, Int>()
    val visited = HashSet<Crucible>()
    fun enq(pos: GridPoint2D, dir: Int, straightLength: Int, heatLoss: Int, prev: Crucible? = null) {
        val c = Crucible(pos, dir, straightLength, heatLoss, prev)
        if (c in visited) return
        heap.put(c, heatLoss)
    }
    enq(GridPoint2D.ZERO, 0, 0, 0)
    while (heap.isNotEmpty()) {
        val (cur, minHeatLoss) = heap.removeMin()
        visited += cur
        var p = cur.pos
        if (p == d.endExclusive) {
            return minHeatLoss
        }
        for (dir in -1..1) {
            // TODO: also account for the stopping distance of 4
            if (dir == 0) {
                if (cur.straightLength == 10) continue
            } else if (cur.straightLength < 4) continue
            val nextDir = (cur.dir + dir).mod(towerDirs.size)
            val np = p + towerDirs[nextDir]
            input.getOrNull(np)?.let {
                enq(
                    pos = np,
                    dir = nextDir,
                    straightLength = if (dir == 0) cur.straightLength + 1 else 1,
                    heatLoss = minHeatLoss + (it - '0'),
                    prev = cur
                )
            }
        }
    }
    error("No solution found")
}

data class Crucible(
    val pos: GridPoint2D,
    val dir: Int = 0, // 0 east, 1 south, 2 west, 3 north
    val straightLength: Int = 0,
    val heatLoss: Int = 0,
    override val prev: Crucible? = null
) : Node<Crucible> {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Crucible) return false

        if (pos != other.pos) return false
        if (dir != other.dir) return false
        if (straightLength != other.straightLength) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pos.hashCode()
        result = 31 * result + dir
        result = 31 * result + straightLength
        return result
    }

    override fun toString(): String = "Crucible(pos=$pos, dir=$dir, straightLength=$straightLength)"
}

