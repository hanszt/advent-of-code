package aoc.seastories

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day23AmphipodTest {

    private val day23Amphipod = Day23Amphipod(testMode = true)

    @Test
    fun `part 1 test input`() = assertEquals(12_521, day23Amphipod.part1("input/day23test.txt"))

    @Test
    fun `least energy rooms three deep`() = assertEquals(26_284, day23Amphipod.part1("input/day23deep.txt"))

    @Test
    fun `part 1 result`() = assertEquals(14_348, day23Amphipod.part1().also(::println))

    @Test
    fun `part 2 test input`() = assertEquals(44_169, day23Amphipod.part2("input/day23test.txt"))

    @Test
    fun `part 2 result`() = assertEquals(40_954, day23Amphipod.part2().also(::println))
}
