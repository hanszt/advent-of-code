package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import aoc.utils.mapLines
import java.io.File

class Day19(
    fileName: String? = null,
    text: String = File(fileName ?: error("No fileName or text provided")).readText()
) : ChallengeDay {

    private val workFlows: List<WorkFlow>
    private val parts: List<Part>

    init {
        val (workFlows, ratings) = text.split("\n\n")
        this.workFlows = workFlows.mapLines { WorkFlow.parse(it) }
        this.parts = ratings.mapLines { Part.parse(it) }
    }

    override fun part1(): Int {
        workFlows.forEach(::println)
        println()
        parts.forEach(::println)
        return -1
    }

    override fun part2(): Int {
        TODO()
    }

    private data class WorkFlow(val name: String, val rules: List<Rule>) {

        companion object {
            fun parse(line: String): WorkFlow {
                val (name, rules) = line.split('{')
                return WorkFlow(name, rules.dropLast(1)
                    .split(',')
                    .map {
                        val split = it.split(':')
                        if (split.size == 1) return@map Rule(destination = split.single())
                        Rule(split.first(), split.last())
                    })
            }
        }

        private data class Rule(val condition: String? = null, val destination: String)
    }

    private data class Part(val x: Int, val m: Int, val a: Int, val s: Int) {

        companion object {

            private val partRatingsRegex = Regex("\\{x=(\\d+),m=(\\d+),a=(\\d+),s=(\\d+)}")
            fun parse(line: String): Part {
                val (x, m, a, s) = (partRatingsRegex.matchEntire(line)?.groupValues ?: emptyList())
                    .drop(1)
                    .map(String::toInt)
                return Part(x, m, a, s)
            }

        }
    }
}
