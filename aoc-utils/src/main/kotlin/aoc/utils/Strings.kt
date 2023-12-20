package aoc.utils

operator fun String.component1(): Char = this[0]
operator fun String.component2(): Char = this[1]
operator fun String.component3(): Char = this[2]
operator fun String.component4(): Char = this[3]
operator fun String.component5(): Char = this[4]

fun <R> CharSequence.mapLines(mapper: (String) -> R): List<R> = lineSequence().map(mapper).toList()

