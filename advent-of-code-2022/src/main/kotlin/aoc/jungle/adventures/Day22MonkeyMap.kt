package aoc.jungle.adventures

import aoc.utils.AocUtils
import aoc.utils.ChallengeDay
import aoc.utils.grouping
import aoc.utils.invoke
import aoc.utils.model.GridPoint2D.Companion.by
import aoc.utils.model.GridPoint2D.Companion.up
import aoc.utils.model.GridPoint2D.Companion.left
import aoc.utils.model.GridPoint2D.Companion.right
import aoc.utils.model.GridPoint2D.Companion.down
import aoc.utils.model.GridPoint3D
import aoc.utils.model.gridPoint2D
import aoc.utils.model.gridPoint3D
import java.io.File
import kotlin.math.sqrt
import aoc.utils.model.GridPoint2D as Point2D

/**
 * Credits to Elizarov
 *
 * @see <a href="https://adventofcode.com/2022/day/22">Day 22</a>
 */
class Day22MonkeyMap(
    fileName: String? = null,
    text: String = File(fileName ?: error("No fileName or text provided")).readText()
) : ChallengeDay {

    private val dirs = listOf(right, down, left, up)
    private val grid: List<String>
    private val instructions: String

    init {
        val (grid, instructionLines) = text.split(AocUtils.DOUBLE_LINE_SEPARATOR)
        this.instructions = instructionLines
        this.grid = grid.lines()
    }

    override fun part1(): Int {
        val initPoint = gridPoint2D(grid[0].indexOf('.').also { check(it >= 0) }, 0)
        return simulate(initPoint, instructions) { x, point, facing -> facing to grid.moveAlongGrid(x, point, facing) }
    }

    override fun part2(): Int {
        val cube = Cube(grid, dirs)
        val initPoint = gridPoint2D(cube.initialFace.y * cube.faceSize, 0)
        return simulate(initPoint, instructions) { x, point, facing -> cube.moveAlongCube(x, point, facing) }
    }

    private fun simulate(
        initPoint: Point2D,
        instructions: String,
        move: (Int, Point2D, Int) -> Pair<Int, Point2D>
    ): Int {
        var finalPoint = initPoint
        var instrIndex = 0
        var finalFacing = 0
        // start walking for solution
        while (instrIndex < instructions.length) {
            when (instructions[instrIndex]) {
                in '0'..'9' -> {
                    var x = instructions[instrIndex++] - '0'
                    while (instrIndex < instructions.length && instructions[instrIndex] in '0'..'9') {
                        x = x * 10 + (instructions[instrIndex++] - '0')
                    }
                    val (nextFacing, nextPoint) = move(x, finalPoint, finalFacing)
                    finalFacing = nextFacing
                    finalPoint = nextPoint
                }

                'L' -> {
                    finalFacing = (finalFacing + 3) % 4
                    instrIndex++
                }

                'R' -> {
                    finalFacing = (finalFacing + 1) % 4
                    instrIndex++
                }

                else -> error(instructions[instrIndex])
            }
        }
        val column = finalPoint.x + 1
        val row = finalPoint.y + 1
        return 1000 * row + 4 * column + finalFacing
    }

    private fun List<String>.moveAlongGrid(x: Int, point: Point2D, facing: Int): Point2D {
        var nextPoint = point
        for (step in 1..x) {
            var d = nextPoint + dirs[facing]
            if (isInGrid(d)) {
                d = when (facing) {
                    0 -> 0 by d.y
                    1 -> d.x by 0
                    2 -> this[nextPoint.y].length - 1 by d.y
                    3 -> d.x by size - 1
                    else -> error(facing)
                }
                while (isInGrid(d)) {
                    d += dirs[facing]
                }
            }
            when (this[d.y][d.x]) {
                '.' -> nextPoint = d
                '#' -> break
                else -> error("!!!")
            }
        }
        return nextPoint
    }

    private fun List<String>.isInGrid(p: Point2D) =
        p.y !in indices || p.x !in this[p.y].indices || this[p.y][p.x] == ' '

    private fun Cube.moveAlongCube(x: Int, point: Point2D, facing: Int): Pair<Int, Point2D> {
        var nextPoint = point
        var nextFacing = facing
        for (step in 1..x) {
            var neighbor = nextPoint + dirs[nextFacing]
            var curFacing = nextFacing
            if (grid.isInGrid(neighbor)) {
                // transition according to the folded cube
                val oriFace = gridPoint2D(nextPoint.y / faceSize, nextPoint.x / faceSize) // the original face

                val connectedFace3D = faceToNeighbors(oriFace)[nextFacing] // basis of the next face if it was connected
                val nextFace = normalsToFaces(connectedFace3D.point3)// next face (look up by the normal)
                val newFace3D = facesToVectors(nextFace) // the actual basis of the new face

                // direction on the new face
                fun Face3D.dir2vertex(index: Int): GridPoint3D = point1 * dirs[index].y + point2 * dirs[index].x
                fun Face3D.vertex2direction(point3D: GridPoint3D): Int = (0..3).single { dir2vertex(it) == point3D }
                curFacing = newFace3D.vertex2direction(connectedFace3D.dir2vertex(nextFacing))
                // compute 3D vector of the offset on the face in the original basis
                val vector =
                    connectedFace3D.point1 * (neighbor.y.mod(faceSize) + 1) + connectedFace3D.point2 * (neighbor.x.mod(faceSize) + 1)

                // project offset in the new basis
                fun flipNeg(s: Int) = if (s < 0) faceSize + 1 + s else s
                neighbor = (nextFace.y * faceSize + flipNeg(vector.dotProduct(newFace3D.point2)) - 1) by
                        (nextFace.x * faceSize + flipNeg(vector.dotProduct(newFace3D.point1)) - 1)
            }
            when (grid[neighbor.y][neighbor.x]) {
                '.' -> {
                    nextPoint = neighbor
                    nextFacing = curFacing
                }

                '#' -> break
                else -> error("!!!")
            }
        }
        return nextFacing to nextPoint
    }

    data class Face3D(val point1: GridPoint3D, val point2: GridPoint3D, val point3: GridPoint3D)

    private class Cube(val grid: List<String>, val dirs: List<Point2D>) {

        val faceSize: Int
        val initialFace: Point2D
        val facesToVectors = HashMap<Point2D, Face3D>() // visited faces to vectors
        val normalsToFaces = HashMap<GridPoint3D, Point2D>() // normals to visited faces
        val faceToNeighbors = HashMap<Point2D, List<Face3D>>() // neighbour faces in all directions

        init {
            val totalCnt = grid.sumOf { r -> r.count { it != ' ' } }
            faceSize = sqrt(totalCnt / 6.0).toInt() // face size
            check(faceSize * faceSize * 6 == totalCnt)
            val faceToCount = buildList {
                for (x in grid.indices) for (y in grid[x].indices) if (grid[x][y] != ' ') {
                    add(gridPoint2D(x / faceSize, y / faceSize))
                }
            }.grouping.eachCount()

            check(faceToCount.values.all { it == faceSize * faceSize })
            initialFace = faceToCount.keys.filter { it.x == 0 }.minBy(Point2D::y)
            val face3D = Face3D(
                point1 = gridPoint3D(1, 0, 0),
                point2 = gridPoint3D(0, 1, 0),
                point3 = gridPoint3D(0, 0, 1)
            )
            dfs(initialFace, face3D, faceToCount)
        }

        fun dfs(face: Point2D, face3D: Face3D, faceToCount: Map<Point2D, Int>) {
            if (face in facesToVectors) return
            facesToVectors[face] = face3D
            normalsToFaces[face3D.point3] = face
            faceToNeighbors[face] = List(4) {
                val rotatedFace3D = when (it) {
                    0 -> Face3D(face3D.point1, face3D.point3, -face3D.point2)
                    1 -> Face3D(face3D.point3, face3D.point2, -face3D.point1)
                    2 -> Face3D(face3D.point1, -face3D.point3, face3D.point2)
                    3 -> Face3D(-face3D.point3, face3D.point2, face3D.point1)
                    else -> error(it)
                }
                val curFace = face + (dirs[it].y by dirs[it].x)
                if (curFace in faceToCount) dfs(curFace, rotatedFace3D, faceToCount) // fold neighbour
                rotatedFace3D
            }
        }
    }
}
