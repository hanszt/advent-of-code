package utils

import model.GridPoint3D
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Grid3DUtilsKtTest {

    @Test
    fun `test reorient`() {
        val point = GridPoint3D(1, 2, 3)
        val newOrientations = orientations.map { reorient -> reorient(point) }.toList()
        for (orientation in orientations.indices) {
            val newOrientation = point.rotate(orientation)
            println("newOrientation = $newOrientation")
            assertEquals(newOrientations[orientation], newOrientation)
        }
    }

    private fun GridPoint3D.get(i: Int) = when (i) {
        0 -> x
        1 -> y
        2 -> z
        else -> error("$i")
    }

    // from Elizarov
    private fun GridPoint3D.rotate(index: Int): GridPoint3D {
        val c0 = index % 3
        val c0s = 1 - ((index / 3) % 2) * 2
        val c1 = (c0 + 1 + (index / 6) % 2) % 3
        val c1s = 1 - (index / 12) * 2
        val c2 = 3 - c0 - c1
        val c2s = c0s * c1s * (if (c1 == (c0 + 1) % 3) 1 else -1)
        return GridPoint3D(get(c0) * c0s, get(c1) * c1s, get(c2) * c2s)
    }
}
