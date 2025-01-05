package aoc.snowrescuemission

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class Day25Test {

    @Test
    fun testPart1TestInput() {
        Day25(
            """
                jqt: rhn xhk nvd
                rsh: frs pzl lsr
                xhk: hfx
                cmg: qnr nvd lhk bvb
                rhn: xhk bvb hfx
                bvb: xhk hfx
                pzl: lsr hfx nvd
                qnr: nvd
                ntq: jqt hfx bvb xhk
                nvd: lhk
                lsr: lhk
                rzs: qnr cmg lsr rsh
                frs: qnr lhk lsr
            """.trimIndent().lines()
        ).part1() shouldBe 54
    }

    @Test
    fun testPart1() {
        Day25(Path("input/day25.txt")).part1() shouldBe 0
    }
}
