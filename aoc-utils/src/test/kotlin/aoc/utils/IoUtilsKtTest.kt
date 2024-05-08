package aoc.utils

import io.kotest.matchers.shouldBe
import java.io.File
import org.junit.jupiter.api.Test

internal class IoUtilsKtTest {

    @Test
    fun `test read lines`() {
        val actual = File("input/iotest.txt").readLines().map(String::trim)
        val expected = listOf("hallo", "dit", "is", "een", "test")
        actual shouldBe expected
    }

    @Test
    fun `test use lines`() {
        val actual = File("input/iotest.txt").useLines {
            it.map(String::trim)
            .map(String::length)
            .toList()
        }
        actual shouldBe listOf(5, 3, 2, 3, 4)
    }

    @Test
    fun `test read text from resource file`() {
        val actual = this::class.java.getResource("/test.txt")?.readText() ?: ""
        actual.trim() shouldBe "hello this is a test"
    }

    @Test
    fun `test read lines from resource`() {
        val list = this::class.java.getResource("/test.txt")
            ?.let { File(it.file).readLines() }
            ?: emptyList()

        list shouldBe listOf("hello this is a test")
    }

}
