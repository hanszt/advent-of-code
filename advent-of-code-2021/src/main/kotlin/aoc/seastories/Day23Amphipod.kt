package aoc.seastories

import aoc.utils.BRIGHT_BLUE
import aoc.utils.BROWN_BG
import aoc.utils.GREEN
import aoc.utils.RED
import aoc.utils.YELLOW
import aoc.utils.withColor
import java.io.File
import java.util.PriorityQueue
import java.util.Queue
import kotlin.math.abs

/**
 * I've not solved day 23 myself. This solution is from the [repo from Elizarov](https://github.com/elizarov/AdventOfCode2021/tree/main/src).
 * All credits go to him.
 *
 * I refactored and renamed it to understand what is going on.
 *
 * It is very educational to see how such a solution can be solved. Many thanks to Roman Elizarov.
 */
internal class Day23Amphipod(
    inputPath: String,
    private val testMode: Boolean = false
) : ChallengeDay {

    private val lines = File(inputPath).readLines()

    override fun part1(): Int = lines
        .let { it.slice(1..<it.lastIndex) }
        .let { Burrow(it.map(String::toCharArray).toTypedArray()) }
        .also { if (testMode) println(it) }
        .let(::calculateMinimumUsedEnergy)

    override fun part2(): Int = lines.subList(1, 4)
        .let { (first, second, bottom) -> listOf(first, second, "  #D#C#B#A#  ", "  #D#B#A#C#  ", bottom) }
        .let { Burrow(it.map(String::toCharArray).toTypedArray()) }
        .also { if (testMode) println(it) }
        .let(::calculateMinimumUsedEnergy)

    // adapted form of Dijkstra
    private fun calculateMinimumUsedEnergy(start: Burrow): Int {
        val queue = PriorityQueue(compareBy(Burrow::usedEnergy)).apply { enqueue(start) }
        val checkedBurrows = HashSet<Burrow>()

        while (queue.isNotEmpty()) {
            val burrow = queue.remove()
            if (burrow.inWantedConfiguration()) {
                if (testMode) println(burrow)
                return burrow.usedEnergy
            }
            if (burrow !in checkedBurrows) {
                burrow.printIfInTestMode(queue.size, checkedBurrows.size)
                burrow.ifPossibleMoveInHallWay(queue)
                burrow.ifPossibleMoveToTargetRoom(queue)
                checkedBurrows.add(burrow)
            }
        }
        error("No minimum energy found with start burrow: $start")
    }

    private fun Burrow.ifPossibleMoveToTargetRoom(queue: Queue<Burrow>) {
        for (x in 1..hallWayLength) {
            val amphipod = grid[0][x]
            if (amphipod.isAmphipod()) {
                val targetRoomNr = amphipod - 'A'
                val targetX = roomX(targetRoomNr)
                if (noOtherAmphipodInWay(x, targetX) && allWantedAmphipodTypeOrEmpty(targetX, amphipod)) {
                    val firstEmptyY = (roomDepth downTo 1).first { roomY -> grid[roomY][targetX] == '.' }
                    val distanceMoved = abs(targetX - x) + firstEmptyY
                    val copy = copy(usedEnergy + amphipod.energyToMove() * distanceMoved)
                    copy.grid[0][x] = '.'
                    copy.grid[firstEmptyY][targetX] = amphipod
                    queue.enqueue(copy)
                }
            }
        }
    }

    private fun Burrow.ifPossibleMoveInHallWay(queue: Queue<Burrow>) {
        for (room in 0..lastRoomNr) {
            for (curY in 1..roomDepth) {
                val roomX = roomX(room)
                ifPossibleMoveInHallWay(roomX, curY, queue)
            }
        }
    }

    private fun Burrow.ifPossibleMoveInHallWay(roomX: Int, curY: Int, queue: Queue<Burrow>) {
        val amphipod = grid[curY][roomX]
        if (amphipod.isAmphipod() && freePassageInRoomToTarget(curY, roomX)) {
            for (hallWayX in 1..hallWayLength) {
                if (notAboveRoom(hallWayX) && freePassageInHallWay(hallWayX, roomX)) {
                    val distanceMoved = abs(hallWayX - roomX) + curY
                    val copy = copy(usedEnergy + amphipod.energyToMove() * distanceMoved)
                    copy.grid[curY][roomX] = '.'
                    copy.grid[0][hallWayX] = amphipod
                    queue.enqueue(copy)
                }
            }
        }
    }

    private val burrowsToMinimumUsedEnergies = HashMap<Burrow, Int>()

    private fun Queue<Burrow>.enqueue(burrow: Burrow) {
        val usedEnergy = burrowsToMinimumUsedEnergies[burrow] ?: Int.MAX_VALUE
        if (burrow.usedEnergy < usedEnergy) {
            burrowsToMinimumUsedEnergies[burrow] = burrow.usedEnergy
            add(burrow)
        }
    }

    private fun Burrow.printIfInTestMode(queueSize: Int, setSize: Int) {
        if (testMode && setSize % 10_000 == 0) {
            println("least used energy=$usedEnergy, queue size=$queueSize, set size=$setSize")
            println(this)
        }
    }

    private fun Char.isAmphipod() = this in 'A'..'D'

    private fun Char.energyToMove(): Int = when (this) {
        'A' -> 1
        'B' -> 10
        'C' -> 100
        'D' -> 1000
        else -> error("$this")
    }

    //
    //    The Burrow in its start configuration
    //    #############
    //    #...........#
    //    ###D#A#D#C###
    //      #B#C#B#A#
    private data class Burrow(val grid: Array<CharArray>, val usedEnergy: Int = 0) {

        val roomDepth = grid.size - 1
        val hallWayLength = 11
        val lastRoomNr = 3

        fun notAboveRoom(hallWayX: Int) =
            ((hallWayX - lastRoomNr) % 2 == 0 && (hallWayX - lastRoomNr) / 2 in 0..lastRoomNr).not()

        fun roomX(roomNr: Int) = 2 * roomNr + lastRoomNr

        fun inWantedConfiguration() =
            (1..roomDepth).all { roomY -> (0..lastRoomNr).all { grid[roomY][roomX(it)] == targetType(it) } }

        private fun targetType(room: Int) = 'A' + room

        fun allWantedAmphipodTypeOrEmpty(roomX: Int, targetAmphipod: Char) =
            (1..roomDepth).all { y -> grid[y][roomX] == '.' || grid[y][roomX] == targetAmphipod }

        fun noOtherAmphipodInWay(hallWayX: Int, roomX: Int) =
            (minOf(hallWayX, roomX) + 1 until maxOf(hallWayX, roomX)).all { x -> grid[0][x] == '.' }

        fun freePassageInHallWay(hallWayX: Int, roomX: Int) =
            (minOf(hallWayX, roomX)..maxOf(hallWayX, roomX)).all { x -> grid[0][x] == '.' }

        fun freePassageInRoomToTarget(y: Int, roomX: Int) = (1 until y).all { roomY -> grid[roomY][roomX] == '.' }

        override fun equals(other: Any?): Boolean =
            other is Burrow && (0..roomDepth).all { y -> grid[y].contentEquals(other.grid[y]) }

        override fun hashCode(): Int = (0..roomDepth)
            .fold(0) { hashCode, depth -> hashCode + grid[depth].contentHashCode() }

        fun copy(usedEnergy: Int) = Burrow(grid.map(CharArray::copyOf).toTypedArray(), usedEnergy)

        override fun toString(): String = buildList {
            add(" ".repeat(13).withColor(BROWN_BG))
            addAll(grid
                .map(CharArray::concatToString)
                .map {
                    it.map { char ->
                        when (char) {
                            'A' -> char.toString().withColor(RED)
                            'B' -> char.toString().withColor(GREEN)
                            'C' -> char.toString().withColor(BRIGHT_BLUE)
                            'D' -> char.toString().withColor(YELLOW)
                            '#' -> ' '.toString().withColor(BROWN_BG)
                            else -> char
                        }
                    }.joinToString("")
                })
        }.joinToString("\n")
    }
}
