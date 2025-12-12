package aoc.secret_entrance

import aoc.utils.ChallengeDay
import aoc.utils.invoke
import java.nio.file.Path
import kotlin.io.path.readLines

/**
 *
 */
class Day11(private val lines: List<String>) : ChallengeDay {
    private val adjacencyMap = lines.associate { line ->
        line.split(": ").let { it[0] to it[1].split(' ') }
    }

    constructor(path: Path) : this(path.readLines())

    override fun part1(): Int {
        fun countPathsTo(device: String): Int {
            if (device == "out") {
                return 1
            }
            var count = 0
            for (adj in adjacencyMap(device)) {
                count += countPathsTo(adj)
            }
            return count
        }
        return countPathsTo("you")
    }

    /**
     * [Day 11, Part 2](https://github.com/elizarov/AdventOfCode2025/blob/main/src/Day11_2.kt)
     */
    override fun part2(): Long {
        val queue = ArrayDeque<String>().apply { add("svr") }
        val cnt = HashMap<String, LongArray>()
        cnt["svr"] = LongArray(4) { if (it == 0) 1 else 0 }
        while (queue.isNotEmpty()) {
            val u = queue.removeFirst()
            if (u == "out") continue
            val d = cnt.remove(u) ?: continue
            for (v in (adjacencyMap[u] ?: continue)) {
                if (v !in cnt) queue.add(v)
                val vd = cnt.getOrPut(v) { LongArray(4) }
                val bit = when (v) {
                    "fft" -> 1
                    "dac" -> 2
                    else -> 0
                }
                for (k in 0..vd.lastIndex) vd[k or bit] += d[k]
            }
        }
        return cnt("out").last()
    }
}
