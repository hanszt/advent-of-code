package aoc.utils

import aoc.utils.model.GridPoint2D.Companion.by
import aoc.utils.model.gridPoint2D
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class GridPoint2DProgressionTest {

    @Test
    fun testPointProgression() {
        (gridPoint2D(0, 0)..gridPoint2D(3, 3)).toList() shouldBe listOf(0 by 0, 1 by 1, 2 by 2, 3 by 3)
        (gridPoint2D(0, 0)..gridPoint2D(0, 3)).toList() shouldBe listOf(0 by 0, 0 by 1, 0 by 2, 0 by 3)
    }

    @Test
    fun testPointProgressionNotSupported() {
        shouldThrow<IllegalArgumentException> { (gridPoint2D(0, 0)..gridPoint2D(2, 3)) }
    }

}