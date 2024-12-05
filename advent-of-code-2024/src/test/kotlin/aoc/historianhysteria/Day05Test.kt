package aoc.historianhysteria

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day05Test {


    private companion object {
        private val day05 by lazy { Day05(Path("input/day05.txt")) }
        private val day05dr by lazy { Day05(Path("input/day05-dr.txt")) }
        private val day05testInput = Day05("""
            47|53
            97|13
            97|61
            97|47
            75|29
            61|13
            75|53
            29|13
            97|29
            53|29
            61|53
            97|53
            61|29
            47|13
            75|47
            97|75
            47|61
            75|61
            47|29
            75|13
            53|13

            75,47,61,53,29
            97,61,53,29,13
            75,29,13
            75,97,47,61,53
            61,13,29
            97,13,75,29,47
        """.trimIndent())
    }

    @Test
    fun part1TestInput() {
        day05testInput.part1() shouldBe 143
    }

    @Test
    fun part2TestInput() {
        day05testInput.part2() shouldBe 123
    }

    @Test
    fun part1dr() {
        day05dr.part1() shouldBe 5452
    }

    @Test
    fun part2dr() {
        day05dr.part2() shouldBe 4598
    }

    @Test
    fun part1() {
        day05.part1() shouldBe 5091
    }

    @Test
    fun part2() {
        day05.part2() shouldBe 4681
    }

}
