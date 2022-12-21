package aoc;

import org.hzt.graph.TreeNode;
import org.hzt.utils.io.FileX;
import org.hzt.utils.iterables.Collectable;
import org.hzt.utils.strings.StringX;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.LongBinaryOperator;

public class Day21 implements ChallengeDay {


    private final List<String> lines;

    public Day21(String fileName) {
        lines = FileX.of(fileName).useLines(Collectable::toList);
    }

    @NotNull
    @Override
    public Long part1() {
        final var monkeyMap = createMonkeyMap(lines);
        Monkey root = buildTree(monkeyMap);
        return root.yell();
    }

    private static Monkey buildTree(Map<String, Monkey> monkeyMap) {
        final var root = monkeyMap.get("root");
        buildTree(root, monkeyMap);
        return root;
    }

    private static void buildTree(Monkey monkey, Map<String, Monkey> monkeyMap) {
        if (monkey instanceof NrMonkey) {
            return;
        }
        if (monkey instanceof EquationMonkey equationMonkey) {
            final var monkey1 = monkeyMap.get(equationMonkey.monkey1Name);
            final var monkey2 = monkeyMap.get(equationMonkey.monkey2Name);
            equationMonkey.children.addAll(List.of(monkey1, monkey2));
            buildTree(monkey1, monkeyMap);
            buildTree(monkey2, monkeyMap);
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
                monkeyMap.put(name, new EquationMonkey(name, parts[0], parts[1], parts[2]));
            }
        }
        return monkeyMap;
    }

    @NotNull
    @Override
    public Integer part2() {
        return 0;
    }

    interface Monkey extends TreeNode<Monkey, Monkey> {

        long yell();
        String name();
    }

    record NrMonkey(String name, int nr) implements Monkey {

        @Override
        public Collection<Monkey> getChildren() {
            return Collections.emptyList();
        }

        @Override
        public long yell() {
            return nr;
        }
    }

    record EquationMonkey(String name, String monkey1Name, String operation, String monkey2Name, List<Monkey> children)
            implements Monkey {

        @SuppressWarnings("java:S1213")
        private static final Map<String, LongBinaryOperator> eqationMap = Map.of(
                "+", (n1, n2) -> n1 + n2,
                "-", (n1, n2) -> n1 - n2,
                "*", (n1, n2) -> n1 * n2,
                "/", (n1, n2) -> n1 / n2);

        EquationMonkey(String name, String monkey1Name, String operation, String monkey2Name) {
            this(name, monkey1Name, operation, monkey2Name, new ArrayList<>());
        }

        @Override
        public Collection<Monkey> getChildren() {
            return children;
        }

        @Override
        public long yell() {
            final var monkey1 = children.get(0);
            final var monkey2 = children.get(1);
            if (monkey1 instanceof NrMonkey m1 && monkey2 instanceof NrMonkey m2) {
                return eqationMap.get(operation).applyAsLong(m1.nr, m2.nr);
            }
            if (monkey1 instanceof EquationMonkey m1 && monkey2 instanceof NrMonkey m2) {
                return eqationMap.get(operation).applyAsLong(m1.yell(), m2.nr);
            }
            if (monkey1 instanceof NrMonkey m1 && monkey2 instanceof EquationMonkey m2) {
                return eqationMap.get(operation).applyAsLong(m1.nr, m2.yell());
            }
            return eqationMap.get(operation).applyAsLong(monkey1.yell(), monkey2.yell());
        }

    }
}
