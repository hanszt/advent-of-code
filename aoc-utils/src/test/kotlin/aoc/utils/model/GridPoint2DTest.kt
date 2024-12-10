package aoc.utils.model

import aoc.utils.model.GridPoint2D.Companion.by
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GridPoint2DTest {

    @Test
    fun testPointsEqualWhenSameCoordinate() {
        2 by 4 shouldBe (2 by 4)
    }
}
