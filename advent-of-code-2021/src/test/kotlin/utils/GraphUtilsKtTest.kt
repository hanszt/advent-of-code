package utils

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

internal class GraphUtilsKtTest {

    @Test
    fun `convert a associations list to a graph`() {
        val graph = File("input/day12.txt").readLines().toBiDiGraph("-").onEach(::println)
        val startNode = graph["start"] ?: throw IllegalStateException()
        assertEquals(startNode.neighbors, setOf(graph["my"], graph["PK"], graph["lj"]))
    }
}
