package aoc.historianhysteria

import aoc.utils.TextColor
import aoc.utils.grid2d.*
import aoc.utils.invoke
import aoc.utils.withColor
import kotlin.math.abs
import aoc.utils.grid2d.GridPoint2D as P2

class Day20Elizarov(private val picoSecondsSaveTarget: Int = 100) {
    fun day20Part1(input: List<String>): Int = solve(input, 2, printGridAsString = true)
    fun day20Part2(input: List<String>): Int = solve(input, 20)

    private fun solve(input: List<String>, nrCheatPs: Int, printGridAsString: Boolean = false): Int {
        val d = input.dimension2D()
        val start = input.firstPoint { it == 'S' }
        val goal = input.firstPoint { it == 'E' }
        val inf = Int.MAX_VALUE / 4
        val ds = floodFill(d, start, inf) { input[it.position] != '#' }
        val de = floodFill(d, goal, inf) { input[it.position] != '#' }
        val gn = ds(goal)
        val sn = de(start)
        if (printGridAsString) println(gridAsString(input, gn, sn))
        check(de(start).cost == gn.cost)
        var cnt = 0
        input.forEachPoint {
            for (dx in -nrCheatPs..nrCheatPs) for (dy in -nrCheatPs..nrCheatPs) {
                val r = abs(dx) + abs(dy)
                if (r <= nrCheatPs) {
                    val np = it + P2(dx, dy)
                    val dn = ds[np] ?: continue
                    if (dn.cost + (de[it]?.cost ?: inf) + r <= gn.cost - picoSecondsSaveTarget) {
                        cnt++
                    }
                }
            }
        }
        return cnt
    }
}

private fun gridAsString(
    input: List<String>,
    startNode: Grid2DNode,
    goalNode: Grid2DNode
): String {
    val grid = input.toMutableGrid { " ".repeat(4) + " " }
    val sTrace = startNode.traceBack { it.position to it.cost }
    val eTrace = goalNode.traceBack { it.position to it.cost }.toList()
    val max = sTrace.first().second.toDouble()
    sTrace.zip(eTrace.reversed().asSequence()).forEach { (a, b) ->
        val (ps, cs) = a
        val (pe, _) = b
        check(ps == pe)
        val cost = cs
        val hue = ((cost / (max)) * 1080).toInt()
        val ch = input[ps]
        val cc = if (ch == '.') "%04d ".format(cost) else "$ch".repeat(4) + " "
        grid[ps] = cc.withColor(TextColor.hsb(hue))
    }
    return grid.gridAsString()
}
