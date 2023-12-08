package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import aoc.utils.lcm
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
        network = networkPart.lines().map { it.take(3) }.associateWith(::Node)

        for (n in networkPart.lines()) {
            val (label, lrLabel) = n.split(" = ")
            val (lLabel, rLabel) = lrLabel.slice(1..<lrLabel.lastIndex).split(", ")

            network[label]?.apply {
                left = network[lLabel]
                right = network[rLabel]
            }
        }
    }

    override fun part1(): Long = countSteps(start = network["AAA"]) { it == network["ZZZ"] }
    override fun part2(): Long = network.values
        .filter { it.label.endsWith("A") }
        .map { startNode -> countSteps(startNode) { it?.label?.endsWith("Z") == true } }
        .reduce(Long::lcm)

    private fun countSteps(start: Node?, isGoalAt: (Node?) -> Boolean): Long {
        var current = start
        var index = 0
        var steps = 0L
        do {
            current = when (val instr = instructions[index]) {
                'L' -> current?.left
                'R' -> current?.right
                else -> error("Unknown instruction: $instr")
            }
            index = if (index == instructions.lastIndex) 0 else index + 1
            steps++
        } while (!isGoalAt(current))
        return steps
    }

    private data class Node(val label: String, var left: Node? = null, var right: Node? = null) {
        override fun toString(): String = "Node(label='$label', left=${left?.label}, right=${right?.label})"
    }
}