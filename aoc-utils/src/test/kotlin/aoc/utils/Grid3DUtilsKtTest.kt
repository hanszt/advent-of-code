package aoc.utils

import aoc.utils.grid3d.GridPoint3D
import aoc.utils.grid3d.gridPoint3D
import aoc.utils.grid3d.rotations
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Grid3DUtilsKtTest {

    @Test
    fun `test reorient`() {
        val point = gridPoint3D(1, 2, 3)
        val newOrientations = rotations.map { reorient -> reorient(point) }.toList()

        for (i in rotations.indices) {
            val newOrientation = point.rotate(i)
            println("newOrientation = $newOrientation")
            newOrientations[i] shouldBe newOrientation
        }
    }

    // from Elizarov
    private fun GridPoint3D.rotate(index: Int): GridPoint3D {
        fun GridPoint3D.get(i: Int) = when (i) {
            0 -> x
            1 -> y
            2 -> z
            else -> error("$i")
        }
        val c0 = index % 3
        val c0s = 1 - ((index / 3) % 2) * 2
        val c1 = (c0 + 1 + (index / 6) % 2) % 3
        val c1s = 1 - (index / 12) * 2
        val c2 = 3 - c0 - c1
        val c2s = c0s * c1s * (if (c1 == (c0 + 1) % 3) 1 else -1)
        return gridPoint3D(get(c0) * c0s, get(c1) * c1s, get(c2) * c2s)
    }
}
