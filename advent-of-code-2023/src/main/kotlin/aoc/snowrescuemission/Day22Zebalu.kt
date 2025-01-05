package aoc.snowrescuemission

import aoc.utils.grid3d.GridPoint3D
import aoc.utils.invoke
import kotlin.collections.minBy
import aoc.utils.grid3d.GridPoint3D as P3

/**
 * Source: <a href="https://github.com/zebalu/advent-of-code-2023.git">Zebalu aoc repo</a>
 *
 * [aoc.utils.Tag.THREE_D]
 */
class Day22Zebalu(input: List<String>) {

    private val network = createNetwork(input)

    fun part1(): Int = countDisintegrateable()
    fun part2(): Int = countDisintegration()

    private fun countDisintegrateable(): Int = network.values.count { bs ->
        bs.supports.isEmpty() || bs.supports.all { network(it).supportedBy.size > 1 }
    }

    private fun countDisintegration(): Int = network.keys.sumOf(::countDisintegrationFrom)

    private fun createNetwork(input: List<String>): Map<Brick, BrickSupport> = makeThemFall(
        input.asSequence()
            .map(Brick::parse)
            .sortedWith(compareBy<Brick> { it.z1 }.then(compareByDescending { it.z2 }))
            .toList()
    )

    private fun makeThemFall(zSorted: List<Brick>): Map<Brick, BrickSupport> = buildMap {
        val stopped = HashMap<P3, Brick>()
        zSorted.forEach { this.put(it, BrickSupport(it)) }
        zSorted.forEach { brick ->
            val aboveThese = brick.findAbove(zSorted)
            if (aboveThese.isEmpty()) {
                brick.fallN(brick.z1)
            } else {
                brick.findClosestBelow(aboveThese)
                    ?.takeIf { brick.z1 - it.z2 > 1 }
                    ?.let { closest -> brick.fallN(brick.z1 - closest.z2 - 1) }
            }
            val standingOn = HashSet<Brick>()
            val allPoints = brick.allPoints()
            allPoints.map { it.below() }.forEach { stopped[it]?.let(standingOn::add) }
            allPoints.forEach { stopped.put(it, brick) }
            standingOn.forEach {
                this(it).supports.add(brick)
                this(brick).supportedBy.add(it)
            }
        }
    }

    private fun countDisintegrationFrom(brick: Brick): Int {
        val disintegrated = HashSet<Brick>()
        val toRemove = ArrayList<Brick>()
        disintegrated.add(brick)
        toRemove.add(brick)
        while (toRemove.isNotEmpty()) {
            val toDelete = toRemove.removeFirst()
            network(toDelete).supports.forEach { b ->
                val support = network(b)
                val remainingSupport = support.supportedBy.count { it !in disintegrated }
                if (remainingSupport < 1) {
                    toRemove.add(b)
                    disintegrated.add(b)
                }
            }
        }
        return disintegrated.size - 1
    }

    private fun P3.below(): P3 = GridPoint3D(x, y, z - 1)

    private class Brick(var x1: Int, var y1: Int, var z1: Int, var x2: Int, var y2: Int, var z2: Int) {

        companion object {
            fun parse(line: String): Brick {
                val (s, e) = line.split('~').map {
                    it.split(',').map(String::toInt).let { (x, y, z) -> GridPoint3D(x, y, z) }
                }
                return Brick(x1 = s.x, y1 = s.y, z1 = s.z, x2 = e.x, y2 = e.y, z2 = e.z)
            }
        }

        fun fallN(n: Int) {
            z1 -= n
            z2 -= n
        }

        fun findClosestBelow(bricks: List<Brick>): Brick? = findAbove(bricks).minBy { z1 - it.z2 }
        fun findAbove(bricks: List<Brick>): List<Brick> = bricks.filter(::isAbove)

        fun isAbove(other: Brick): Boolean = other.z2 < z1 && hasCommonX(other) && hasCommonY(other)

        fun hasCommonX(other: Brick): Boolean {
            val a = if (x1 <= other.x1) this else other
            val b = if (this === a) other else this
            return a.x1 <= b.x1 && b.x1 <= a.x2
        }

        fun hasCommonY(other: Brick): Boolean {
            val a = if (y1 <= other.y1) this else other
            val b = if (this === a) other else this
            return a.y1 <= b.y1 && b.y1 <= a.y2
        }

        fun allPoints(): List<P3> = buildList {
            for (x in x1..x2) {
                for (y in y1..y2) {
                    for (z in z1..z2) {
                        add(GridPoint3D(x, y, z))
                    }
                }
            }
        }
    }

    @JvmRecord
    private data class BrickSupport(
        val brick: Brick,
        val supportedBy: MutableSet<Brick> = LinkedHashSet(),
        val supports: MutableSet<Brick> = LinkedHashSet()
    )
}
