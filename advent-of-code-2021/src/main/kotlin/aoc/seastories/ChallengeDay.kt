package aoc.seastories

import aoc.utils.AocResult
import aoc.utils.camelCaseToSentence

internal interface ChallengeDay : aoc.utils.ChallengeDay {

    override fun runParts(): Sequence<AocResult> {
        val name = javaClass.simpleName.camelCaseToSentence()
        return sequenceOf(
            runChallengeTimed({ part1() }, "$name part 1"),
            runChallengeTimed({ part2() }, "$name part 2")
        )
    }

    private fun runChallengeTimed(solve: () -> Any, name: String): AocResult {
        val start = System.nanoTime()
        val result = solve.runCatching { solve().toString() }
        return AocResult(name, result, System.nanoTime() - start)
    }

    override fun part1(): Any
    override fun part2(): Any

    companion object {
        var inputDir: String = "input"
    }
}
