package aoc.seastories

import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.io.path.Path
import kotlin.io.path.readLines

internal class Day12PassagePathingTest {

    private companion object {
        private val day12PassagePathingTestInput = Day12PassagePathing("input/day12test.txt")
        private val day12PassagePathingMediumInput = Day12PassagePathing("input/day12test-medium.txt")
        private val day12PassagePathing = Day12PassagePathing("input/day12.txt")
    }

    @Test
    fun `part 1 test input small`() {
        day12PassagePathingTestInput.part1() shouldBe 10
    }

    @Test
    fun `part 1 test input medium`() {
        day12PassagePathingMediumInput.part1() shouldBe 19
    }

    @Test
    fun `part 1 result`() {
        day12PassagePathing.part1().also(::println) shouldBe 3_298
    }

    @Test
    fun `part 2 test input small`() {
        day12PassagePathingTestInput.part2() shouldBe 36
    }

    @Test
    fun `part 2 test input medium`() {
        day12PassagePathingMediumInput.part2() shouldBe 103
    }

    @Test
    fun `part 2 result`() {
        day12PassagePathing.part2().also(::println) shouldBe 93_572
    }

    @Nested
    inner class ElizarovSolutions {

        @Test
        fun `part 1 result`() {
            day12PassagePathing.part1Elizarov() shouldBe 3_298
        }

        @Test
        fun `part 2 result`() {
            day12PassagePathing.part2Elizarov() shouldBe 93_572
        }

        @Test
        fun `part 1 small`() {
            val s = day12Elizarov(Path("input/day12test.txt").readLines())
                .map { it.traceBack { it.label }.reversed().joinToString(",") }
                .toList()

            s shouldHaveSize 10
        }

        @Test
        fun `part 2 small`() {
            val s = day12Elizarov(Path("input/day12test.txt").readLines(), visitSmallCaveTwice = true)
                .map { it.traceBack { it.label }.reversed().joinToString(",") }
                .toList()

            s shouldHaveSize 36
        }

    }
}
