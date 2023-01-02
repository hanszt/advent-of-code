package aoc

import aoc.utils.camelCaseToSentence

/**
 * Challenge day
 *
 * @see <a href="https://kotlinlang.org/docs/java-to-kotlin-interop.html#default-methods-in-interfaces">Default methods in interfaces</a>
 *
 * @constructor Create empty Challenge day
 */
@JvmDefaultWithCompatibility
interface ChallengeDay {

    fun runParts(): Sequence<AocResult> {
        val name = javaClass.simpleName.camelCaseToSentence()
        return sequenceOf(
            runChallengeTimed({ part1() }, "$name part 1"),
            runChallengeTimed({ part2() }, "$name part 2")
        )
    }

    private fun runChallengeTimed(solve: () -> Any, name: String): AocResult {
        val start = System.nanoTime()
        val result = runCatching { solve().toString() }
        return AocResult(name, result, System.nanoTime() - start)
    }

    fun part1(): Any
    fun part2(): Any
}
