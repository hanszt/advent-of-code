package aoc.historianhysteria

import aoc.utils.parts
import java.util.*
import java.util.List
import java.util.Map
import java.util.function.Function
import java.util.function.IntBinaryOperator
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.stream.Collectors

class Day24Zebalu(input: kotlin.collections.List<String>) {
    private val values: MutableMap<String, Int> = HashMap<String, Int>()
    private val rules: MutableList<Rule>
    private val bitMap: MutableMap<Char, MutableList<String>> = HashMap<Char, MutableList<String>>()

    init {
        val groups = input.parts { it }
        for (initial in groups.first()) {
            val parts: Array<String> = initial.split(": ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            values.put(parts[0], parts[1].toInt())
            if (BIT_PATTERN.matcher(parts[0]).matches()) {
                addToBitMap(parts[0])
            }
        }
        rules = groups.last().stream().map<Rule> { input: String -> Rule.Companion.parse(input) }.toList()
        rules.stream().filter { r: Rule -> r.result.startsWith("z") }.forEach { r: Rule -> addToBitMap(r.result) }
        require(bitMap.get('x')!!.size == bitMap.get('y')!!.size) { "Invalid input! We should have as many Xes as many Ys!" }
    }

    fun part1(): Long {
        return execute(HashMap<String, Int>(values), rules).toLong(2)
    }

    fun part2(): String {
        val allBits = rules.stream().map<MutableList<String>> { r: Rule -> List.of<String>(r.first, r.second, r.result) }
            .flatMap<String> { obj: MutableList<String> -> obj.stream() }.filter { str: String ->
                this.isBit(
                    str
                )
            }.collect(Collectors.toSet())
        val xs = allBits.stream().filter { s: String -> s.startsWith("x") }.toList()
        val zs = allBits.stream().filter { s: String -> s.startsWith("z") }.toList()
        val swapped: MutableList<String> = ArrayList<String>()
        var carry: String? = null
        for (i in xs.indices) {
            val xKey = String.format("x%02d", i)
            val yKey = String.format("y%02d", i)
            var outputs = fullAdder(xKey, yKey, carry, rules, swapped)
            if (outputs.last() != null && outputs.last().startsWith("z") && (outputs.last() != "z" + (zs.size - 1))) {
                outputs = outputs.reversed().toMutableList()
                swapped.add(outputs.first())
                swapped.add(outputs.last())
            }
            if (outputs.last() != null) {
                carry = outputs.last()
            } else {
                carry = findMatchingRule(xKey, yKey, Rule.Companion.AND, rules).map<String>(Function { r: Rule -> r.result }).orElse(null)
            }
        }
        return swapped.stream().sorted().collect(Collectors.joining(","))
    }

    private fun addToBitMap(key: String) {
        bitMap.compute(key[0]) { _, v ->
            if (v == null) {
                return@compute ArrayList<String>(List.of<String>(key))
            } else {
                if (Collections.binarySearch<String>(v, key) < 0) {
                    v.add(key)
                    Collections.sort<String>(v)
                }
                return@compute v
            }
        }
    }


    private fun isBit(str: String): Boolean {
        return BIT_PATTERN.matcher(str).matches()
    }

    private fun execute(copyOfValues: MutableMap<String, Int>, rules: MutableList<Rule>): String {
        var changed = true
        val copy: MutableList<Rule> = LinkedList<Rule>(rules)
        while (!copy.isEmpty() && changed) {
            changed = false
            val it: MutableIterator<Rule> = copy.iterator()
            while (it.hasNext()) {
                val next = it.next()
                if (next.canBeCalculated(copyOfValues)) {
                    next.calculate(copyOfValues)
                    it.remove()
                    changed = true
                }
            }
        }
        if (!copy.isEmpty()) {
            return "1".repeat(63)
        } else {
            return copyOfValues.entries.stream()
                .filter { e: MutableMap.MutableEntry<String, Int> -> e.key.startsWith("z") }
                .sorted(KEY_COMPARATOR)
                .map<Int> { it.value }
                .map<String> { i: Int -> i.toString() }
                .collect(Collectors.joining(""))
        }
    }

    @JvmRecord
    data class Rule(val first: String, val second: String, val result: String, val operator: IntBinaryOperator) {
        fun canBeCalculated(values: MutableMap<String, Int>): Boolean {
            return values.containsKey(this.first) && values.containsKey(this.second) && !values.containsKey(this.result)
        }

        fun calculate(values: MutableMap<String, Int>) {
            values.put(result, this.operator.applyAsInt(values.get(first)!!, values.get(second)!!))
        }

        val isTop: Boolean
            get() = result.startsWith("z")

        override fun toString(): String {
            val result = StringBuilder()
            result.append(first)
            result.append(" ")
            when {
                operator === AND -> result.append("AND")
                operator === OR -> result.append("OR")
                operator === XOR -> result.append("XOR")
                else -> throw IllegalStateException("Unexpected value: " + operator)
            }
            result.append(" ")
            result.append(second)
            result.append(" -> ")
            result.append(this.result)
            return result.toString()
        }

        companion object {
            private val RULE_PATTERN: Pattern = Pattern.compile("(\\w+) (XOR|AND|OR) (\\w+) -> (\\w+)")
            internal val AND = IntBinaryOperator { a: Int, b: Int -> if (a == 1 && b == 1) 1 else 0 }
            internal val OR = IntBinaryOperator { a: Int, b: Int -> if (a == 1 || b == 1) 1 else 0 }
            internal val XOR = IntBinaryOperator { a: Int, b: Int -> if (a != b) 1 else 0 }

            fun parse(input: String): Rule {
                val matcher: Matcher = RULE_PATTERN.matcher(input)
                if (matcher.matches()) {
                    val first = matcher.group(1)
                    val second = matcher.group(3)
                    val result = matcher.group(4)
                    val op = matcher.group(2)
                    return when (op) {
                        "XOR" -> Rule(first, second, result, XOR)
                        "AND" -> Rule(first, second, result, AND)
                        "OR" -> Rule(first, second, result, OR)
                        else -> throw IllegalStateException("Unexpected value: " + op + " in line: " + input)
                    }
                }
                throw IllegalStateException("Unexpected input: " + input)
            }
        }
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
    fun fullAdder(a: String, b: String, carry: String?, rules: MutableList<Rule>, swapped: MutableList<String>): MutableList<String> {
        var basicSum = findMatchingRule(a, b, Rule.Companion.XOR, rules)
        basicSum.orElseThrow()
        var basicCarry = findMatchingRule(a, b, Rule.Companion.AND, rules)
        basicCarry.orElseThrow()
        if (carry == null) {
            return List.of<String>(basicSum.get().result, basicCarry.get().result)
        } else {
            var carryForward1 = findMatchingRule(basicSum.get().result, carry, Rule.Companion.AND, rules)
            if (carryForward1.isEmpty()) {
                swapped.add(basicSum.get().result)
                swapped.add(basicCarry.get().result)
                val temp = basicSum
                basicSum = basicCarry
                basicCarry = temp
                carryForward1 = findMatchingRule(basicSum.get().result, carry, Rule.Companion.AND, rules)
            }

            var finalSum = findMatchingRule(basicSum.get().result, carry, Rule.Companion.XOR, rules)

            if (basicSum.get().isTop && finalSum.isPresent()) {
                val temp = basicSum
                basicSum = finalSum
                finalSum = temp
                swapped.add(finalSum.get().result)
                swapped.add(basicSum.get().result)
            }
            if (basicCarry.get().isTop && finalSum.isPresent()) {
                val temp = basicCarry
                basicCarry = finalSum
                finalSum = temp
                swapped.add(basicCarry.get().result)
                swapped.add(finalSum.get().result)
            }
            if (carryForward1.isPresent() && carryForward1.get().isTop && finalSum.isPresent()) {
                val temp = carryForward1
                carryForward1 = finalSum
                finalSum = temp
                swapped.add(carryForward1.get().result)
                swapped.add(finalSum.get().result)
            }
            val finalCarry: Optional<Rule>
            if (carryForward1.isPresent()) {
                finalCarry = findMatchingRule(carryForward1.get().result, basicCarry.get().result, Rule.Companion.OR, rules)
            } else {
                finalCarry = Optional.empty<Rule>()
            }
            val result: MutableList<String> = ArrayList<String>()
            result.add(finalSum.map<String>(Rule::result).orElse(null))
            result.add(finalCarry.map<String>(Rule::result).orElse(null))
            return result
        }
    }

    fun findMatchingRule(a: String, b: String, operator: IntBinaryOperator, rules: MutableList<Rule>): Optional<Rule> {
        return rules.stream().filter { r: Rule -> r.operator === operator }
            .filter { r: Rule -> (r.first == a && r.second == b) || (r.second == a && r.first == b) }.findFirst()
    }

    companion object {
        private val BIT_PATTERN: Pattern = Pattern.compile("[xyz]\\d+")
        private val KEY_COMPARATOR: Comparator<MutableMap.MutableEntry<String, Int>> = Map.Entry.comparingByKey<String, Int>().reversed()
    }
}
