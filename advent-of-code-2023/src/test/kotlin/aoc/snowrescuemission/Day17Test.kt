package aoc.snowrescuemission

import aoc.snowrescuemission.Day17Abnew.Crucible
import aoc.utils.TextColor
import aoc.utils.grid2d.gridAsString
import aoc.utils.grid2d.rangeTo
import aoc.utils.grid2d.set
import aoc.utils.grid2d.toMutableGrid
import aoc.utils.withColor
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.io.path.Path
import kotlin.io.path.readLines

class Day17Test {

    private companion object {
        private val day17dr = Day17(Path("input/day17dr.txt"))
        private val day17 = Day17(Path("input/day17.txt"))
        private val testInput1 = """
                2413432311323
                3215453535623
                3255245654254
                3446585845452
                4546657867536
                1438598798454
                4457876987766
                3637877979653
                4654967986887
                4564679986453
                1224686865563
                2546548887735
                4322674655533
            """.trimIndent()
        private val testInput2 = """
                111111111111
                999999999991
                999999999991
                999999999991
                999999999991
            """.trimIndent()
        private val day17TestInput = Day17(
            grid = testInput1.lines()
        )
    }

    @Test
    fun testPart1TestInput() {
        day17TestInput.part1() shouldBe 102
    }

    @Test
    fun testPart2TestInput() {
        day17TestInput.part2() shouldBe 94
    }

    @Test
    fun testPart2TestInput2() {
        Day17(testInput2.lines()).part2() shouldBe 71
    }

    @Test
    fun testPart1() {
        day17dr.part1() shouldBe 1260
    }

    @Test
    fun testPart1V2() {
        day17.part1() shouldBe 1155
    }

    @Test
    fun testPart2() {
        day17.part2() shouldBe 1283 // 1421, 1286 to high
    }

    @Test
    fun testPart2dr() {
        day17dr.part2() shouldBe 1416 // 1421 to high
    }

    @Nested
    inner class AbnewVersionTest {

        private val grid = Path("input/day17.txt").readLines()
        private val day17 = Day17Abnew(grid)

        @Test
        fun testPart1() {
            val location = day17.part1()
            println(pathInGridAsString(location, grid))
            location.best shouldBe 1155
        }

        @Test
        fun testPart2() {
            val location = day17.part2()
            println(pathInGridAsString(location, grid))
            location.best shouldBe 1283
        }

        @Test
        fun testPart2Test1() {
            val input = testInput1.lines()
            val crucible = Day17Abnew(input).part2()
            println(pathInGridAsString(crucible, input))
            crucible.best shouldBe 94
        }

        @Test
        fun testPart2Test2() {
            val input = testInput2.lines()
            val crucible = Day17Abnew(input).part2()
            println(pathInGridAsString(crucible, input))
            crucible.best shouldBe 71
        }

        private fun pathInGridAsString(best: Crucible, grid: List<String>): String {
            val gr = grid.toMutableGrid { it.toString() }
            val traceBack = best.traceBack { it }
            val maxBest = traceBack.first().best
            traceBack
                .zipWithNext { c, n -> c.position..n.position to c.best }
                .forEach { (r, b) ->
                    r.forEach {
                        val hue = ((b / maxBest.toDouble()) * 270).toInt()
                        gr[it] = '#'.withColor(TextColor.hsb(hue))
                    }
                }
            return gr.gridAsString(1)
        }
    }
}