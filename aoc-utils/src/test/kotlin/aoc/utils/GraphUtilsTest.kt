package aoc.utils

import aoc.utils.graph.toBiDiGraph
import io.kotest.matchers.shouldBe
import java.io.File
import org.junit.jupiter.api.Test

internal class GraphUtilsTest {

    @Test
    fun `convert an associations list to a graph`() {
        val name = "/2021day12.txt"
        val resource = this::class.java.getResource(name) ?: error("$name not found")
        val graph = File(resource.file).readLines().toBiDiGraph("-").onEach(::println)
        val startNode = graph["start"] ?: error("start not found")
        setOf(graph["my"], graph["PK"], graph["lj"]) shouldBe startNode.neighbors
    }
}
