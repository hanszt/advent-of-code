package aoc.historianhysteria

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day23Test {

    private companion object {
        private val day23 by lazy { Day23(Path("input/day23.txt")) }
        private val day23Test by lazy {
            Day23(
                """
                    kh-tc
                    qp-kh
                    de-cg
                    ka-co
                    yn-aq
                    qp-ub
                    cg-tb
                    vc-aq
                    tb-ka
                    wh-tc
                    yn-cg
                    kh-ub
                    ta-co
                    de-co
                    tc-td
                    tb-wq
                    wh-td
                    ta-ka
                    td-qp
                    aq-cg
                    wq-ub
                    ub-vc
                    de-ta
                    wq-aq
                    wq-vc
                    wh-yn
                    ka-de
                    kh-ta
                    co-tc
                    wh-qp
                    tb-vc
                    td-yn
                """.trimIndent().lines()
            )
        }
    }

    @Test
    fun part1Test() {
        day23Test.part1() shouldBe 7
    }

    @Test
    fun part1() {
        day23.part1() shouldBe 1378
    }

    @Test
    fun part2Test() {
        day23Test.part2() shouldBe "co,de,ka,ta"
    }

    @Test
    fun part2() {
        day23.part2() shouldBe "bs,ey,fq,fy,he,ii,lh,ol,tc,uu,wl,xq,xv"
    }

}