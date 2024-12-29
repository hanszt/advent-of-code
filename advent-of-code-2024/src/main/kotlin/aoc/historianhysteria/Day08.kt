package aoc.historianhysteria

import aoc.utils.ChallengeDay
import aoc.utils.grid2d.forEachPointAndValue
import aoc.utils.grid2d.getOrNull
import aoc.utils.grid2d.set
import aoc.utils.grid2d.toMutableCharGrid
import java.nio.file.Path
import kotlin.io.path.readLines
import aoc.utils.grid2d.GridPoint2D as P2
import aoc.utils.grid2d.gridPoint2D as P2

/**
 * How many unique locations within the bounds of the map contain an antinode?
 *
 * Solution from Elizarov
 */
class Day08(private val cityMap: List<String>) : ChallengeDay {

    constructor(text: String) : this(text.lines())
    constructor(path: Path) : this(path.readLines())

    override fun part1(): Int {
        val a = cityMap.toMutableCharGrid()
        val map = HashMap<Char, MutableList<P2>>()
        a.forEachPointAndValue { x, y, c ->
            if (c != '.') {
                map.getOrPut(c, ::ArrayList).add(P2(x, y))
            }
        }
        var ans = 0
        fun node(p2: P2) {
            a.getOrNull(p2)?.takeIf { it != '#' } ?: return
            a[p2] = '#'
            ans++
        }
        for (l in map.values) for (p in 0..<l.size) for (q in p + 1..<l.size) {
            val p1 = l[p]
            val p2 = l[q]
            node(p1 * 2 - p2)
            node(p2 * 2 - p1)
        }
        return ans
    }

    override fun part2(): Int {
        val a = cityMap.toMutableCharGrid()
        val map = HashMap<Char, MutableList<P2>>()
        a.forEachPointAndValue { x, y, c ->
            if (c != '.') {
                map.getOrPut(c, ::ArrayList).add(P2(x, y))
            }
        }
        var ans = 0
        for (l in map.values) for (p in 0..<l.size) for (q in 0..<l.size) if (p != q) {
            val p1 = l[p]
            val p2 = l[q]
            val d = p1 - p2
            var k = 0
            while (true) {
                val pp = p1 + d * k
                var ch = a.getOrNull(pp) ?: break
                if (ch != '#') {
                    a[pp] = '#'
                    ans++
                }
                k++
            }
        }
        return ans
    }

}
