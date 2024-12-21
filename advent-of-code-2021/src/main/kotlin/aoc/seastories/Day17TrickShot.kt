package aoc.seastories

import aoc.utils.grid2d.GridPoint2DRange
import aoc.utils.grid2d.by
import aoc.utils.grid2d.GridPoint2D
import aoc.utils.grid2d.GridPoint2D.Companion.by

internal object Day17TrickShot : ChallengeDay {

    private const val UPPER_SEARCH_BOUND_Y = 1000

    fun part1(targetArea: GridPoint2DRange): Int =
        probesInTargetArea(targetArea).maxOf { it.highestPosition.y }

    private fun probesInTargetArea(targetArea: GridPoint2DRange) =
        (0..targetArea.start.x).asSequence()
            .flatMap { initVelocityX ->
                (0 until UPPER_SEARCH_BOUND_Y)
                    .map { initVelocityY -> Probe(initVelocityX by initVelocityY) }
                    .filter { it.endsUpInTargetArea(targetArea) }
            }

    fun part2(targetRange: GridPoint2DRange): Int {
        val successfulInitVelocityVals = mutableSetOf<GridPoint2D>()
        for (initVelX in 1..targetRange.endInclusive.x) {
            for (initVelY in targetRange.start.y until UPPER_SEARCH_BOUND_Y) {
                val probe = Probe(initVelX by initVelY)
                val endsUpInTargetArea = probe.endsUpInTargetArea(targetRange)
                if (endsUpInTargetArea) {
                    successfulInitVelocityVals.add(initVelX by initVelY)
                }
            }
        }
        return successfulInitVelocityVals.size
    }

    override fun part1() = part1(185..221 by -122..-74)
    override fun part2() = part2(185..221 by -122..-74)

    internal class Probe(var velocity: GridPoint2D = GridPoint2D.ZERO) {

        var highestPosition: GridPoint2D = 0 by Int.MIN_VALUE
        var position = GridPoint2D.ZERO

        private fun updatePosition(): GridPoint2D {
            position = position.plus(velocity)
            velocity = velocity.plusX(0.compareTo(velocity.x))
            velocity = velocity.plusY(-1)
            highestPosition = if (position.y < highestPosition.y) highestPosition else position
            return position
        }

        fun getPath(minY: Int = 0): Set<GridPoint2D> {
            val path = mutableSetOf(position)
            while (position.y >= minY) {
                val nextPosition = updatePosition()
                path.add(nextPosition)
            }
            return path
        }

        fun endsUpInTargetArea(targetRange: GridPoint2DRange): Boolean {
            while (position.x < targetRange.endInclusive.x && position.y > targetRange.start.y) {
                val nextPosition = updatePosition()
                if (nextPosition in targetRange) {
                    return true
                }
            }
            return false
        }

        override fun toString(): String = "Probe(velocity=$velocity, position=$position)"
    }
}
