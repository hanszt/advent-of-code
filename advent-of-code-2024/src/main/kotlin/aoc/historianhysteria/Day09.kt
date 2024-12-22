package aoc.historianhysteria

import aoc.utils.ChallengeDay
import java.nio.file.Path
import kotlin.io.path.readText

/**
 * What is the resulting filesystem checksum?
 *
 * I had an of by one error, looked at the following to see what it had to be.
 * https://github.com/elizarov/AdventOfCode2024/blob/main/src/Day09_1.kt to see what the right answer should have been
 */
class Day09(diskMap: String) : ChallengeDay {
    constructor(path: Path) : this(path.readText())

    private val diskSegments = toSegments(diskMap)

    private fun toSegments(diskMap: String): List<DiskSegment> {
        var index = 0L
        val segments = diskMap.flatMapIndexed { i, c ->
            val size = c.digitToInt()
            val space = if (i % 2 == 0) (0..<size).map { FileSegment(i / 2) }
            else (0..<size).map { EmptySegment }
            index += size
            space
        }
        return segments
    }

    override fun part1(): Long {
        val deFragmented = deFragment(diskSegments)
        var sum = 0L
        for ((i, fs) in deFragmented.withIndex()) {
            sum += (i * fs.id).toLong()
        }
        return sum
    }

    override fun part2(): Int {
        TODO()
    }

    private fun deFragment(memory: List<DiskSegment>): List<FileSegment> = buildList {
        var i1 = 0
        var i2 = memory.lastIndex
        while (i1 <= i2) {
            val segment = memory[i1]
            if (segment !is FileSegment) {
                var other = memory[i2]
                while (other !is FileSegment) {
                    other = memory[--i2]
                }
                add(other)
                i2--
            } else {
                add(segment)
            }
            i1++
        }
        if (size % 2 != 0) removeLast() // hack
    }

    sealed interface DiskSegment

    data class FileSegment(val id: Int) : DiskSegment {
        override fun toString(): String = "$id"
    }

    data object EmptySegment : DiskSegment {
        override fun toString(): String = "."
    }
}
