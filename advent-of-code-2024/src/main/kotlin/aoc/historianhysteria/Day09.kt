package aoc.historianhysteria

import aoc.utils.ChallengeDay
import java.nio.file.Path
import kotlin.io.path.readText

/**
 * What is the resulting filesystem checksum?
 */
class Day09(diskMap: String) : ChallengeDay {

    private val diskSpaces = getDiskSpaces(diskMap)
    private val diskSpaces2 = joinToString(diskMap)

    private fun joinToString(diskMap: String): String {
        var index = 0
        val string = diskMap.withIndex().joinToString("") { (i, c) ->
            val size = c.digitToInt()
            val space = if (i % 2 == 0) (0..<size).joinToString("") { "${i / 2}"} else (0..<size).joinToString("") { "." }
            index += size
            space
        }
        return string
    }

    private fun getDiskSpaces(diskMap: String): List<DiskSpace> {
        var index = 0L
        return diskMap.mapIndexed { i, size ->
            val size = size.digitToInt()
            val space = if (i % 2 == 0) File(id = i / 2, startIndex = index, size = size) else Empty(startIndex = index, size = size)
            index += size
            space
        }
    }

    constructor(path: Path) : this(path.readText())

    override fun part1(): Long {
        println(diskSpaces2)
        return idsToPositions(diskSpaces)
            .entries
            .sumOf { (id, positions) -> positions.sumOf { id * it.toLong() } }
    }

    override fun part2(): Int {
        TODO()
    }

    private fun idsToPositions(spaces: List<DiskSpace>): Map<Int, List<Int>> = buildMap {
        val empties = spaces.filterIsInstance<Empty>().mapTo(HashSet()) {}
        var indexFront = 0
        var indexBack = spaces.lastIndex
        while (true) {
            val space = spaces[indexFront]
            if (space is Empty) {
                for (i in 1..space.size) {

                }
            }
            when (spaces[indexBack]) {
                is File -> TODO()
                is Empty -> TODO()
            }
            indexFront++
        }
    }

    sealed interface DiskSpace {
        val startIndex: Long
        val size: Int
    }

    data class File(val id: Int, override val startIndex: Long, override val size: Int) : DiskSpace
    data class Empty(override val size: Int, override val startIndex: Long) : DiskSpace
}
