package aoc.snowrescuemission

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day16Test {

    @Test
    fun testPart1() {
        Day16("input/day16-dr.txt").part1() shouldBe 7_482
    }

    @Test
    fun testPart1TestInput() {
        Day16(
            grid = """
            .|...\....
            |.-.\.....
            .....|-...
            ........|.
            ..........
            .........\
            ..../.\\..
            .-.-/..|..
            .|....-|.\
            ..//.|....
        """.trimIndent().lines()
        ).part1() shouldBe 46
    }

    @Test
    fun testLoops() {
        Day16(
            grid = """
            |.-
            ...
            -.|
        """.trimIndent().lines()
        ).part1() shouldBe 8
    }

    @Test
    fun test90degBounces() {
        Day16(
            grid = """
            \/\
            \/.
            ..|
        """.trimIndent().lines()
        ).part1() shouldBe 7
    }

    @Test
    fun testSplit() {
        Day16(
            grid = """
            .|.
            .-.
            ...
        """.trimIndent().lines()
        ).part1() shouldBe 5
    }

    @Test
    fun testSplitLarger() {
        Day16(
            grid = """
            ..|..
            .....
            |.-.|
            .....
            .....
        """.trimIndent().lines()
        ).part1() shouldBe 16
    }

    @Test
    fun testPart2() {
        Day16("input/day16-dr.txt").part2() shouldBe 7_896
    }

}