package aoc.jungle.adventures

fun day19(input: List<String>, part: Int): Int {
    var ans = if (part == 1) 0 else 1
    val tl = if (part == 1) 24 else 32
    val robots = listOf("ore", "clay", "obsidian", "geode")
    var totalStates = 0
    for ((index, line) in input.withIndex()) {
        val name = "Blueprint ${index + 1}: "
        check(line.startsWith(name))
        val rule = line.substringAfter(": ").split(".")
        val costTable = robots.createCostTable(rule)
        val max0 = costTable.maxOf(IntArray::first)
        var cs = LHS()
        var ns = LHS()
        cs.add(1L)
        fun produce(n: Long): Long {
            var count = n
            for (j in robots.indices) {
                count = count.plus(robots.size + j, n[j])
            }
            return count
        }
        for (t in 1..tl) {
            val rem = tl - t
            fun Long.gg(): Int {
                val g = this[2 * robots.size - 1]
                val gr = this[robots.size - 1]
                return g + rem * gr
            }

            var bestGG = -1
            cs.forEach { bestGG = maxOf(bestGG, it.gg()) }
            cs.forEach { s ->
                totalStates++
                fun addNext(n: Long) {
                    val g = n[2 * robots.size - 1]
                    val gr = n[robots.size - 1]
                    val gg = g + rem * gr
                    val estG = gg + rem * (rem - 1) / 2
                    if (estG <= bestGG) return
                    ns.add(n)
                }

                var canBuildCnt = 0
                val isMax0 = s[0] >= max0
                val i0 = if (isMax0) 1 else 0
                build@ for (i in i0 until robots.size) {
                    val d = costTable[i]
                    for (j in robots.indices) {
                        if (s[robots.size + j] < d[j]) {
                            continue@build
                        }
                    }
                    canBuildCnt++
                    var n = s
                    for (j in robots.indices) {
                        n = n.minus(robots.size + j, d[j])
                    }
                    n = produce(n)
                    n = n.plus(i, 1)
                    addNext(n)
                }
                if (canBuildCnt < robots.size - i0) {
                    addNext(produce(s))
                }
            }
            cs = ns.also { ns = cs }
            ns.clear()
        }
        var best = 0
        cs.forEach { s ->
            best = maxOf(best, s[2 * robots.size - 1])
        }
        if (part == 1) {
            ans += (index + 1) * best
        } else {
            ans *= best
            if (index == 2) break
        }
    }
    return ans
}

private fun List<String>.createCostTable(rules: List<String>): Array<IntArray> {
    val robotCosts = Array(size) { i ->
        val prefix = "Each ${this[i]} robot costs "
        val rule = rules[i].trim()
        check(rule.startsWith(prefix)) { "$rule, expect=$prefix" }
        val rawMaterial = rule.substring(prefix.length).split(" ")
        val costs = IntArray(size)
        for (j in 1 until rawMaterial.size) {
            if (rawMaterial[j] in this) {
                val index = this.indexOf(rawMaterial[j])
                costs[index] = rawMaterial[j - 1].toInt()
            }
        }
        costs
    }
    return robotCosts
}

private const val SH = 6
private const val MASK = (1L shl SH) - 1
private operator fun Long.get(i: Int): Int = ((this shr (i * SH)) and MASK).toInt()
private fun Long.plus(i: Int, d: Int): Long = this + (d.toLong() shl (i * SH))
private fun Long.minus(i: Int, d: Int): Long = this - (d.toLong() shl (i * SH))

/**
 * [Where do "magic" hashing constants like 0x9e3779b9 and 0x9e3779b1 come from?](https://softwareengineering.stackexchange.com/questions/402542/where-do-magic-hashing-constants-like-0x9e3779b9-and-0x9e3779b1-come-from)
 *
 * 0x9e3779b9 is the integral part of the Golden Ratio's fractional part 0.61803398875… (sqrt(5)-1)/2, multiplied by 2^32.
 *
 * Hence, if φ = (sqrt(5)+1)/2 = 1.61803398875 is the Golden Ratio, the hash function calculates the fractional part of n * φ, which has nice scattering properties.
 */
private const val HASH_CONSTANT = 0x9E3779B9.toInt()
private const val TX = ((2 / 3.0) * (1L shl 32)).toLong().toInt()

class LHS(private var shift: Int = 29) {
    var a = LongArray(1 shl (32 - shift))
    var size = 0
    private var tx = TX ushr shift

    fun clear() {
        a.fill(0L)
        size = 0
    }

    fun add(k: Long) {
        require(k != 0L)
        if (size >= tx) {
            rehash()
        }
        val hc0 = k.toInt() * 31 + (k ushr 32).toInt()
        var i = (hc0 * HASH_CONSTANT) ushr shift
        while (true) {
            if (a[i] == 0L) {
                a[i] = k
                size++
                return
            }
            if (a[i] == k) return
            if (i == 0) i = a.size
            i--
        }
    }

    inline fun forEach(op: (Long) -> Unit) {
        for (i in a.indices) {
            if (a[i] != 0L) {
                op(a[i])
            }
        }
    }

    private fun rehash() {
        val b = LHS(shift - 1)
        forEach { k -> b.add(k) }
        check(b.size == size)
        shift = b.shift
        a = b.a
        tx = b.tx
    }
}
