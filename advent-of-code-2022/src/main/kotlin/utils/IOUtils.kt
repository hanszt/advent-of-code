package utils

fun readTextFromResource(path: String) = {}::class.java.getResource(path)?.readText() ?: "resource not found"
