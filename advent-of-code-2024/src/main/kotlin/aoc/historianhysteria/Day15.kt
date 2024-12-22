package aoc.historianhysteria

import aoc.utils.ChallengeDay
import aoc.utils.grid2d.*
import aoc.utils.splitByBlankLine
import java.nio.file.Path
import kotlin.io.path.readText
import aoc.utils.grid2d.gridPoint2D as P2

class Day15(input: String) : ChallengeDay {

    private val map: List<String>
    private val moves: List<String>

    init {
        val (map, instructions) = input.splitByBlankLine()
        this.map = map.lines()
        this.moves = instructions.lines()
    }

    constructor(path: Path) : this(path.readText())

    /**
     * what is the sum of all boxes' GPS coordinates?
     */
    override fun part1(): Long = p1Elizarov()

    /**
     * What is the sum of all boxes' final GPS coordinates?
     */
    override fun part2(): Long = p2Elizarov()

    fun p1Elizarov(): Long {
        val a = map.toMutableCharGrid()
        var r = map.firstPoint { it == '@' }
        a[r] = '.'
        for (line in moves) for (c in line) {
            var d = toDirection(c)
            var k = 1
            while (a[r + d * k] == 'O') k++
            when (a[r + d * k]) {
                '#' -> continue
                '.' -> {
                    if (k > 1) {
                        a[r + d * k] = 'O'
                        a[r + d] = '.'
                    }
                    r += d
                }

                else -> error("!!!")
            }
        }
        var sum = 0L
        a.forEachPointAndValue { x, y, c -> if (c == 'O') sum += 100 * y + x }
        return sum
    }

    fun p2Elizarov(): Long {
        val a = modifyMap(map)
        var r = a.firstPoint { it == '@' }
        a[r] = '.'
        for (line in moves) for (c in line) {
            var d = toDirection(c)
            var k = 1
            if (d.y == 0) {
                while (a[r.y][r.x + d.x * k] in setOf('[', ']')) k++
                when (a[r.y][r.x + d.x * k]) {
                    '#' -> continue
                    '.' -> {
                        if (k > 1) {
                            check(k % 2 == 1)
                            for (t in 2..k) {
                                a[r.y][r.x + d.x * t] = if ((t % 2 == 0) == (d.x < 0)) ']' else '['
                            }
                            a[r + d] = '.'
                        }
                        r += d
                    }

                    else -> error("!!!")
                }
            } else {
                val ms = HashMap<Int, HashSet<Int>>()
                ms[r.y] = HashSet(setOf(r.x))
                var cy = r.y
                var blocked = false
                while (!blocked && cy in ms) {
                    for (x in ms[cy]!!) {
                        when (a[cy + d.y][x]) {
                            '[' -> ms.getOrPut(cy + d.y) { HashSet() }.addAll(setOf(x, x + 1))
                            ']' -> ms.getOrPut(cy + d.y) { HashSet() }.addAll(setOf(x - 1, x))
                            '#' -> {
                                blocked = true; break
                            }

                            '.' -> {}
                            else -> error("!!!")
                        }
                    }
                    cy += d.y
                }
                if (!blocked) {
                    cy -= d.y
                    while (cy in ms) {
                        for (x in ms[cy]!!) {
                            a[cy + d.y][x] = a[cy][x]
                            a[cy][x] = '.'
                        }
                        cy -= d.y
                    }
                    r += P2(0, d.y)
                }
            }
        }
        var sum = 0L
        a.forEachPointAndValue { i, j, c -> if (c == '[') sum += 100 * j + i }
        return sum
    }

    private fun modifyMap(map: List<String>): Array<CharArray> = map.map {
        it.map {
            when (it) {
                '#' -> "##"
                'O' -> "[]"
                '.' -> ".."
                '@' -> "@."
                else -> error("!$it")
            }
        }.joinToString("")
    }.toMutableCharGrid()

    private fun toDirection(c: Char): GridPoint2D = when (c) {
        '<' -> P2(-1, 0)
        'v' -> P2(0, 1)
        '>' -> P2(1, 0)
        '^' -> P2(0, -1)
        else -> error("!$c")
    }
}
