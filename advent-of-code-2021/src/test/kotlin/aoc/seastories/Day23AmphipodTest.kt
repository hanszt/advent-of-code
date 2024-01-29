package aoc.seastories

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day23AmphipodTest {

    private val day23Amphipod = Day23Amphipod("input/day23.txt", testMode = true)
    private val day23AmphipodTestInput = Day23Amphipod("input/day23test.txt", testMode = true)

    @Test
    fun `part 1 test input`() {
        day23AmphipodTestInput.part1() shouldBe 12_521
    }

    @Test
    fun `least energy rooms three deep`() {
        Day23Amphipod("input/day23deep.txt", testMode = true).part1() shouldBe 26_284
    }

    @Test
    fun `part 1 result`() {
        day23Amphipod.part1().also(::println) shouldBe 14_348
    }

    @Test
    fun `part 2 test input`() {
        day23AmphipodTestInput.part2() shouldBe 44_169
    }

    @Test
    fun `part 2 result`() {
        day23Amphipod.part2().also(::println) shouldBe 40_954
    }
}
