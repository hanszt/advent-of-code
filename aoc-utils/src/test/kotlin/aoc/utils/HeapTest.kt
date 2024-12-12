package aoc.utils

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class HeapTest {

    @Test
    fun heapTest() {
        val heap = Heap<String, Int>()
        heap.putBetter("1", 1)
        heap.putBetter("2", 4)
        heap.putBetter("3", 3)
        heap.putBetter("4", 165)

        val actual = buildList {
            while (!heap.isEmpty())
                add(heap.removeMin())
        }

        actual.map { it.first } shouldBe listOf("1", "3", "2", "4")
        actual.map { it.second } shouldBe listOf(1, 3, 4, 165)
    }

    @Test
    fun heapTestUniqueKeys() {
        val heap = Heap<String, Int>()
        heap.putBetter("1", 165) shouldBe true
        heap.putBetter("1", 2) shouldBe true
        heap.putBetter("1", 4) shouldBe false
        heap.putBetter("1", 1) shouldBe true
        heap.putBetter("0", 3) shouldBe true

        val actual = buildList {
            while (!heap.isEmpty())
                add(heap.removeMin())
        }

        actual.map { it.first } shouldBe listOf("1", "0")
        actual.map { it.second } shouldBe listOf(1, 3)
    }


}