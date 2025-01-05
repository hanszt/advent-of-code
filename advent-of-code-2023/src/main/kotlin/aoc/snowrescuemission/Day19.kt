package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import aoc.utils.parts
import java.nio.file.Path
import kotlin.io.path.readLines

class Day19(private val input: List<String>) : ChallengeDay {
    constructor(path: Path) : this(path.readLines())

    /**
     * What do you get if you add together all the rating numbers for all the parts that ultimately get accepted?
     */
    override fun part1(): Long = part1Elizarov()

    private fun part1Elizarov(): Long {
        val (w, partRatings) = input.parts { it }
        val workFlows = toWorkflows(w)
        val parts = parts(partRatings)
        var sum = 0L
        for (part in parts) {
            var cur = "in"
            while (cur != "A" && cur != "R") {
                val wf = workFlows[cur]!!
                for (rule in wf.rules) {
                    val c = rule.condition
                    if (c != null) {
                        val pv = part[c.v]
                        val ok = when (c.op) {
                            '<' -> pv < c.w
                            '>' -> pv > c.w
                            else -> error("!")
                        }
                        if (!ok) continue
                    }
                    cur = rule.dest
                    break
                }
            }
            if (cur == "A") sum += part.vs.values.sum()
        }
        return sum
    }

    /**
     * How many distinct combinations of ratings will be accepted by the Elves' workflows?
     */
    override fun part2(): Long = part2Elizarov()

    private fun part2Elizarov(): Long {
        val workFlows = toWorkflows(input.parts { it }.first())
        fun compute(cur: String, rm0: Map<Char, IntRange>): Long {
            if (cur == "R") return 0
            if (cur == "A") return rm0.values.fold(1) { pr, r -> pr * (r.last - r.first + 1) }
            val workFlow = workFlows[cur] ?: error("Workflow $cur not found")
            val rm = rm0.toMutableMap()
            var res = 0L
            for (r in workFlow.rules) {
                val condition = r.condition
                if (condition == null) {
                    res += compute(cur = r.dest, rm0 = rm)
                    break
                } else {
                    val cr = rm[condition.v]!!
                    val (rt, rf) = when (condition.op) {
                        '<' -> cr.first..minOf(cr.last, condition.w - 1) to maxOf(cr.first, condition.w)..cr.last
                        '>' -> maxOf(cr.first, condition.w + 1)..cr.last to cr.first..minOf(cr.last, condition.w)
                        else -> error("!")
                    }
                    if (!rt.isEmpty()) {
                        val rm1 = rm.toMutableMap()
                        rm1[condition.v] = rt
                        res += compute(cur = r.dest, rm0 = rm1)
                    }
                    if (rf.isEmpty()) break
                    rm[condition.v] = rf
                }
            }
            return res
        }
        return compute(cur = "in", rm0 = "xmas".associateWith { 1..4000 })
    }

    private fun parts(partRatings: List<String>): List<Part> = buildList {
        for (s in partRatings) {
            val vs = buildMap {
                for (s in s.removeSurrounding("{", "}").split(',')) {
                    check(s[1] == '=')
                    this[s[0]] = s.substring(2).toInt()
                }
            }
            this += Part(vs)
        }
    }

    private fun toWorkflows(workflows: List<String>) = buildMap {
        for (s in workflows) {
            val oi = s.indexOf("{")
            val name = s.substring(0, oi)
            val rules = s.substring(oi).removeSurrounding("{", "}")
                .split(',').map { rs ->
                val ci = rs.indexOf(':')
                val condition = if (ci >= 0) {
                    Condition(v = rs[0], op = rs[1], w = rs.substring(2, ci).toInt())
                } else null
                Rule(condition = condition, dest = rs.substring(ci + 1))
            }
            this[name] = WorkFlow(rules)
        }
    }

    data class Condition(val v: Char, val op: Char, val w: Int)
    data class Rule(val condition: Condition?, val dest: String)
    data class WorkFlow(val rules: List<Rule>)
    data class Part(val vs: Map<Char, Int>) {
        operator fun get(v: Char): Int = vs[v] ?: error("$v not found")
    }
}
