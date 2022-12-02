package utils

import java.io.File

fun File.createFilesIfNotPresent(
    amount: Int,
    name: String,
    postFix: String = "",
    fileExtension: String = "txt"
): Array<out File> {
    return if (isDirectory) {
        (1..amount).map { nr -> resolve("$name$nr$postFix.$fileExtension") }.forEach(File::createNewFile)
        listFiles() ?: emptyArray()
    } else throw IllegalStateException("$this is not a directory")
}

fun readTextFromResource(path: String) = {}::class.java.getResource(path)?.readText() ?: "resource not found"
