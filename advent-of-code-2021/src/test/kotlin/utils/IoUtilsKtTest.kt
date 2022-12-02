package utils

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class IoUtilsKtTest {

    @Test
    fun testReadLines() {
        val actual = File("input/iotest.txt").readLines().map(String::trim)
        val expected = listOf("hallo", "dit", "is", "een", "test")
        assertEquals(expected, actual)
    }

    @Test
    fun testUseLines() {
        val actual = File("input/iotest.txt").readLines().map { it.trim().length }
        assertEquals(listOf(5, 3, 2, 3, 4), actual)
    }

    @Test
    fun testReadFileTextUsingGetResource() {
        val actual = this::class.java.getResource("/testFiles/test.txt")?.readText() ?: ""
        assertEquals("hallo dit is een test", actual.trim())
    }

    @Test
    fun testReadFileLinesUsingGetResource() {
        val list = this::class.java.getResource("/testFiles/test.txt")
            ?.let { File(it.file).readLines() }
            ?: emptyList()

        assertEquals(listOf("hallo dit is een test"), list)
    }

    @Test
    fun testCreateInputFiles() {
        val directory = File("input")
        val createdFiles = directory.createFilesIfNotPresent(25, "day")
        assertTrue(createdFiles.isNotEmpty())
    }

    @Test
    fun testCreateTestInputFiles() {
        val directory = File("input")
        val createdFiles = directory.createFilesIfNotPresent(25, "day", "test")
        assertTrue(createdFiles.isNotEmpty())
    }

}
