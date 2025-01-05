package aoc.historianhysteria

import aoc.utils.invoke
import aoc.utils.parts

class Day24Zebalu(input: List<String>) {
    private val values: Map<String, Int>
    private val rules: List<Rule>
    private val bitMap: Map<Char, MutableList<String>>

    init {
        fun MutableMap<Char, MutableList<String>>.addToBitMap(key: String) {
            compute(key[0]) { _, v ->
                if (v == null) {
                    ArrayList<String>(listOf(key))
                } else {
                    if (v.binarySearch(key) < 0) {
                        v.add(key)
                        v.sort()
                    }
                    v
                }
            }
        }

        val groups = input.parts { it }
        val vals = HashMap<String, Int>()
        bitMap = buildMap {
            for (initial in groups.first()) {
                val parts = initial.split(": ")
                vals.put(parts[0], parts[1].toInt())
                if (BIT_PATTERN.matches(parts[0])) {
                    addToBitMap(parts[0])
                }
            }
            rules = groups.last().map(Rule::parse)
            rules.filter { it.result.startsWith("z") }.forEach { addToBitMap(it.result) }
        }
        values = vals
        require(bitMap('x').size == bitMap('y').size) { "Invalid input! We should have as many Xes as many Ys!" }
    }

    fun part1(): Long = execute(HashMap(values), rules).toLong(radix = 2)

    fun part2(): String {
        val allBits = rules.asSequence()
            .flatMap { listOf(it.first, it.second, it.result) }
            .filter(::isBit)
            .toSet()
        val xs = allBits.filter { it.startsWith("x") }
        val zs = allBits.filter { it.startsWith("z") }
        val swapped: MutableList<String> = ArrayList<String>()
        var carry: String? = null
        for (i in xs.indices) {
            val xKey = String.format("x%02d", i)
            val yKey = String.format("y%02d", i)
            var outputs = fullAdder(xKey, yKey, carry, rules, swapped)
            if (outputs.last().startsWith("z") && (outputs.last() != "z" + (zs.size - 1))) {
                outputs = outputs.reversed()
                swapped.add(outputs.first())
                swapped.add(outputs.last())
            }
            carry = outputs.last()
        }
        return swapped.sorted().joinToString(",")
    }

    private fun isBit(str: String): Boolean = BIT_PATTERN.matches(str)

    private fun execute(copyOfValues: MutableMap<String, Int>, rules: List<Rule>): String {
        var changed = true
        val copy = ArrayDeque<Rule>(rules)
        while (copy.isNotEmpty() && changed) {
            changed = false
            val it = copy.iterator()
            while (it.hasNext()) {
                val next = it.next()
                if (next.canBeCalculated(copyOfValues)) {
                    next.calculate(copyOfValues)
                    it.remove()
                    changed = true
                }
            }
        }
        return if (copy.isNotEmpty()) {
            "1".repeat(63)
        } else {
            copyOfValues.entries.asSequence()
                .filter { it.key.startsWith("z") }
                .sortedByDescending { it.key }
                .joinToString("") { it.value.toString() }
        }
    }

    @JvmRecord
    data class Rule(val first: String, val second: String, val result: String, val operator: Op) {

        fun canBeCalculated(values: Map<String, Int>): Boolean = values.containsKey(first) &&
                values.containsKey(second) &&
                !values.containsKey(result)

        fun calculate(values: MutableMap<String, Int>) {
            values.put(result, operator(values(first), values(second)))
        }

        val isTop: Boolean get() = result.startsWith("z")

        override fun toString(): String = buildString {
            append(first)
            append(" ")
            when (operator) {
                Op.AND -> append("AND")
                Op.OR -> append("OR")
                Op.XOR -> append("XOR")
            }
            append(" ")
            append(second)
            append(" -> ")
            append(result)
        }

