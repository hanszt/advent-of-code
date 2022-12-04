package aoc

import java.io.File

class Day04CampCleanup(filName: String) : ChallengeDay {

    private val sectionAssignments = File(filName).readLines()
        .map { it.split(',').map(::toRange).let { (r1, r2) -> r1 to r2 } }

    private fun toRange(s: String) = s.split('-').map(String::toInt).let { (start, end) -> start..end }

    override fun part1(): Int = sectionAssignments.count { (r1, r2) -> r1.all { it in r2 } || r2.all { it in r1 } }

    override fun part2(): Int = sectionAssignments.count { (r1, r2) -> r1.any { it in r2 } || r2.any { it in r1 } }
}
