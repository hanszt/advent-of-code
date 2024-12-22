package aoc.historianhysteria

import aoc.utils.grid2d.get
import aoc.utils.grid2d.Dimension2D
import aoc.utils.grid2d.GridPoint2D
import aoc.utils.grid2d.mod
import aoc.utils.grid2d.set

/**
 * https://github.com/elizarov/AdventOfCode2024/blob/main/src/Day14_2.kt
 *
 * The idea is to, for each iteration look at the robot positions and see how many neighbor positions ara also occupied by a robot.
 *
 * The iteration where most robots have neighbors has the highest chance of forming the Christmas tree and therefore is the best option.
 */
internal fun elizarovDay14Part2(robots: List<Robot>, searchRange: IntRange, dimension: Dimension2D): Int {
    val nrOfRobots = Array(dimension.height) { IntArray(dimension.width) }
    var best = 0
    var bestK = -1
    val q = ArrayDeque<GridPoint2D>()

    /**
     * Store only if the nr of robots at a point is greater than 0
     */
    fun enq(point: GridPoint2D) {
        if (point !in dimension) return
        if (nrOfRobots[point] <= 0) return
        nrOfRobots[point] = -1
        q += point
    }
    for (k in searchRange) {
        val robotPositions = robots.map { (pos, vel) ->
            (pos + vel * k).mod(dimension)
        }
        for (p in robotPositions) nrOfRobots[p]++
        dimension.gridPoints().forEach { point ->
            if (nrOfRobots[point] > 0) {
                q.clear()
                var h = 0
                enq(point)
                /**
                 * Not using a regular while q.isNotEmpty, then q.poll() approach because we need to see how many items where enqueued in total.
                 */
                while (h < q.size) {
                    val p = q[h++]
                    /**
                     * Look at all neighbors
                     */
                    GridPoint2D.kingDirs.forEach { enq(p + it) }
                }
                val size = q.size
                if (size > best) {
                    best = size
                    bestK = k
                }
            }
        }
        // reset positions for next iteration
        for (point in robotPositions) nrOfRobots[point] = 0
    }
    return bestK
}