package aoc.seastories

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day24ArithmeticLogicUnitTest {

    @Test
    fun `part 1 result`() = Day24ArithmeticLogicUnit.part1().also(::println) shouldBe 99_598_963_999_971

    @Test
    fun `part 1 second input`() = Day24ArithmeticLogicUnit.part1("input/day24-2.txt") shouldBe 94_992_992_796_199

    @Test
    fun `part 2 result`() = Day24ArithmeticLogicUnit.part2().also(::println) shouldBe 93_151_411_711_211

    @Test
    fun `part 2 second input`() = Day24ArithmeticLogicUnit.part2("input/day24-2.txt") shouldBe 11_931_881_141_161

    @Test
    fun `test floor division`() {
        var x = 5
        x /= 3
        println(x)
        x shouldBe 1
    }
}
