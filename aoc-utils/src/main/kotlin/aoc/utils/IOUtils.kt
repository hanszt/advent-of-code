package aoc.utils

import java.io.File

fun Any.readTextFromResource(path: String, orElse: String = "resource not found") =
    javaClass.getResource(path)?.readText() ?: orElse

fun <R> File.mapLines(mapper: (String) -> R): List<R> = useLines { it.map(mapper).toList() }
