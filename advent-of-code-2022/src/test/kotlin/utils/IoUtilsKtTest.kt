package utils

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

internal class IoUtilsKtTest {

    @Test
    fun `test read lines`() {
        val actual = File("input/iotest.txt").readLines().map(String::trim)
        val expected = listOf("hallo", "dit", "is", "een", "test")
        assertEquals(expected, actual)
    }

    @Test
    fun `test use lines`() {
        val actual = File("input/iotest.txt").useLines {
            it.map(String::trim)
            .map(String::length)
            .toList()
        }
        assertEquals(listOf(5, 3, 2, 3, 4), actual)
    }

    @Test
    fun `test read text from resource file`() {
        val actual = this::class.java.getResource("/test.txt")?.readText() ?: ""
        assertEquals("hallo dit is een test", actual.trim())
    }

    @Test
    fun `test read lines from resource`() {
        val list = this::class.java.getResource("/test.txt")
            ?.let { File(it.file).readLines() }
            ?: emptyList()

        assertEquals(listOf("hallo dit is een test"), list)
    }

}
