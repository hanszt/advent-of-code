package aoc.utils

fun Any.readTextFromResource(path: String) = javaClass.getResource(path)?.readText() ?: "resource not found"
