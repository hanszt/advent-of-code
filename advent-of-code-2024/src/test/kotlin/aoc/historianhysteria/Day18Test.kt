package aoc.historianhysteria

import aoc.utils.grid2d.dimension2D
import aoc.utils.grid2d.GridPoint2D
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day18Test {

    private companion object {
        private val day18 by lazy { Day18(Path("input/day18.txt")) }
        private val day18dr by lazy { Day18(Path("input/day18-dr.txt")) }
        private val day18Test by lazy {
            Day18(
                """
                5,4
                4,2
                4,5
                3,0
                2,1
                6,3
                2,4
                1,5
                0,6
                3,3
                2,6
                5,1
                1,2
                5,5
                2,5
                6,5
                1,4
                0,4
                6,4
                1,1
                6,1
                1,0
                0,5
                1,6
                2,0
            """.trimIndent().lines(),
                nrOfFallenBytes = 12,
                dimension2D = dimension2D(7, 7)
            )
        }

    }

    @Test
    fun part1Test() {
        day18Test.part1() shouldBe 22
    }

    @Test
    fun part1dr() {
        day18dr.part1() shouldBe 372
    }

    @Test
    fun part1() {
        day18.part1() shouldBe 270
    }

    @Test
    fun part2Test() {
        day18Test.part2() shouldBe GridPoint2D(6, 1)
    }

    @Test
    fun part2dr() {
        day18dr.part2() shouldBe GridPoint2D(25, 6)
    }

    @Test
    fun part2() {
        day18.part2() shouldBe GridPoint2D(51, 40)
    }

    @Nested
    inner class ElizarovSolutionsTest {

        @Test
        fun part1drElizarov() {
            day18dr.part1Elizarov() shouldBe 372
        }

        @Test
        fun part2drElizarov() {
            day18dr.part2Elizarov() shouldBe GridPoint2D(25, 6)
        }

        @Test
        fun part2drElizarovRaw() {
            day18dr.part2ElizarovRaw() shouldBe "25,6"
        }
    }

}