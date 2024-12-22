package aoc.utils

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.*

class HeapTest {

    @Test
    fun heapTest() {
        val heap = Heap<String, Int>()
        heap.put("1", 1)
        heap.put("2", 4)
        heap.put("3", 3)
        heap.put("4", 165)

        val actual = buildList {
            while (!heap.isEmpty())
                add(heap.removeMin())
        }

        actual.map { it.first } shouldBe listOf("1", "3", "2", "4")
        actual.map { it.second } shouldBe listOf(1, 3, 4, 165)
    }

    @Test
    fun heapVsPriorityQueue() {
        data class Robot(val id: String, val age: Int)

        val compareByAge = compareBy<Robot> { it.age }
        val robots = listOf(
            Robot("4", 165),
            Robot("5", 12),
            Robot("2", 4),
            Robot("3", 3),
            Robot("5", 1),
            Robot("1", 123),
            Robot("1", 23),
        )
        val heap = robots.associateByTo(Heap<String, Robot>(compareByAge), Robot::id)

        val priorityQueue = robots.toCollection(PriorityQueue(compareByAge))

        val (id1, robot) = heap.getMin()
        val actual = buildList {
            while (heap.isNotEmpty())
                add(heap.removeMin())
        }
        val robotPq = priorityQueue.peek()
        val pqActual = buildList {
            while (priorityQueue.isNotEmpty())
                add(priorityQueue.remove())
        }

        id1 shouldBe "5"
        robot.age shouldBe 1
        robot shouldBe robotPq
        actual.map { it.first } shouldBe listOf("5", "3", "2", "1", "4")
        actual.map { it.second.age } shouldBe listOf(1, 3, 4, 23, 165)
        pqActual.map { it.age } shouldBe listOf(1, 3, 4, 12, 23, 123, 165)
    }


    @Test
    fun heapTestUniqueKeys() {
        val heap = Heap<String, Int>()
        heap.putBetter("1", 165) shouldBe 165
        heap.putBetter("1", 2) shouldBe 2
        heap.putBetter("1", 4) shouldBe null
        heap.putBetter("1", 1) shouldBe 1
        heap.putBetter("0", 3) shouldBe 3

        val actual = buildList {
            while (!heap.isEmpty())
                add(heap.removeMin())
        }

        actual.map { it.first } shouldBe listOf("1", "0")
        actual.map { it.second } shouldBe listOf(1, 3)
    }

}