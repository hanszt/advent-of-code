package aoc.historianhysteria

import aoc.utils.grid2d.dimension2D
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day14Test {

    private companion object {
        private val day14 by lazy { Day14(Path("input/day14.txt")) }
        private val day14dr by lazy { Day14(Path("input/day14-dr.txt")) }
        private val day14Test = Day14("""
            p=0,4 v=3,-3
            p=6,3 v=-1,-3
            p=10,3 v=-1,2
            p=2,0 v=2,-1
            p=0,0 v=1,3
            p=3,0 v=-2,-2
            p=7,6 v=-1,-3
            p=3,0 v=-1,-2
            p=9,3 v=2,3
            p=7,3 v=-1,2
            p=2,4 v=2,-3
            p=9,5 v=-3,-3
        """.trimIndent().lines(), dimension2D(width = 11, height = 7))
    }

    @Test
    fun part1Test() {
        day14Test.part1() shouldBe 12
    }

    @Test
    fun part1dr() {
        day14dr.part1() shouldBe 230435667
    }

    @Test
    fun part1() {
        day14.part1() shouldBe 230435667
    }

    @Test
    fun part2dr() {
        day14dr.part2() shouldBe 7709
    }

    @Test
    fun part2() {
        day14.part2() shouldBe 7709
    }

}