package aoc.utils

import java.io.File
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.PathWalkOption.BREADTH_FIRST
import kotlin.io.path.PathWalkOption.INCLUDE_DIRECTORIES
import kotlin.io.path.absolutePathString
import kotlin.io.path.name
import kotlin.io.path.walk

fun Any.readTextFromResource(path: String, orElse: String = "resource not found") =
    javaClass.getResource(path)?.readText() ?: orElse

fun Any.resourcePath(path: String): Path? = javaClass.getResource(path)?.let { File(it.file).toPath() }

fun <R> File.mapLines(mapper: (String) -> R): List<R> = useLines { it.map(mapper).toList() }

@OptIn(ExperimentalPathApi::class)
fun Any.relativeToResources(resourcePath: String, rootFileName: String, inputFileName: String): String =
    (resourcePath(resourcePath) ?: error("Resource $resourcePath not found"))
        .parents
        .first { it.fileName.name.endsWith(rootFileName) }
        .walk(BREADTH_FIRST, INCLUDE_DIRECTORIES)
        .first { it.fileName.name == inputFileName }
        .absolutePathString()

val Path.parents: Sequence<Path>
    get() = sequence {
        var path = this@parents.toAbsolutePath()
        while (path.parent != null) {
            yield(path)
            path = path.parent
        }
    }
