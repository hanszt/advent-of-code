package aoc.jungle.adventures;

import aoc.utils.ChallengeDay;
import org.hzt.graph.TreeNode;
import org.hzt.utils.io.FileX;
import org.hzt.utils.iterables.Collectable;
import org.hzt.utils.sequences.Sequence;
import org.hzt.utils.strings.StringX;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.LongBinaryOperator;

public class Day21MonkeyMath implements ChallengeDay {

    final Map<String, Monkey> monkeyMap;
    final EquationMonkey root;

    public Day21MonkeyMath(String fileName) {
        final var lines = FileX.of(fileName).useLines(Collectable::toList);
        monkeyMap = createMonkeyMap(lines);
        root = (EquationMonkey) monkeyMap.get("root");
        buildTree(root, monkeyMap);
    }

    @NotNull
    @Override
    public Long part1() {
        return root.yell();
    }

    @NotNull
    @Override
    public Long part2() {
        final var human = monkeyMap.get("humn");
        final var isRootChild1Unknown = root.child1.depthFirstSequence().any(human::equals);
        var unknown = isRootChild1Unknown ? root.child1 : root.child2;
        var deducedNr = (isRootChild1Unknown ? root.child2 : root.child1).yell();
        while (unknown instanceof EquationMonkey monkey) {
            final var isChild1Unknown = monkey.child1.depthFirstSequence().any(human::equals);
            final var newUnknown = isChild1Unknown ? monkey.child1 : monkey.child2;
            final var knownYell = (isChild1Unknown ? monkey.child2 : monkey.child1).yell();
            deducedNr = monkey.inverseOp(isChild1Unknown).applyAsLong(deducedNr, knownYell);
            unknown = newUnknown;
        }
        return deducedNr;
    }

    private static void buildTree(Monkey monkey, Map<String, Monkey> monkeyMap) {
        if (monkey instanceof NrMonkey) {
            return;
        }
        if (monkey instanceof EquationMonkey equationMonkey) {
            equationMonkey.child1 = monkeyMap.get(equationMonkey.monkey1Name);
            equationMonkey.child2 = monkeyMap.get(equationMonkey.monkey2Name);
            buildTree(equationMonkey.child1, monkeyMap);
            buildTree(equationMonkey.child2, monkeyMap);
        }
    }

    private static Map<String, Monkey> createMonkeyMap(List<String> lines) {
        Map<String, Monkey> monkeyMap = new HashMap<>();
        for (final var line : lines) {
            final var split = StringX.of(line).split(": ");
            final var name = split.first();
            final var nrOrEquation = split.last();
            if (nrOrEquation.chars().allMatch(Character::isDigit)) {
                monkeyMap.put(name, new NrMonkey(name, Integer.parseInt(nrOrEquation)));
            } else {
                final var parts = nrOrEquation.split(" ");
                monkeyMap.put(name, new EquationMonkey(name, parts[0], nrOrEquation.charAt(5), parts[2]));
            }
        }
        return monkeyMap;
    }

    abstract static class Monkey implements TreeNode<Monkey, Monkey> {

        final String name;

        Monkey(String name) {
            this.name = name;
        }

        abstract long yell();

        public @NotNull Iterator<Monkey> childrenIterator() {
            return Collections.emptyIterator();
        }
    }

    static final class NrMonkey extends Monkey {
        final int nr;

        NrMonkey(String name, int nr) {
            super(name);
            this.nr = nr;
        }

        @Override
        public long yell() {
            return nr;
        }
    }

    static final class EquationMonkey extends Monkey {
        private final String monkey1Name;
        private final String monkey2Name;
        final char op;
        Monkey child1;
        Monkey child2;

        EquationMonkey(String name, String monkey1Name, char operation, String monkey2Name) {
            super(name);
            this.monkey1Name = monkey1Name;
            this.monkey2Name = monkey2Name;
            this.op = operation;
        }

        @Override
        public @NotNull Iterator<Monkey> childrenIterator() {
            return Sequence.of(child1, child2).iterator();
        }

        @Override
        public long yell() {
            final var n1 = child1.yell();
            final var n2 = child2.yell();
            return switch (op) {
                case '+' -> n1 + n2;
                case '-' -> n1 - n2;
                case '*' -> n1 * n2;
                case '/' -> n1 / n2;
                default -> throw new IllegalStateException();
            };
        }

        private LongBinaryOperator inverseOp(boolean firstChildUnknown) {
            return switch (op) {
                case '+' -> (n, nc) -> n - nc;
                case '-' -> (n, nc) -> firstChildUnknown ? (n + nc) : (nc - n);
                case '*' -> (n, nc) -> n / nc;
                case '/' -> (n, nc) -> firstChildUnknown ? (n * nc) : (nc / n);
                default -> throw new IllegalStateException();
            };
        }
    }
}
