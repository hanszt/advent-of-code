package aoc.snowrescuemission

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day17Test {

    private companion object {
        private val day17 = Day17(Path("input/day17.txt"))
        private val day17TestInput = Day17(
            grid = """
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
            """.trimIndent().lines()
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
        Day17(
            grid = """
            111111111111
            999999999991
            999999999991
            999999999991
            999999999991
        """.trimIndent().lines()
        ).part2() shouldBe 71
    }

    @Test
    fun testPart1() {
        day17.part1() shouldBe 1260
    }

    @Test
    fun testPart2() {
        day17.part2() shouldBe 1420 // 1421 to high
    }

}