package aoc.spacesaga

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Day01TheTyrannyOfTheRocketEquationTest {

    private val day01TheTyrannyOfTheRocketEquation = Day01TheTyrannyOfTheRocketEquation("input/day01.txt")

    @Test
    fun `test part 1`() = day01TheTyrannyOfTheRocketEquation.part1() shouldBe 3_391_707

    @Test
    fun `test part 2`() = day01TheTyrannyOfTheRocketEquation.part2() shouldBe 5_084_676

    @ParameterizedTest(name = "A module of mass {0} should require {1} fuel")
    @CsvSource(value = ["12, 2", "1969, 966", "100756, 50346"])
    fun `test calculate compound fuel`(mass: Int, expectedFuel: Int) =
        day01TheTyrannyOfTheRocketEquation.compoundFuel(mass) shouldBe expectedFuel
}
