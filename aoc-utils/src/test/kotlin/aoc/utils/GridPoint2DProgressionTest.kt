package aoc.utils

import aoc.utils.grid2d.GridPoint2D.Companion.by
import aoc.utils.grid2d.gridPoint2D
import aoc.utils.grid2d.rangeTo
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class GridPoint2DProgressionTest {

    @Test
    fun testPointProgression() {
        (gridPoint2D(0, 0)..gridPoint2D(3, 3)).toList() shouldBe listOf(0 by 0, 1 by 1, 2 by 2, 3 by 3)
        (gridPoint2D(0, 0)..gridPoint2D(0, 3)).toList() shouldBe listOf(0 by 0, 0 by 1, 0 by 2, 0 by 3)
        (gridPoint2D(0, 0)..gridPoint2D(1, 3)).toList() shouldBe listOf(0 by 0, 1 by 1, 1 by 2, 1 by 3)
        (gridPoint2D(1, 3)..gridPoint2D(0, 0)).toList() shouldBe listOf(1 by 3, 0 by 2, 0 by 1, 0 by 0)
    }

    @Test
    fun testPointRangeContains() {
        (gridPoint2D(0, 0)..gridPoint2D(3, 3)).contains(gridPoint2D(0, 0)) shouldBe true
        (gridPoint2D(0, 0)..gridPoint2D(0, 3)).contains(gridPoint2D(0, 3)) shouldBe true
        (gridPoint2D(0, 3)..gridPoint2D(0, 0)).contains(gridPoint2D(0, 3)) shouldBe true
        (gridPoint2D(0, 0)..gridPoint2D(1, 3)).contains(gridPoint2D(1, 3)) shouldBe true
    }

    @Test
    fun testPointRangeDoesNotContain() {
        (gridPoint2D(0, 0)..gridPoint2D(3, 3)).contains(gridPoint2D(4, 0)) shouldBe false
        (gridPoint2D(0, 0)..gridPoint2D(0, 3)).contains(gridPoint2D(1, 3)) shouldBe false
        (gridPoint2D(0, 0)..gridPoint2D(1, 3)).contains(gridPoint2D(2, 3)) shouldBe false
    }

}