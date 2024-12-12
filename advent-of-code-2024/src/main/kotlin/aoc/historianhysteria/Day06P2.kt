package aoc.historianhysteria

val RDLU_DIRS: Pair<IntArray, IntArray> = Pair(
    intArrayOf(0, 1, 0, -1),
    intArrayOf(1, 0, -1, 0)
)

/**
 * [Source](https://github.com/elizarov/AdventOfCode2024/blob/main/src/Day06_2.kt)
 */
fun day06P2(input: List<String>): Int {
    val n = input.size
    val m = input[0].length
    var ii = -1
    var jj = -1
    for (i in 0..<n) for (j in 0..<m) if (input[i][j] == '^') {
        ii = i
        jj = j
    }
    val (di, dj) = RDLU_DIRS
    val v = Array(4) { Array(n) { BooleanArray(m) } }
    var cnt = 0
    for (ib in 0..<n) for (jb in 0..<m) {
        v.forEach { w -> w.forEach { it.fill(false) } }
        var i0 = ii
        var j0 = jj
        var d0 = 3
        var found = true
        while (!v[d0][i0][j0]) {
            v[d0][i0][j0] = true
            val i1 = i0 + di[d0]
            val j1 = j0 + dj[d0]
            if (i1 !in 0..<n || j1 !in 0..<m) {
                found = false
                break
            }
            if (input[i1][j1] == '#' || (i1 == ib && j1 == jb)) {
                d0 = (d0 + 1) % 4
                continue
            }
            i0 = i1
            j0 = j1
        }
        if (found) cnt++
    }
    return cnt
}