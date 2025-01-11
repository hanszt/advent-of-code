package aoc.snowrescuemission

import aoc.utils.invoke
import java.util.ArrayDeque

/** ### Credits to Abnew
 *
 * ---
 *
 * ### Original comments:
 *
 * Major inspiration drawn from the following rust solution:
 *
 * [Day 25 rust solution](https://github.com/maneatingape/advent-of-code-rust/blob/main/src/year2023/day25.rs)
 *
 * Did not do the low level optimizations, but roughly followed the high level solution
 *
 * ---
 *
 * ### Comments from Rust solution
 *
 * #### Snow overload
 *
 * We need to find a [minimum cut](https://en.wikipedia.org/wiki/Minimum_cut) of 3 edges that
 * divides the graph into 2 parts. Several general purpose algorithms exist:
 *
 * * Deterministic [Stoer–Wagner algorithm](https://en.wikipedia.org/wiki/Stoer%E2%80%93Wagner_algorithm)
 * * Probabilistic [Karger's algorithm](https://en.wikipedia.org/wiki/Karger%27s_algorithm)
 *
 * The [max-flow min-cut theorem](https://en.wikipedia.org/wiki/Max-flow_min-cut_theorem) also
 * allows the minimum cut to be expressed as a [maximum flow problem](https://en.wikipedia.org/wiki/Maximum_flow_problem).
 * There are several general purpose algorithms:
 *
 * * [Ford–Fulkerson algorithm](https://en.wikipedia.org/wiki/Ford%E2%80%93Fulkerson_algorithm)
 * * [Edmonds–Karp algorithm](https://en.wikipedia.org/wiki/Edmonds%E2%80%93Karp_algorithm)
 *
 * We can use a simplified version of the Edmonds–Karp algorithm taking advantage of two pieces of
 * information and a special property of the input graph structure:
 *
 * * The minimum cut size is already known to be 3.
 * * All edge weights (or flow capacity) are 1.
 * * The 3 edges to be cut are in the "middle" of the graph, that is the graph looks something like:
 *     ```none
 *           * *       * *
 *         * * * * - * * * *
 *       * * * * * - * * * * *
 *         * * * * - * * * *
 *           * *       * *
 *     ```
 *
 * Our high level approach is as follows:
 * * Pick any arbitrary node
 * * Find a start node furthest from it.
 * * Find an end node furthest from the start node.
 *
 * The key insight is that the start and end nodes must be on opposite sides of the cut.
 * We then BFS 3 times from the start to the end to find 3 different shortest paths.
 * We keep track of the edges each time and only allow each edge to be used once so that each
 * path has no common edges.
 *
 * This will "saturate" the 3 edges across the middle. Finally, we BFS from the start node
 * a fourth time. As the middle links are already used, this will only be able to reach the nodes
 * on start's side of the graph and will find our answer.
 *
 * The complexity of each BFS is `O(V + E)` and we perform a total of 6. To speed things up even
 * further some low level optimizations are used:
 *
 * * Numeric node and edge identifiers to allow `vec` to store previously seen values instead
 *   of `HashMap`.
 * * Linked list of path from start to end, stored in a `vec` using indices for simplicity and
 *   cache locality. This [blog post series](https://rust-unofficial.github.io/too-many-lists/)
 *   describes the complexity of using actual references.
 *
 * ---
 */
internal class Day25Abnew(input: List<String>) {
    private val graph = createGraph(input)

    fun part1(): Int {
        val start = bfs(graph.keys.first())
        val end = bfs(start)
        val size = getSize(start, end)
        return size * (graph.size - size)
    }

    private fun createGraph(input: List<String>): Map<String, List<String>> = buildMap<String, MutableList<String>> {
        for (l in input) {
            val (component, connections) = l.split(':')
            this.putIfAbsent(component, ArrayList<String>())
            for (c in connections.trim().split(' ')) {
                this.putIfAbsent(c, ArrayList<String>())
                this(component).add(c)
                this(c).add(component)
            }
        }
    }

    /** BFS across the graph to find the furthest nodes from start. */
    private fun bfs(node: String): String {
        val queue = ArrayDeque<String>()
        val visited = HashSet<String>()
        queue.add(node)
        while (true) {
            val cur = queue.poll()
            for (neighbor in graph(cur)) {
                if (neighbor !in visited) {
                    queue.add(neighbor)
                    visited.add(neighbor)
                }
            }
            if (queue.isEmpty()) {
                return cur
            }
        }
    }

    /** Simplified approach based on Edmonds–Karp algorithm. See top for key insights and sources. */
    private fun getSize(start: String, end: String): Int {
        var result = 0
        val queue = ArrayDeque<Route>()
        val visited = HashSet<String>()
        val usedEdges = HashSet<Edge>()
        repeat(4) {
            result = 0
            queue += Route(start)
            visited += start
            while (queue.isNotEmpty()) {
                result++
                val current = queue.poll()
                if (current.vertex == end) {
                    usedEdges.addAll(current.edges)
                    break
                }
                for (neighbor in graph(current.vertex)) {
                    val e = Edge(current.vertex, neighbor)
                    if (e !in usedEdges && neighbor !in visited) {
                        visited += neighbor
                        queue += Route(neighbor, current)
                    }
                }
            }
            visited.clear()
            queue.clear()
        }
        return result
    }

    internal class Route(val vertex: String, val edges: List<Edge>) {
        constructor(start: String) : this(start, emptyList())
        constructor(next: String, current: Route) : this(next, current.edges + Edge(current.vertex, next))

        override fun toString(): String = "$vertex: $edges"
    }

    internal class Edge(s: String, e: String) {
        val start: String = if (s > e) s else e
        val end: String = if (s > e) e else s

        override fun equals(other: Any?): Boolean = this === other || other is Edge && start == other.start && end == other.end
        override fun hashCode(): Int = arrayOf(start, end).contentHashCode()
    }
}