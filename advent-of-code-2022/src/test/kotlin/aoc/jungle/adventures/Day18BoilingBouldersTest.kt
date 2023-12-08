package aoc.jungle.adventures

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day18BoilingBouldersTest {

    private val day18BoilingBoulders = Day18BoilingBoulders("input/day18.txt")

    @Test
    fun `test part 1`() {
        day18BoilingBoulders.part1() shouldBe 4_370
    }

    @Test
    fun `test part 1 test`() {
        Day18BoilingBoulders("input/day18test.txt").part1() shouldBe 64
    }

    @Test
    fun `test part 2`() {
        day18BoilingBoulders.part2() shouldBe 2_458
    }

    @Test
    fun `test part 2 test`() {
        Day18BoilingBoulders("input/day18test.txt").part2() shouldBe 58
    }
}
