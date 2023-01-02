package aoc;

import org.junit.jupiter.api.Test;

import static org.hzt.utils.It.println;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day21MonkeyMathTest {

    @Test
    void testPart1() {
        final var day21MonkeyMath = new Day21MonkeyMath("input/day21.txt");
        final var result = day21MonkeyMath.part1();
        println(result);
        println(day21MonkeyMath.root.toTreeString(1, "-", m ->
                m.name + ": " + (m instanceof Day21MonkeyMath.NrMonkey nm ? String.valueOf(nm.nr) : m instanceof Day21MonkeyMath.EquationMonkey em ? em.op : "")));
        assertEquals(157714751182692L, result);
    }

    @Test
    void testPart1Test() {
        final var result = new Day21MonkeyMath("input/day21test.txt").part1();
        println(result);
        assertEquals(152, result);
    }

    @Test
    void testPart2() {
        final var result = new Day21MonkeyMath("input/day21.txt").part2();
        assertEquals(3373767893067L, result);
    }

    @Test
    void testPart2Test() {
        final var result = new Day21MonkeyMath("input/day21test.txt").part2();
        assertEquals(301, result);
    }

}
