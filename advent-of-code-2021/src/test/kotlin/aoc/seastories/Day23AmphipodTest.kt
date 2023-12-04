package aoc.seastories

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day23AmphipodTest {

    private val day23Amphipod = Day23Amphipod(testMode = true)

    @Test
    fun `part 1 test input`() {
        day23Amphipod.part1("input/day23test.txt") shouldBe 12_521
    }

    @Test
    fun `least energy rooms three deep`() {
        day23Amphipod.part1("input/day23deep.txt") shouldBe 26_284
    }

    @Test
    fun `part 1 result`() {
        day23Amphipod.part1().also(::println) shouldBe 14_348
    }

    @Test
    fun `part 2 test input`() {
        day23Amphipod.part2("input/day23test.txt") shouldBe 44_169
    }

    @Test
    fun `part 2 result`() {
        day23Amphipod.part2().also(::println) shouldBe 40_954
    }
}
