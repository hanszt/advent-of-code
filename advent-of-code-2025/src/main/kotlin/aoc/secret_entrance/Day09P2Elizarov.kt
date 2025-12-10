package aoc.secret_entrance

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.abs
import aoc.utils.grid2d.GridPoint2D as P

object Day09P2Elizarov {

    fun run(lines: List<String>): Long {
        val p = lines.map { s ->
            val (x, y) = s.split(",").map { it.toInt() }
            P(x, y)
        }
        val n = p.size
        val xs = p.map { it.x }.distinct().sorted().toIntArray()
        val ys = p.map { it.y }.distinct().sorted().toIntArray()
        val xm = xs.withIndex().associate { (i, x) -> x to i }
        val ym = ys.withIndex().associate { (i, y) -> y to i }
        val a = Array(ys.size) { CharArray(xs.size) { ' ' } }
        for (i in 0..<n) {
            val xi = xm[p[i].x]!!
            val yi = ym[p[i].y]!!
            check(a[yi][xi] == ' ')
            val j1 = (i + 1) % n
            val j2 = (i + n - 1) % n
            val j = if (p[j1].x == p[i].x) j1 else j2
            check(p[j].x == p[i].x)
            a[yi][xi] = if (p[j].y > p[i].y) 'v' else '^'
        }
        for (i in 0..<n) {
            val j = (i + 1) % n
            when {
                p[i].x == p[j].x -> {
                    val x = xm[p[i].x]!!
                    val yi = ym[p[i].y]!!
                    val yj = ym[p[j].y]!!
                    val y1 = minOf(yi, yj)
                    val y2 = maxOf(yi, yj)
                    for (y in y1 + 1..<y2) {
                        check(a[y][x] == ' ')
                        a[y][x] = '|'
                    }
                }

                p[i].y == p[j].y -> {
                    val y = ym[p[i].y]!!
                    val xi = xm[p[i].x]!!
                    val xj = xm[p[j].x]!!
                    val x1 = minOf(xi, xj)
                    val x2 = maxOf(xi, xj)
                    for (x in x1 + 1..<x2) {
                        check(a[y][x] == ' ')
                        a[y][x] = '-'
                    }
                }

                else -> error("$i -> $j")
            }
        }
        fun dump() {
            println("=".repeat(xs.size))
            for (y in ys.indices) {
                for (x in xs.indices) {
                    print(a[y][x])
                }
                println()
            }
        }
        dump()
        for (y in ys.indices) {
            var c = ' '
            var inside = false
            for (x in xs.indices) {
                val wasX = inside || c != ' '
                when (a[y][x]) {
                    '|' -> inside = !inside
                    'v' -> when (c) {
                        ' ' -> c = 'v'
                        'v' -> c = ' '
                        '^' -> {
                            c = ' '; inside = !inside
                        }
                    }

                    '^' -> when (c) {
                        ' ' -> c = '^'
                        '^' -> c = ' '
                        'v' -> {
                            c = ' '; inside = !inside
                        }
                    }
                }
                if ((wasX || inside || c != ' ') && a[y][x] == ' ') a[y][x] = 'X'
            }
            check(!inside)
        }
        dump()
        dumpPng(a, "Day09.png")
        var ans = 0L
        for (i in 0..<n) for (j in i + 1..<n) {
            val xi = xm[p[i].x]!!
            val xj = xm[p[j].x]!!
            val x1 = minOf(xi, xj)
            val x2 = maxOf(xi, xj)
            val yi = ym[p[i].y]!!
            val yj = ym[p[j].y]!!
            val y1 = minOf(yi, yj)
            val y2 = maxOf(yi, yj)
            var ok = true
            check@ for (x in x1..x2) for (y in y1..y2) if (a[y][x] == ' ') {
                ok = false
                break@check
            }
            if (ok) {
                val a = (abs(p[i].x - p[j].x) + 1L) * (abs(p[i].y - p[j].y) + 1L)
                ans = maxOf(ans, a)
            }
        }
        return ans
    }

    fun dumpPng(a: Array<CharArray>, fileName: String) {
        val m = a[0].size
        val n = a.size
        val img = BufferedImage(m, n, BufferedImage.TYPE_INT_RGB)
        for (y in 0..<n) for (x in 0..<m) {
            val c = when (a[y][x]) {
                'v', '^' -> Color.RED
                'X' -> Color.GREEN
                '-', '|' -> Color.GREEN.darker().darker()
                else -> Color.WHITE
            }
            img.setRGB(x, y, c.rgb)
        }
        ImageIO.write(img, "png", File(fileName))
    }
}
