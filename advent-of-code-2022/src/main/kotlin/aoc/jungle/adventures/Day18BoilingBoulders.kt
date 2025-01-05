package aoc.jungle.adventures

import aoc.utils.ChallengeDay
import aoc.utils.grid3d.GridPoint3D
import aoc.utils.grid3d.rangeTo
import java.io.File
import aoc.utils.grid3d.GridPoint3D as Cube
import aoc.utils.grid3d.GridPoint3D as cube

/**
 * Credits to Elizarov and Angelo
 *
 * @see <a href="https://adventofcode.com/2022/day/18">Day 18</a>
 *
 * [aoc.utils.Tag.THREE_D]
 */
class Day18BoilingBoulders(fileName: String) : ChallengeDay {

    private val cubes = File(fileName).useLines {
        it.map { line -> line.splitToSequence(",").map(String::toInt).toList() }
            .map { (x, y, z) -> cube(x, y, z) }
            .toList()
    }

    /**
     * What is the surface area of your scanned lava droplet?
     */
    override fun part1(): Int = calcSurfaceArea(cubes)

    /**
     * What is the exterior surface area of your scanned lava droplet?
     */
    override fun part2(): Int = part2ElizarovRefactored()

    private fun calcSurfaceArea(cubes: List<Cube>): Int {
        var touchingSides = 0
        for (i in cubes.indices) {
            for (j in i + 1..<cubes.size) {
                touchingSides += if (cubes[i].manhattanDistance(cubes[j]) == 1) 2 else 0
            }
        }
        val allSides = cubes.size * 6
        return allSides - touchingSides
    }

    /**
     * [Source](https://github.com/elizarov/AdventOfCode2022/blob/main/src/Day18.kt)
     */
    private fun part2ElizarovRefactored(): Int {
        val cubes = HashSet(cubes)

        val minPoint = cube(cubes.minOf(Cube::x), cubes.minOf(Cube::y), cubes.minOf(Cube::z))
        val maxPoint = cube(cubes.maxOf(Cube::x), cubes.maxOf(Cube::y), cubes.maxOf(Cube::z))

        val cubesCoveredMap = HashMap<Cube, Boolean>()

        val visited = HashSet<Cube>()

        fun isPartOfSurfaceArea(cube: Cube): Boolean {
            cubesCoveredMap[cube]?.let { return it }
            if (cube !in minPoint..maxPoint) {
                return true
            }
            cubesCoveredMap[cube] = false
            visited.add(cube)
            for (dir in Cube.orthoDirs) {
                val neighbor = cube + dir
                if (neighbor in cubes) continue
                if (isPartOfSurfaceArea(neighbor)) {
                    return true
                }
            }
            return false
        }

        var area = 0
        for (cube in cubes) {
            for (dir in GridPoint3D.orthoDirs) {
                val neighbor = cube + dir
                if (neighbor in cubes) continue
                visited.clear()
                if (isPartOfSurfaceArea(neighbor)) {
                    for (cubeCovered in visited) {
                        cubesCoveredMap[cubeCovered] = true
                    }
                    area++
                }
            }
        }
        return area
    }
}
