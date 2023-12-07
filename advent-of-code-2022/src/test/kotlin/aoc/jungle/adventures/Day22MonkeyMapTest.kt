package aoc.jungle.adventures

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day22MonkeyMapTest {

    @Test
    fun `test part 1 test input`() {
        Day22MonkeyMap("input/day22test.txt").part1() shouldBe 6_032
    }

    @Test
    fun `test part 1`() {
        Day22MonkeyMap("input/day22.txt").part1() shouldBe 136_054
    }

    @Test
    fun `test part 2 test input`() {
        Day22MonkeyMap("input/day22test.txt").part2() shouldBe 5_031
    }

    @Test
    fun `test part 2`() {
        Day22MonkeyMap("input/day22.txt").part2() shouldBe 122_153
    }
}
