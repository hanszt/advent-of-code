package aoc

import java.io.File

class Day02RockPaperScissors(fileName: String) : ChallengeDay {

    private val lines: List<String>

    init {
        lines = File(fileName).readLines()
    }

    override fun part1(): Int {
        var totalScore = 0
        for (line in lines) {
            val (opponent, self) = line.split(" ").map { types[it]!! }
            totalScore += determineScore(opponent, self) + self
        }
        return totalScore
    }

    private fun determineScore(opponent: Int, self: Int): Int {
        return if (opponent == self) draw
        else {
            val win1 = opponent == rock && self == paper
            val win2 = opponent == paper && self == scissors
            val win3 = opponent == scissors && self == rock
            if (win1 || win2 || win3) win else lost
        }
    }

    override fun part2(): Int {
        var totalScore = 0
        for (line in lines) {
            val (opponent, requiredOutcome) = line.split(" ")
            val outcome = requiredOutcomes[requiredOutcome]!!
            totalScore += outcome + determineSelf(types[opponent]!!, outcome)
        }
        return totalScore
    }

    private fun determineSelf(opponent: Int, outcome: Int): Int {
        return when (outcome) {
            draw -> opponent
            win -> determineSelfWhenWon(opponent)
            else -> determineSelfWhenLost(opponent)
        }
    }

    private fun determineSelfWhenWon(opponent: Int): Int {
        val i = opponent + 1
        return if (i > 3) 1 else i
    }

    private fun determineSelfWhenLost(opponent: Int): Int {
        val i = opponent - 1
        return if (i < 1) 3 else i
    }

    companion object {

        private const val lost = 0
        private const val draw = 3
        private const val win = 6

        private const val rock = 1
        private const val paper = 2
        private const val scissors = 3

        private val types = mapOf(
            "A" to rock, "B" to paper, "C" to scissors,
            "X" to rock, "Y" to paper, "Z" to scissors
        )
        private val requiredOutcomes = mapOf("X" to lost, "Y" to draw, "Z" to win)
    }
}
