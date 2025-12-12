package aoc.secret_entrance

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day11Test {

    companion object {
        private val testInputP1 = """
            aaa: you hhh
            you: bbb ccc
            bbb: ddd eee
            ccc: ddd eee fff
            ddd: ggg
            eee: out
            fff: out
            ggg: out
            hhh: ccc fff iii
            iii: out
        """.trimIndent()

        private val testInputP2 = """
            svr: aaa bbb
            aaa: fft
            fft: ccc
            bbb: tty
            tty: ccc
            ccc: ddd eee
            ddd: hub
            hub: fff
            eee: dac
            dac: fff
            fff: ggg hhh
            ggg: out
            hhh: out
        """.trimIndent()

        private val path = Path("input/day11.txt")
        private val day11 = Day11(path)
        private val day11TestInputP1 = Day11(testInputP1.lines())
        private val day11Zebalu = Day11Zebalu.fromPath(path)
    }

    @Test
    fun testPart1TestInput() {
        day11TestInputP1.part1() shouldBe 5
    }

    @Test
    fun testPart1() {
        day11.part1() shouldBe 772
    }

    @Test
    fun testPart2TestInput() {
        Day11(testInputP2.lines()).part2() shouldBe 2
    }

    @Test
    fun testPart2() {
        day11.part2() shouldBe 423227545768872
    }

    @Test
    fun testPart2TestInputZebalu() {
        Day11Zebalu.fromText(testInputP2).part2() shouldBe 2
    }

    @Test
    fun testPart2Zebalu() {
        day11Zebalu.part2() shouldBe 423227545768872
    }
}