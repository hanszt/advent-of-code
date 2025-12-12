package aoc.secret_entrance

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.io.path.Path
import kotlin.io.path.readLines

class Day10Test {

    private val testInput = """
                [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
                [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
                [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
            """.trimIndent()

    private val path = Path("input/day10.txt")
    private val day10 = Day10(path)
    private val day10TestInput = Day10(testInput.lines())
    private val day10Zebalu = Day10Zebalu.fromPath(path)
    private val day10ZebaluTestInput = Day10Zebalu.fromText(testInput)


    @Test
    fun testPart1TestInput() {
        day10TestInput.part1() shouldBe 7
    }

    @Test
    fun testPart1() {
        day10.part1() shouldBe 558
    }

    @Test
    fun testPart2TestInput() {
        day10TestInput.part2() shouldBe 33
    }

    @Test
    fun testPart2ZebaluTestInput() {
        day10ZebaluTestInput.part2() shouldBe 33
    }

    @Test
    @Disabled("Takes to long")
    fun testPart2() {
        day10.part2() shouldBe 20317
    }

    @Test
    fun testPart2Fast() {
        Day10ElizarovGauss.part2Fast(path.readLines()) shouldBe 20317
    }

    @Test
    @Disabled("Takes to long")
    fun testPart2Zebalu() {
        day10Zebalu.part2() shouldBe 20317
    }

}