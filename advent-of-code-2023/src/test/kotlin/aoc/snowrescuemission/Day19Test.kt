package aoc.snowrescuemission

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day19Test {

    private companion object {
        private val day19 by lazy { Day19(Path("input/day19.txt")) }
        private val day19dr by lazy { Day19(Path("input/day19dr.txt")) }
        private val day19TestInput = Day19(
            input = """
                    px{a<2006:qkq,m>2090:A,rfg}
                    pv{a>1716:R,A}
                    lnx{m>1548:A,A}
                    rfg{s<537:gd,x>2440:R,A}
                    qs{s>3448:A,lnx}
                    qkq{x<1416:A,crn}
                    crn{x>2662:A,R}
                    in{s<1351:px,qqz}
                    qqz{s>2770:qs,m<1801:hdj,R}
                    gd{a>3333:R,R}
                    hdj{m>838:A,pv}
        
                    {x=787,m=2655,a=1222,s=2876}
                    {x=1679,m=44,a=2067,s=496}
                    {x=2036,m=264,a=79,s=2244}
                    {x=2461,m=1339,a=466,s=291}
                    {x=2127,m=1623,a=2188,s=1013}
                """.trimIndent().lines()
        )
    }

    @Test
    fun part1TestInput() {
        day19TestInput.part1() shouldBe 19114
    }

    @Test
    fun part2TestInput() {
        day19TestInput.part2() shouldBe 167409079868000
    }

    @Test
    fun testPart1() {
        day19.part1() shouldBe 476889
    }

    @Test
    fun testPart2() {
        day19.part2() shouldBe 132380153677887L
    }

    @Test
    fun testPart1Dr() {
        day19dr.part1() shouldBe 425811
    }
}
