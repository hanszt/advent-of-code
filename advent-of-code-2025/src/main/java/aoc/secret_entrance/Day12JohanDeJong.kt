package aoc.secret_entrance

import aoc.utils.grid2d.*
import aoc.utils.parts

/** [Johan de Jong Day 12](https://github.com/johandj123/adventofcode2025/blob/master/src/Day12.java) */
class Day12JohanDeJong(private val text: String) {

    fun run(): Int {
        val parts = text.lines().parts { it }
        val presents = parts.dropLast(1).map { CharMatrix(it.drop(1)) }
        val regions = parts.last().map(::Region)
        val presentSizes = presents.map(::countGridPoint2Ds)
        val presentsAllOrientations = presents.map(::allOrientations)

        var count = 0
        for (region in regions) {
            if (region.size < region.countGridPoint2DsNeeded(presentSizes)) {
                continue
            }
            if (region.canPlacePresents(presentsAllOrientations)) {
                count++
            }
        }
        return count
    }

    internal class Region(line: String) {
        val width: Int
        val height: Int
        val counts: List<Int>

        init {
            val ints = extractPositiveIntegers(line)
            width = ints[0]
            height = ints[1]
            counts = ints.drop(2)
        }

        val size: Int = width * height

        fun countGridPoint2DsNeeded(presentSizes: List<Int>): Int =
            counts.indices.sumOf { counts[it] * presentSizes[it] }

        fun presentsToBePlaced(presentsAllOrientations: List<Set<CharMatrix>>): List<Set<CharMatrix>> = buildList {
            for (i in counts.indices) {
                repeat(counts[i]) {
                    add(presentsAllOrientations[i])
                }
            }
        }

        override fun toString(): String = "Region{width=$width, height=$height, counts=$counts}"

        fun canPlacePresents(presentsAllOrientations: List<Set<CharMatrix>>): Boolean {
            val charMatrix = CharMatrix(height, width, '.')
            val presentList = presentsToBePlaced(presentsAllOrientations)
            return charMatrix.canPlacePresents(presentList)
        }

        private fun CharMatrix.canPlacePresents(presentsAllOrientations: List<Set<CharMatrix>>): Boolean {
            if (presentsAllOrientations.isEmpty()) {
                return true
            }
            val presentOrientations: Set<CharMatrix> = presentsAllOrientations.first()
            for (present in presentOrientations) {
                for (y in 0..<this.height - present.height + 1) {
                    for (x in 0..<this.width - present.width + 1) {
                        val position = GridPoint2D(x, y)
                        if (canPlace(present, position)) {
                            place(present, position)
                            val canPlace = canPlacePresents(presentsAllOrientations.drop(1))
                            if (canPlace) {
                                return true
                            }
                            unplace(present, position)
                        }
                    }
                }
            }
            return false
        }

        private fun CharMatrix.canPlace(present: CharMatrix, position: GridPoint2D): Boolean {
            for (y in 0..<present.height) {
                for (x in 0..<present.width) {
                    val d = GridPoint2D(x, y)
                    if (present[d] == '#' && this[position + d] == '#') {
                        return false
                    }
                }
            }
            return true
        }

        private fun CharMatrix.place(present: CharMatrix, position: GridPoint2D) {
            set(present, position, '#')
        }

        private fun CharMatrix.unplace(present: CharMatrix, position: GridPoint2D) {
            set(present, position, '.')
        }

        companion object {
            private val nonDigit = "\\D".toRegex()

            fun extractPositiveIntegers(input: String): List<Int> {
                return input.split(nonDigit).filter(String::isNotBlank).map(String::toInt)
            }

            private fun CharMatrix.set(present: CharMatrix, position: GridPoint2D, c: Char) {
                for (y in 0..<present.height) {
                    for (x in 0..<present.width) {
                        val d = GridPoint2D(x, y)
                        if (present[d] == '#') {
                            this[position + d] = c
                        }
                    }
                }
            }
        }
    }

    internal class CharMatrix(private val content: Array<CharArray>) {

        constructor(content: List<String>) : this(
            Array(content.size) { y -> CharArray(content[y].length) { x -> content[y][x] } }
        )

        constructor(height: Int, width: Int, fill: Char) :
                this(Array(height) { CharArray(width) { fill } })

        fun chars(): Sequence<Char> = content.asSequence().flatMap { it.asSequence() }

        val width: Int = content[0].size
        val height: Int = content.size

        operator fun get(p: GridPoint2D): Char = content[p]

        operator fun set(p: GridPoint2D, c: Char) {
            content[p] = c
        }

        fun getRow(y: Int): String = buildString(content[y].size) {
            for (x in content[y].indices) {
                append(content[y][x])
            }
        }

        fun getColumn(x: Int): String = buildString(content.size) {
            for (chars in content) {
                append(chars[x])
            }
        }

        fun transpose(): CharMatrix {
            val s = List(this.width) { getColumn(it) }
            return CharMatrix(s)
        }

        fun mirrorHorizontal(): CharMatrix = CharMatrix(content.mirroredHorizontally())
        fun mirrorVertical(): CharMatrix = CharMatrix(content.mirroredVertically())

        override fun equals(other: Any?): Boolean =
            this === other || (other is CharMatrix && content.contentDeepEquals(other.content))

        override fun hashCode(): Int = content.contentDeepHashCode()
        override fun toString(): String = content.indices.joinToString("\n", transform = this::getRow)
    }

    companion object {
        private fun allOrientations(charMatrix: CharMatrix): Set<CharMatrix> =
            charMatrix.seenByBfs { listOf(it, it.mirrorHorizontal(), it.mirrorVertical(), it.transpose()) }

        private fun countGridPoint2Ds(charMatrix: CharMatrix): Int = charMatrix.chars().count { it == '#' }

        fun <T> T.seenByBfs(toNeighbours: (T) -> Iterable<T>): Set<T> {
            val start = setOf(this)
            val seen = HashSet(start)
            val todo = ArrayDeque(start)
            while (todo.isNotEmpty()) {
                val node = todo.removeLast()
                for (nextNode in toNeighbours(node)) {
                    if (seen.add(nextNode)) {
                        todo.add(nextNode)
                    }
                }
            }
            return seen
        }
    }
}
