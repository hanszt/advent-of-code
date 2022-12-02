package aoc

import utils.camelCaseToSentence

internal interface ChallengeDay {

    fun runParts(): List<AocResult> {
        val name = javaClass.simpleName.camelCaseToSentence()
        val result1 = runChallengeTimed({ part1() }, "$name part 1")
        val result2 = runChallengeTimed({ part2() }, "$name part 2")
        return listOf(result1, result2)
    }

    private fun runChallengeTimed(solve: () -> Any, name: String): AocResult {
        val start = System.nanoTime()
        val result = solve.runCatching { solve().toString() }
        return AocResult(name, result, System.nanoTime() - start)
    }

    fun part1(): Any
    fun part2(): Any

    companion object {
        var inputDir: String = "input"
    }
}
