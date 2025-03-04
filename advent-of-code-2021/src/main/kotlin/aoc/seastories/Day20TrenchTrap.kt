package aoc.seastories

import aoc.utils.grid2d.forEachPoint
import aoc.utils.splitByBlankLine
import aoc.utils.grid2d.toMutableIntGrid
import java.io.File

internal class Day20TrenchTrap(private val inputPath: String) : ChallengeDay {

    override fun part1(): Int = File(inputPath).toAlgoAndImage()
        .let { (algo, image) -> image.enhance(algo, 2).sumOf { it.count { char -> char == 1 } } }

    private fun Array<IntArray>.enhance(algo: List<Int>, times: Int): Array<IntArray> =
        (0 until times).fold(this) { image, step -> image.enhance(step, algo) }

    private fun Array<IntArray>.enhance(step: Int, algo: List<Int>): Array<IntArray> {
        val pixelValueOutsideImage = if (algo[0] == 1 && step % 2 != 0) 1 else 0
        val enhanced = Array(this.size + 2) { IntArray(first().size + 2) { pixelValueOutsideImage } }
        this.forEachPoint { x, y -> enhanced[y + 1][x + 1] = this[y][x] }
        val map = buildMap {
            for (row in enhanced.indices) {
                for (col in 0 until enhanced.first().size) {
                    val index = enhanced.findIndex(row, col, pixelValueOutsideImage)
                    this[row to col] = algo[index]
                }
            }
        }
        enhanced.forEachPoint { x, y -> enhanced[y][x] = map.getOrDefault(y to x, 0) }
        return enhanced
    }

    private fun Array<IntArray>.findIndex(row: Int, col: Int, pixelOutsideImage: Int) =
        ((row - 1)..(row + 1)).flatMap { y ->
            ((col - 1)..(col + 1)).map { x ->
                getOrNull(y)?.getOrNull(x) ?: pixelOutsideImage
            }
        }.joinToString("") { it.toString() }.toInt(radix = 2)

    fun File.toEnhancedImage() = toAlgoAndImage().let { (algo, image) -> image.enhance(algo, 50) }

    override fun part2(): Int = File(inputPath).toEnhancedImage().flatMap(IntArray::toList).count { it == 1 }

    private fun File.toAlgoAndImage(): Pair<List<Int>, Array<IntArray>> {
        val (enhancementAlgo, imageAsString) = readText().splitByBlankLine()
        val algo = enhancementAlgo.trim().lines().flatMap { it.toList().map { char -> if (char == '#') 1 else 0 } }
        val image = imageAsString.lines().toMutableIntGrid { char -> if (char == '#') 1 else 0 }
        return Pair(algo, image)
    }
}
