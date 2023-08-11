package aoc.jungle.adventures

import aoc.utils.ChallengeDay
import aoc.utils.model.GridPoint2D.Companion.by
import java.io.File
import kotlin.math.max
import kotlin.math.min
import aoc.utils.model.GridPoint2D as Point


/**
 * Credits to the Turkey dev.
 * @see <a href="https://adventofcode.com/2022/day/14">Day 14: Regolith reservoir</a>
 */
class Day14RegolithReservoir(fileName: String) : ChallengeDay {

    private val input = File(fileName).readLines()

    override fun part1(): Int = buildCave(input).simulate().first
    override fun part2() = buildCave(input).simulate().second

    private fun buildCave(input: List<String>): Cave {
        val cave = Cave()
        for (s in input) {
            val parts = s.split(" -> ")
            for (i in 1 until parts.size) {
                val (startX, startY) = parts[i - 1].split(',').map(String::toInt)
                val (endX, endY) = parts[i].split(',').map(String::toInt)
                (min(startX, endX)..max(startX, endX)).forEach { x -> cave.grid[x by startY] = Type.WALL }
                (min(startY, endY)..max(startY, endY)).forEach { y -> cave.grid[startX by y] = Type.WALL }
                cave.highestRow = max(startY, cave.highestRow)
            }
        }
        return cave
    }

    data class Cave(val grid: MutableMap<Point, Type> = HashMap(), var highestRow: Int = 0)
    enum class Type { WALL, SAND }

    @SuppressWarnings("kotlin:S3776")
    private fun Cave.simulate(): Pair<Int, Int> {
        val entryPoint = 500 by 0
        var sandPlacedPart1 = 0
        var sandPlacedPart2 = 0
        var hasSettled = true
        var canFall = true
        while (canFall) {
            var sandPoint = entryPoint
            var moving = true
            while (moving) {
                val below = sandPoint.plusY(1)
                if (below.y == highestRow + 2) {
                    hasSettled = false
                    moving = false
                    grid[sandPoint] = Type.SAND
                    sandPlacedPart2++
                    continue
                }
                if (below !in grid) {
                    sandPoint = below
                    continue
                }
                val belowLeft = sandPoint + (-1 by 1)
                if (belowLeft !in grid) {
                    sandPoint = belowLeft
                    continue
                }
                val belowRight = sandPoint + (1 by 1)
                if (belowRight !in grid) {
                    sandPoint = belowRight
                    continue
                }
                if (sandPoint == entryPoint) {
                    sandPlacedPart2++
                    canFall = false
                    break
                }
                moving = false
                grid[sandPoint] = Type.SAND
                if (hasSettled) sandPlacedPart1++
                sandPlacedPart2++
            }
        }
        return sandPlacedPart1 to sandPlacedPart2
    }
}
