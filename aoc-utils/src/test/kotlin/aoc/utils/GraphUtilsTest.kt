package aoc.utils

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

internal class GraphUtilsTest {

    @Test
    fun `convert an associations list to a graph`() {
        val name = "/2021day12.txt"
        val resource = this::class.java.getResource(name) ?: error("$name not found")
        val graph = File(resource.file).readLines().toBiDiGraph("-").onEach(::println)
        val startNode = graph["start"] ?: error("start not found")
        assertEquals(startNode.neighbors, setOf(graph["my"], graph["PK"], graph["lj"]))
    }
}
