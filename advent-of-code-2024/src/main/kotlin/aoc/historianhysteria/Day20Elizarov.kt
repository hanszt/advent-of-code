package aoc.historianhysteria

import aoc.utils.grid2d.*
import kotlin.math.abs
import aoc.utils.grid2d.gridPoint2D as P2

fun day20Part1(input: List<String>): Int = solve(input, 2)
fun day20Part2(input: List<String>): Int = solve(input, 20)

private const val picoSecondsSaveTarget = 100

private fun solve(input: List<String>, nrCheatPs: Int): Int {
    val d = input.dimension2D()
    val start = input.firstPoint { it == 'S' }
    val goal = input.firstPoint { it == 'E' }
    val ds = floodFill(d, start) { input[it.position] != '#' }.toGrid(d)
    val de = floodFill(d, goal) { input[it.position] != '#' }.toGrid(d)
    val d0 = ds[goal]
    check(de[start].cost == d0.cost)
    var cnt = 0
    input.forEachPoint {
        for (dx in -nrCheatPs..nrCheatPs) for (dy in -nrCheatPs..nrCheatPs) {
            val r = abs(dx) + abs(dy)
            if (r <= nrCheatPs) {
                val np = it + P2(dx, dy)
                val dn = ds.getOrNull(np) ?: continue
                if (dn.cost + de[it].cost + r <= d0.cost - picoSecondsSaveTarget) {
                    cnt++
                }
            }
        }
    }
    return cnt
}
