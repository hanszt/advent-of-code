package aoc.historianhysteria

import aoc.utils.ChallengeDay
import aoc.utils.grid2d.gridAsString
import aoc.utils.grid2d.Dimension2D
import aoc.utils.grid2d.GridPoint2D
import aoc.utils.grid2d.Vec2
import aoc.utils.grid2d.dimension2D
import aoc.utils.grid2d.gridPoint2D
import aoc.utils.grid2d.mod
import java.nio.file.Path
import kotlin.io.path.readLines

class Day14(input: List<String>, private val dimension2D: Dimension2D = dimension2D(101, 103)) : ChallengeDay {

    private val rx1 = 0..<dimension2D.width / 2
    private val ry1 = 0..<dimension2D.height / 2
    private val rx2 = (dimension2D.width / 2) + 1..dimension2D.width
    private val ry2 = (dimension2D.height / 2) + 1..dimension2D.height

    private val regex = Regex("""p=(-?\d+),(-?\d+) v=(-?\d+),(-?\d+)""")

    internal val robots = input.map { it ->
        val groups = regex.find(it)?.groups ?: error("Not found in $it")
        Robot(
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

    private fun moved(robot: Robot): Robot = robot.copy(position = (robot.position + robot.velocity).mod(dimension2D))

    /**
     * What is the fewest number of seconds that must elapse for the robots to display the Easter egg?
     */
    override fun part2(): Int {
        val target = elizarovDay14Part2(robots = robots, searchRange = 0..10_000, dimension = dimension2D)
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
        val grid = Array(dimension2D.height) { CharArray(dimension2D.width) { '.' } }
        for (r in robots) {
            val x = r.position.x
            val y = r.position.y
            grid[y][x] = 'O'
        }
        return grid.gridAsString()
    }
}

internal data class Robot(val position: GridPoint2D, val velocity: Vec2)
