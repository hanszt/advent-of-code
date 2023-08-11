package aoc.jungle.adventures

import aoc.utils.ChallengeDay
import aoc.utils.groupingByKey
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

    /**
     * @return the most pressure that can be released
     */
    override fun part1(): Int {
        val targetMinute = 30
        val valveMap = parseInput()
        val graph = valveMap.buildGraph(targetMinute)
        return graph[targetMinute].map(Map.Entry<Tunnel, Int>::value).max()
    }

    /**
     * @return the most pressure that can be released
     */
    override fun part2(): Int = part2(26)

    internal fun part2(targetMinute: Int): Int {
        val valveMap = parseInput()
        val graph = valveMap.buildGraph(targetMinute)
        val distancesToPressures = graph[targetMinute]
            .groupingByKey(Tunnel::distance)
            .fold(initialValue = 0) { maxPressure, (_, pressure) -> maxOf(maxPressure, pressure) }

        val activeFlowValveIds = valveMap.values.filter { it.flowRate > 0 }.map(Valve::id)

        fun find(activeFlowCount: Int, distance1: Long, distance2: Long): Int {
            if (activeFlowCount == activeFlowValveIds.size) {
                val pressure1 = distancesToPressures[distance1] ?: return 0
                val pressure2 = distancesToPressures[distance2] ?: return 0
                return pressure1 + pressure2
            }
            val curValveId = activeFlowValveIds[activeFlowCount]
            val p1 = find(activeFlowCount + 1, distance1, distance2)
            val p2 = find(activeFlowCount + 1, distance1 = distance1 or (1L shl curValveId), distance2)
            val p3 = find(activeFlowCount + 1, distance1, distance2 = distance2 or (1L shl curValveId))
            return maxOf(p1, p2, p3)
        }
        return find(activeFlowCount = 0, distance1 = 0, distance2 = 0)
    }

    private fun Map<String, Valve>.buildGraph(totalMinutes: Int): Array<MutableMap<Tunnel, Int>> {
        val graph = Array(totalMinutes + 1) { mutableMapOf<Tunnel, Int>() }
        graph.put(0, 0, "AA", 0)
        for (minute in 0 until totalMinutes) {
            for ((tunnel, pressure) in graph[minute]) {
                val (valveMask, valveName) = tunnel
                val valve = this[valveName] ?: error("No valve with name: $valveName")
                val mask = 1L shl valve.id
                if (valve.flowRate > 0 && (mask and valveMask) == 0L) {
                    graph.put(
                        minute = minute + 1,
                        distance = valveMask or mask,
                        valveName = valveName,
                        pressure = pressure + (totalMinutes - minute - 1) * valve.flowRate
                    )
                }
                for (nextValveName in valve.connectingValveNames) {
                    graph.put(minute = minute + 1, distance = valveMask, valveName = nextValveName, pressure = pressure)
                }
            }
        }
        return graph
    }

    private fun parseInput(): Map<String, Valve> {
        val regex = Regex("Valve ([A-Z][A-Z]) has flow rate=(\\d+); tunnels? leads? to valves? ([A-Z, ]+)")
        val valves = HashMap<String, Valve>()
        for ((index, line) in input.withIndex()) {
            val (name, flowRate, connectingValves) = regex.matchEntire(line)
                ?.groupValues
                ?.drop(1) ?: error("could not parse '$line'")
            val valve = valves.put(name, Valve(
                id = index,
                flowRate = flowRate.toInt(),
                connectingValveNames = connectingValves.split(", ")
            ))
            check(valve == null)
        }
        return valves
    }

    private fun Array<MutableMap<Tunnel, Int>>.put(minute: Int, distance: Long, valveName: String, pressure: Int) {
        val tunnel = Tunnel(distance, valveName)
        val cur = this[minute][tunnel]
        if (cur == null || pressure > cur) this[minute][tunnel] = pressure
    }

    private class Valve(val id: Int, val flowRate: Int, val connectingValveNames: Collection<String>)
    private data class Tunnel(val distance: Long, val valveName: String)
}
