package aoc.utils

fun Any.readTextFromResource(path: String, orElse: String = "resource not found") =
    javaClass.getResource(path)?.readText() ?: orElse