        companion object {
            private val RULE_PATTERN = Regex("(\\w+) (XOR|AND|OR) (\\w+) -> (\\w+)")

            fun parse(input: String): Rule {
                val (first, op, second, result) = RULE_PATTERN.matchEntire(input)
                    ?.destructured
                    ?: error("Unexpected input: $input")
                return when (Op.valueOf(op)) {
                    Op.XOR -> Rule(first, second, result, Op.XOR)
                    Op.AND -> Rule(first, second, result, Op.AND)
                    Op.OR -> Rule(first, second, result, Op.OR)
                }
            }
        }
    }

    enum class Op(private val op: (Int, Int) -> Int) {
        AND({ a, b -> if (a == 1 && b == 1) 1 else 0 }),
        OR({ a, b -> if (a == 1 || b == 1) 1 else 0 }),
        XOR({ a, b -> if (a != b) 1 else 0 });

        operator fun invoke(a: Int, b: Int): Int = op(a, b)
    }

    /** [Full adder logic](https://www.geeksforgeeks.org/full-adder-in-digital-logic/)
     * ```
     * /// A -|
     * ///    |-XOR-> (A XOR B) // basicSum
     * /// B -|
     * ///
     * /// C---------|
     * ///           |-XOR-> (SUM) // finalSum
     * ///(A XOR B)-|
     * ///
     * /// A-|
     * ///   |-AND-> (A AND B) // basicCarry
     * /// B-|
     * ///
     * /// C---------|
     * ///           |-AND->((A XOR B) AND C) // carryForward1
     * ///(A XOR B)-|
     * ///
     * ///((A XOR B) AND C)-|
     * ///                   |-OR->((((A XOR B) XOR C)) OR (A AND B)) // finalCarry
     * ///(A AND B)---------|
     * ///```
     *
     * [Original Idea](https://github.com/xhyrom/aoc/blob/main/2024/24/part_2.py)
     * All hail to @xhyrom */
    fun fullAdder(
        a: String,
        b: String,
        carry: String?,
        rules: List<Rule>,
        swapped: MutableList<String>
    ): List<String> {
        var basicSum = findMatchingRule(a, b, Op.XOR, rules) ?: error("!")
        var basicCarry = findMatchingRule(a, b, Op.AND, rules) ?: error("!")
        if (carry == null) {
            return listOf(basicSum.result, basicCarry.result)
        } else {
            var carryForward1 = findMatchingRule(basicSum.result, carry, Op.AND, rules)
            if (carryForward1 == null) {
                swapped.add(basicSum.result)
                swapped.add(basicCarry.result)
                val temp = basicSum
                basicSum = basicCarry
                basicCarry = temp
                carryForward1 = findMatchingRule(basicSum.result, carry, Op.AND, rules)
            }

            var finalSum = findMatchingRule(basicSum.result, carry, Op.XOR, rules)

            if (basicSum.isTop && finalSum != null) {
                val temp = basicSum
                basicSum = finalSum
                finalSum = temp
                swapped.add(finalSum.result)
                swapped.add(basicSum.result)
            }
            if (basicCarry.isTop && finalSum != null) {
                val temp = basicCarry
                basicCarry = finalSum
                finalSum = temp
                swapped.add(basicCarry.result)
                swapped.add(finalSum.result)
            }
            if (carryForward1 != null && carryForward1.isTop && finalSum != null) {
                val temp = carryForward1
                carryForward1 = finalSum
                finalSum = temp
                swapped.add(carryForward1.result)
                swapped.add(finalSum.result)
            }
            val finalCarry = if (carryForward1 != null) {
                findMatchingRule(carryForward1.result, basicCarry.result, Op.OR, rules)
            } else null
            val result = ArrayList<String>()
            finalSum?.result?.also { result.add(it) }
            finalCarry?.result?.also { result.add(it) }
            return result
        }
    }

    fun findMatchingRule(a: String, b: String, operator: Op, rules: List<Rule>): Rule? = rules.asSequence()
        .filter { it.operator == operator }
        .filter { (it.first == a && it.second == b) || (it.second == a && it.first == b) }
        .firstOrNull()

    companion object {
        private val BIT_PATTERN = Regex("[xyz]\\d+")
    }
}
