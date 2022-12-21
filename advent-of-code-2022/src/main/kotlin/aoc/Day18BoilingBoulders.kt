package aoc

import aoc.utils.model.GridPoint3D
import aoc.utils.model.gridPoint3D
import java.io.File

/**
 * Credits to Elizarov and Angelo
 *
 * @see <a href="https://adventofcode.com/2022/day/18">Day 18</a>
 */
class Day18BoilingBoulders(fileName: String) : ChallengeDay {

    private val cubes = File(fileName).useLines {
        it.map { line -> line.split(",").map(String::toInt) }
            .map { (x, y, z) -> gridPoint3D(x, y, z) }
            .toList()
    }

    override fun part1(): Int = calcSurfaceArea(cubes)
    override fun part2(): Int = part2ElizarovRefactored()

    private fun calcSurfaceArea(cubes: List<GridPoint3D>): Int {
        var touchingSides = 0
        for (i in 0 until cubes.lastIndex) {
            for (j in i + 1 until cubes.size) {
                touchingSides += if (cubes[i].manhattanDistance(cubes[j]) == 1) 2 else 0
            }
        }
        val allSides = cubes.size * 6
        return allSides - touchingSides
    }

    @Suppress("kotlin:S3776")
    private fun part2ElizarovRefactored(): Int {
        val cubes = HashSet(cubes)

        val minPoint = gridPoint3D(cubes.minOf { it.x }, cubes.minOf { it.y }, cubes.minOf { it.z })
        val maxPoint = gridPoint3D(cubes.maxOf { it.x }, cubes.maxOf { it.y }, cubes.maxOf { it.z })

        val cubesCoveredMap = HashMap<GridPoint3D, Boolean>()

        fun MutableSet<GridPoint3D>.scan(cube: GridPoint3D): Boolean {
            cubesCoveredMap[cube]?.let { return it }
            if (cube.x !in minPoint.x..maxPoint.x ||
                cube.y !in minPoint.y..maxPoint.y ||
                cube.z !in minPoint.z..maxPoint.z
            ) {
                return true
            }
            cubesCoveredMap[cube] = false
            this.add(cube)

            return GridPoint3D.orthoDirs
                .map(cube::plus)
                .any { it !in cubes && scan(it) }
        }

        var ans = 0
        for (cube in cubes) {
            for (dir in GridPoint3D.orthoDirs) {
                val neighbor = cube.plus(dir)
                if (neighbor in cubes) continue
                val visited = HashSet<GridPoint3D>()
                if (visited.scan(neighbor)) {
                    for (cubeCovered in visited) {
                        cubesCoveredMap[cubeCovered] = true
                    }
                    ans++
                }
            }
        }
        return ans
    }

}
