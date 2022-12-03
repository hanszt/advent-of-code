package aoc.utils

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

internal class GraphUtilsKtTest {

    @Test
    fun `convert an associations list to a graph`() {
        val resource = this::class.java.getResource("/2021day12.txt") ?: throw IllegalStateException()
        val graph = File(resource.file).readLines().toBiDiGraph("-").onEach(::println)
        val startNode = graph["start"] ?: throw IllegalStateException()
        assertEquals(startNode.neighbors, setOf(graph["my"], graph["PK"], graph["lj"]))
    }
}
