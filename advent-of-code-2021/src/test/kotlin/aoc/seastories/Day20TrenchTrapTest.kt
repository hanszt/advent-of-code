package aoc.seastories

import aoc.seastories.Day20TrenchTrap.toEnhancedImage
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import aoc.utils.*
import java.io.File
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class Day20TrenchTrapTest {

    private lateinit var resultImage: Array<IntArray>

    @BeforeAll
    fun init() {
        resultImage = File("input/day20.txt").toEnhancedImage()
    }

    @Test
    fun `part 1 test input`() = assertEquals(35, Day20TrenchTrap.part1("input/day20test.txt"))

    @Test
    fun `part 1 result`() = assertEquals(5_291, Day20TrenchTrap.part1().also(::println))

    @Test
    fun `part 2 test input`() = assertEquals(3_351, Day20TrenchTrap.part2("input/day20test.txt"))

    @Test
    fun `part 2 result`() = assertEquals(16_665, resultImage.flatMap(IntArray::toList).count { it == 1 }.also(::println))

    @Test
    fun `part 2 print resulting image`() {
        val result = resultImage.gridAsString {
            if (it == 1) "#".withColors(GREEN, BROWN_BG)
            else ".".withColor(random16BitColor()) }
        println(result)
        assertTrue { result.isNotBlank() }
    }
}
