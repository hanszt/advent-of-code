package aoc.utils

import aoc.utils.model.GridPoint3D.Companion.ZERO
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import aoc.utils.model.gridPoint3D as p3d

class GridPoint3DProgressionTest {

    @Test
    fun testPointProgression() {
        (ZERO..p3d(3, 3, 3)).toList() shouldBe listOf(ZERO, p3d(1, 1, 1), p3d(2, 2, 2), p3d(3, 3, 3))
        (ZERO..p3d(0, 3, 3)).toList() shouldBe listOf(ZERO, p3d(0, 1, 1), p3d(0, 2, 2), p3d(0, 3, 3))
        (ZERO..p3d(1, 3, 3)).toList() shouldBe listOf(ZERO, p3d(1, 1, 1), p3d(1, 2, 2), p3d(1, 3, 3))
    }

}