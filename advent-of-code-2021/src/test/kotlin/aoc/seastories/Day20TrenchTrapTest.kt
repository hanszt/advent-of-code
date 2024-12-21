package aoc.seastories

import aoc.utils.*
import aoc.utils.grid2d.gridAsString
import io.kotest.matchers.shouldBe
import java.io.File
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.random.Random
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class Day20TrenchTrapTest {

    private val day20TrenchTrap = Day20TrenchTrap("input/day20.txt")

    private val resultImage by lazy { with(day20TrenchTrap) { File("input/day20.txt").toEnhancedImage() } }

    @Test
    fun `part 1 test input`() {
        Day20TrenchTrap("input/day20test.txt").part1() shouldBe 35
    }

    @Test
    fun `part 1 result`() {
        day20TrenchTrap.part1() shouldBe 5_291
    }

    @Test
    fun `part 2 test input`() {
        Day20TrenchTrap("input/day20test.txt").part2() shouldBe 3_351
    }

    @Test
    fun `part 2 result`() {
        resultImage.flatMap(IntArray::toList).count { it == 1 } shouldBe 16_665
    }

    @Test
    fun `part 2 print resulting image`() {
        val result = resultImage.gridAsString {
            if (it == 1) "#".withColors(GREEN, BROWN_BG)
            else ".".withColor(random16BitColor(Random)) }
        println(result)
        assertTrue { result.isNotBlank() }
    }
}
