package aoc.jungle.adventures

import aoc.utils.ChallengeDay
import java.io.File

/**
 * All credits to Elizarov. Very educational
 *
 * It is an adaption of the traveling salesman problem (NP problem)
 *
 * Figure out the path of most resistance
 *
 * @see <a href="https://adventofcode.com/2022/day/16">Day 16</a>
 */
class Day16ProboscideaVolcanium(fileName: String) : ChallengeDay {

    private val input = File(fileName).readLines()

    override fun part1(): Int {
        val targetMinute = 30
        val valveMap = valveMap()
        val graph = graph(targetMinute, valveMap)
        return graph[targetMinute].map(Map.Entry<Tunnel, Int>::value).max()
    }

    override fun part2(): Int = part2(26)

    fun part2(targetMinute: Int): Int {
        val valveMap = valveMap()
        val pressures = graph(targetMinute, valveMap)

        val tunnelsAndPressures = pressures[targetMinute].toList()
            .groupingBy { it.first.distance }
            .fold(0) { p, (_, pressure) -> maxOf(p, pressure) }

        val valveIds = valveMap.values.filter { it.flowRate > 0 }.map(Valve::id)

        fun find(i: Int, distance1: Long, distance2: Long): Int {
            if (i == valveIds.size) {
                val b1 = tunnelsAndPressures[distance1] ?: return 0
                val b2 = tunnelsAndPressures[distance2] ?: return 0
                return b1 + b2
            }
            val r1 = find(i + 1, distance1, distance2)
            val r2 = find(i + 1, distance1 or (1L timesTwo valveIds[i]), distance2)
            val r3 = find(i + 1, distance1, distance2 or (1L timesTwo valveIds[i]))
            return maxOf(r1, r2, r3)
        }
        return find(0, 0, 0)
    }

    private fun graph(totalMinutes: Int, valveMap: HashMap<String, Valve>): Array<HashMap<Tunnel, Int>> {
        val graph = Array(totalMinutes + 1) { HashMap<Tunnel, Int>() }
        graph.put(0, 0, "AA", 0)
        for (minute in 0 until totalMinutes) {
            for ((tunnel, pressure) in graph[minute]) {
                val (valveMask, valveName) = tunnel
                val valve = valveMap[valveName]!!
                val mask = 1L timesTwo valve.id
                if (valve.flowRate > 0 && (mask and valveMask) == 0L) {
                    graph.put(minute + 1, valveMask or mask, valveName, pressure + (totalMinutes - minute - 1) * valve.flowRate)
                }
                for (nextValve in valve.connectingValves) {
                    graph.put(minute + 1, valveMask, nextValve, pressure)
                }
            }
        }
        return graph
    }

    private fun valveMap(): HashMap<String, Valve> {
        var id = 0
        val valves = HashMap<String, Valve>()
        for (line in input) {
            val (name, flowRate, connectingValves) = Regex("Valve ([A-Z][A-Z]) has flow rate=(\\d+); tunnels? leads? to valves? ([A-Z, ]+)")
                .matchEntire(line)!!.groupValues
                .drop(1)
            val valve = valves.put(name, Valve(id++, flowRate.toInt(), connectingValves.split(", ")))
            check(valve == null)
        }
        return valves
    }
    private infix fun Long.timesTwo(bitCount: Int): Long = this shl bitCount

    private fun Array<HashMap<Tunnel, Int>>.put(minute: Int, distance: Long, valve: String, pressure: Int) {
        val tunnel = Tunnel(distance, valve)
        val cur = this[minute][tunnel]
        if (cur == null || pressure > cur) this[minute][tunnel] = pressure
    }

    private class Valve(val id: Int, val flowRate: Int, val connectingValves: List<String>)
    private data class Tunnel(val distance: Long, val valveName: String)
}
