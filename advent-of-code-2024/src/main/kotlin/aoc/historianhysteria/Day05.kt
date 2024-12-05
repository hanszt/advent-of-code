package aoc.historianhysteria

import aoc.utils.ChallengeDay
import aoc.utils.model.GridPoint2D.Companion.northeast
import aoc.utils.model.GridPoint2D.Companion.northwest
import aoc.utils.model.GridPoint2D.Companion.southeast
import aoc.utils.model.GridPoint2D.Companion.southwest
import java.nio.file.Path
import kotlin.io.path.readText

class Day05(private val input: String) : ChallengeDay {
    constructor(path: Path) : this(path.readText())

    private companion object {
        val diagDirs = listOf(northwest, northeast, southeast, southwest)
    }

    override fun part1(): Int {
       TODO()
    }

    override fun part2(): Int {
        TODO()
    }
}
