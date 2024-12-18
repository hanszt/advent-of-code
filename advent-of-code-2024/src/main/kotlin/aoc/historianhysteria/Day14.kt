package aoc.historianhysteria

import aoc.utils.ChallengeDay
import aoc.utils.gridAsString
import aoc.utils.model.GridPoint2D
import aoc.utils.model.Vec2
import aoc.utils.model.gridPoint2D
import aoc.utils.model.mod
import java.nio.file.Path
import kotlin.io.path.readLines

class Day14(input: List<String>, val width: Int = 101, val height: Int = 103) : ChallengeDay {

    private val rx1 = 0..<width / 2
    private val ry1 = 0..<height / 2
    private val rx2 = (width / 2) + 1..width
    private val ry2 = (height / 2) + 1..height

    private val regex = Regex("""p=(-?\d+),(-?\d+) v=(-?\d+),(-?\d+)""")

    internal val robots = input.mapIndexed { i, it ->
        val groups = regex.find(it)?.groups ?: error("Not found in $it")
        Robot(
            id = i,
            position = gridPoint2D(groups[1]!!.value.toInt(), groups[2]!!.value.toInt()),
            velocity = gridPoint2D(groups[3]!!.value.toInt(), groups[4]!!.value.toInt())
        )
    }

    constructor(path: Path) : this(path.readLines())

    /**
     * What will the safety factor be after exactly 100 seconds have elapsed?
     */
    override fun part1(): Int {
        val nrOfSeconds = 100
        val robots = robots.toTypedArray()
        repeat(nrOfSeconds) {
            for (i in 0..<robots.size) {
                robots[i] = moved(robots[i])
            }
        }
        var q1 = 0
        var q2 = 0
        var q3 = 0
        var q4 = 0
        for (robot in robots) {
            if (robot.position.x in rx1 && robot.position.y in ry1) q1++
            if (robot.position.x in rx2 && robot.position.y in ry1) q2++
            if (robot.position.x in rx1 && robot.position.y in ry2) q3++
            if (robot.position.x in rx2 && robot.position.y in ry2) q4++
        }
        return q1 * q2 * q3 * q4
    }

    private fun moved(robot: Robot): Robot = robot.copy(position = (robot.position + robot.velocity).mod(width, height))

    /**
     * What is the fewest number of seconds that must elapse for the robots to display the Easter egg?
     */
    override fun part2(): Int {
        val target = elizarovDay14Part2(robots = robots, searchRange = 0..10_000)
        val robots = robots.toTypedArray()
        repeat(target) {
            for (i in 0..<robots.size) {
                robots[i] = moved(robots[i])
            }
        }
        println(toPicture(robots))
        return target
    }

    internal fun toPicture(robots: Array<Robot>): String {
        val grid = Array(height) { CharArray(width) { '.' } }
        for (r in robots) {
            val x = r.position.x
            val y = r.position.y
            grid[y][x] = 'O'
        }
        return grid.gridAsString()
    }
}

internal data class Robot(val id: Int, val position: GridPoint2D, val velocity: Vec2)
