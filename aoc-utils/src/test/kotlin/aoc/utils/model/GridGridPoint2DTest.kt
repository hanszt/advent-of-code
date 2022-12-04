package aoc.utils.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GridGridPoint2DTest {

    @Test
    fun testPointsEqualWhenSameCoordinate() = assertEquals(GridPoint2D(2, 4), GridPoint2D(2, 4))
}
