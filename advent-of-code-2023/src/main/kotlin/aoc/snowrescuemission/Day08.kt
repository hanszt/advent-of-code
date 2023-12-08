package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import aoc.utils.lcm
import aoc.utils.toEnds
import java.io.File

class Day08(
    fileName: String? = null,
    text: String = fileName?.let { File(it).readText() } ?: error("No text or fileName provided")
) : ChallengeDay {

    private val instructions: String
    private val network: Map<String, Node>

    init {
        val (instructions, networkPart) = text.split("\n\n")
        this.instructions = instructions
        val networkNodes = networkPart.lines()
        network = networkNodes.map { it.take(3) }.associateWith(::Node)

        for (n in networkNodes) {
            val (label, others) = n.split(" = ")

            val (lLabel, rLabel) = others.split(", ")
                .toEnds()
                .let { (left, right) -> left.drop(1) to right.substring(0, right.lastIndex) }

            network[label]?.apply {
                left = network[lLabel]
                right = network[rLabel]
            }
        }
    }

    override fun part1(): Int = countSteps(start = network["AAA"]) { it != network["ZZZ"] }
    override fun part2(): Long = network.values
        .filter { it.label.endsWith("A") }
        .map { startNode -> countSteps(startNode) { node -> node?.label?.endsWith("Z") == false }.toLong() }
        .reduce(Long::lcm)

    private fun countSteps(start: Node?, stopCondition: (Node?) -> Boolean): Int {
        var current = start
        var index = 0
        var steps = 0
        do {
            current = when (val instr = instructions[index]) {
                'L' -> current?.left
                'R' -> current?.right
                else -> error("Unknown instruction: $instr")
            }
            index = if (index == instructions.lastIndex) 0 else index + 1
            steps++
        } while (stopCondition(current))
        return steps
    }

    private data class Node(val label: String, var left: Node? = null, var right: Node? = null) {
        override fun toString(): String = "Node(label='$label', left=${left?.label}, right=${right?.label})"
    }
}