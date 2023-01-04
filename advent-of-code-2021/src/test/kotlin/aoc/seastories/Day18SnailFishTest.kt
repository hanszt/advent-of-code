package aoc.seastories

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day18SnailFishTest {

    @Test
    fun `part 1 test input`() = assertEquals(4_140, Day18SnailFish.part1("input/day18test.txt"))

    @Test
    fun `part 1 result`() = assertEquals(3_486, Day18SnailFish.part1().also(::println))

    @Test
    fun `part 2 test input`() = assertEquals(3_993, Day18SnailFish.part2("input/day18test.txt"))

    @Test
    fun `part 2 result`() = assertEquals(4_747, Day18SnailFish.part2().also(::println))

    @ParameterizedTest(name = "{0} should reduce to: {1}")
    @CsvSource(
        value = [
            "[[[[[9,8],1],2],3],4] -> [[[[0,9],2],3],4]",
            "[7,[6,[5,[4,[3,2]]]]] -> [7,[6,[5,[7,0]]]]",
            "[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]] -> [[[[0,7],4],[[7,8],[6,0]]],[8,1]]"
        ], delimiterString = " -> "
    )
    fun `test reduce nr`(snailNr: String, expected: String) {
        val toSnailNr = Day18SnailFish.toSnailNr(snailNr)
        println(toSnailNr.toTreeString(1))
        println()
        val reducedNr = toSnailNr.reduce()
        assertEquals(expected, reducedNr.toString())
    }

    @ParameterizedTest(name = "{0} should have a magnitude of {1}")
    @CsvSource(
        value = [
            "[[1,2],[[3,4],5]] -> 143",
            "[[[[0,7],4],[[7,8],[6,0]]],[8,1]] -> 1384",
            "[[[[1,1],[2,2]],[3,3]],[4,4]] -> 445",
            "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]] -> 3488"],
         delimiterString = " -> "
    )
    fun `calculate magnitude of snail numbers`(snailNr: String, expected: Int) {
        val magnitude = Day18SnailFish.toSnailNr(snailNr).magnitude()
        assertEquals(expected, magnitude)
    }

    @ParameterizedTest(name = "{0} = {1}")
    @CsvSource(
        value = [
            "[[[[4,3],4],4],[7,[[8,4],9]]] + [1,1] = [[[[0,7],4],[[7,8],[6,0]]],[8,1]]",
            "[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]] + [7,[[[3,7],[4,3]],[[6,3],[8,8]]]] = [[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]",
            "[[[[6,6],[6,6]],[[6,0],[6,7]]],[[[7,7],[8,9]],[8,[8,1]]]] + [2,9] = [[[[6,6],[7,7]],[[0,7],[7,7]]],[[[5,5],[5,6]],9]]"
        ], delimiter = '='
    )
    fun `add two snail nrs`(nrsToAdd: String, expected: String) {
        val result = nrsToAdd.split(" + ")
            .map(Day18SnailFish::toSnailNr)
            .reduce(Day18SnailFish.SnailNr::plus)

        assertEquals(expected, result.toString())
    }
}
