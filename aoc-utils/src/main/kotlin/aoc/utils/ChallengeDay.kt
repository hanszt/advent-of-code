package aoc.utils

import kotlin.time.measureTimedValue

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
        val (result, duration) = measureTimedValue { runCatching { solve().toString() } }
        return AocResult(name, result, duration.inWholeNanoseconds)
    }

    fun part1(): Any
    fun part2(): Any
}
