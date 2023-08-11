package aoc.jungle.adventures

import aoc.utils.ChallengeDay
import aoc.utils.model.GridPoint3D
import java.io.File
import aoc.utils.model.GridPoint3D as Cube
import aoc.utils.model.gridPoint3D as cube

/**
 * Credits to Elizarov and Angelo
 *
 * @see <a href="https://adventofcode.com/2022/day/18">Day 18</a>
 */
class Day18BoilingBoulders(fileName: String) : ChallengeDay {

    private val cubes = File(fileName).useLines {
        it.map { line -> line.split(",").map(String::toInt) }
            .map { (x, y, z) -> cube(x, y, z) }
            .toList()
    }

    override fun part1(): Int = calcSurfaceArea(cubes)
    override fun part2(): Int = part2ElizarovRefactored()

    private fun calcSurfaceArea(cubes: List<Cube>): Int {
        var touchingSides = 0
        for (i in 0 ..< cubes.lastIndex) {
            for (j in i + 1 ..< cubes.size) {
                touchingSides += if (cubes[i].manhattanDistance(cubes[j]) == 1) 2 else 0
            }
        }
        val allSides = cubes.size * 6
        return allSides - touchingSides
    }

    private fun part2ElizarovRefactored(): Int {
        val cubes = HashSet(cubes)

        val minPoint = cube(cubes.minOf(Cube::x), cubes.minOf(Cube::y), cubes.minOf(Cube::z))
        val maxPoint = cube(cubes.maxOf(Cube::x), cubes.maxOf(Cube::y), cubes.maxOf(Cube::z))

        val cubesCoveredMap = HashMap<Cube, Boolean>()

        fun MutableSet<Cube>.scan(cube: Cube): Boolean {
            cubesCoveredMap[cube]?.let { return it }
            if (cube.x !in minPoint.x..maxPoint.x ||
                cube.y !in minPoint.y..maxPoint.y ||
                cube.z !in minPoint.z..maxPoint.z
            ) return true
            cubesCoveredMap[cube] = false
            add(cube)
            return GridPoint3D.orthoDirs
                .map(cube::plus)
                .filterNot(cubes::contains)
                .any(::scan)
        }
        return cubes.surfaceArea(cubesCoveredMap, MutableSet<Cube>::scan)
    }

    private fun MutableSet<Cube>.surfaceArea(
        cubesCoveredMap: HashMap<Cube, Boolean>,
        scan: MutableSet<Cube>.(Cube) -> Boolean
    ): Int {
        var area = 0
        for (cube in this) {
            for (dir in GridPoint3D.orthoDirs) {
                val neighbor = cube.plus(dir)
                if (neighbor in this) continue
                val visited = HashSet<Cube>()
                if (visited.scan(neighbor)) {
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
