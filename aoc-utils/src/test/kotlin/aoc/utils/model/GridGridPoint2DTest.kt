package aoc.utils.model

import aoc.utils.model.GridPoint2D.Companion.by
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GridGridPoint2DTest {

    @Test
    fun testPointsEqualWhenSameCoordinate() = assertEquals(2 by 4, 2 by 4)
}
