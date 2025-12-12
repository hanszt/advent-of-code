package aoc.secret_entrance

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path
import kotlin.io.path.readText

class Day12Test {

    private val testInput = """
        0:
        ###
        ##.
        ##.

        1:
        ###
        ##.
        .##

        2:
        .##
        ###
        ##.

        3:
        ##.
        ###
        ##.

        4:
        ###
        #..
        ###

        5:
        ###
        .#.
        ###

        4x4: 0 0 0 0 2 0
        12x5: 1 0 1 0 2 2
        12x5: 1 0 1 0 3 2
    """.trimIndent()

    private val path = Path("input/day12.txt")
    private val day12 = Day12(path)

    @Test
    fun testPart1TestInput() {
        Day12JohanDeJong(testInput).run() shouldBe 2
    }

    @Test
    fun testPart1() {
        day12.part1() shouldBe 528
    }

    @Test
    fun part1JJ() {
        Day12JohanDeJong(path.readText()).run() shouldBe 528
    }

}