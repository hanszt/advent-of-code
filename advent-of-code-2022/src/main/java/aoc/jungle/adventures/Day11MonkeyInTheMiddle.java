package aoc.jungle.adventures;

import aoc.utils.AocUtils;
import aoc.utils.ChallengeDay;
import org.hzt.utils.collections.ListX;
import org.hzt.utils.collections.primitives.LongMutableList;
import org.hzt.utils.io.FileX;
import org.hzt.utils.sequences.Sequence;
import org.hzt.utils.strings.StringX;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.LongUnaryOperator;

/**
 * @see <a href="https://adventofcode.com/2022/day/9">Day 9: Rope bridge</a>
 */
public class Day11MonkeyInTheMiddle implements ChallengeDay {

    private final ListX<String> monkeysAsString;

    public Day11MonkeyInTheMiddle(String fileName) {
        monkeysAsString = FileX.of(fileName).readTextX().split(AocUtils.DOUBLE_LINE_SEPARATOR);
    }

    @NotNull
    @Override
    public Long part1() {
        return executeRounds(20, l -> l / 3);
    }

    @NotNull
    @Override
    public Long part2() {
        return executeRounds(10_000, l -> l);
    }

    private static int getCommonDivisor(List<Monkey> monkeys) {
        return monkeys.stream()
                .mapToInt(monkey -> monkey.divisor)
                .reduce(1, (acc, divisor) -> acc * divisor);
    }

    @NotNull
    private List<Monkey> getMonkeys() {
        return monkeysAsString.stream()
                .map(Monkey::parse)
                .toList();
    }

    private long executeRounds(int nrOfRounds, LongUnaryOperator operator) {
        final var monkeys = getMonkeys();
        final var commonDivisor = getCommonDivisor(monkeys);
        for (int i = 0; i < nrOfRounds; i++) {
            for (Monkey monkey : monkeys) {
                final var size = monkey.items.size();
                for (int j = 0; j < size; j++) {
                    monkey.throwItem(monkeys, commonDivisor, operator);
                    monkey.inspectionCount++;
                }
            }
        }
        return Sequence.of(monkeys)
                .sortedByDescending(m -> m.inspectionCount)
                .take(2)
                .mapToLong(m -> m.inspectionCount)
                .reduce(1, (totalCount, count) -> totalCount * count);
    }

    private static class Monkey {

        final LongMutableList items;
        final String operationLine;
        final int divisor;
        final int monkeyNrWhenTrue;
        final int monkeyNrWhenFalse;

        int inspectionCount = 0;

        public Monkey(String operationLine, int divisor, int monkeyNrWhenTrue, int monkeyNrWhenFalse, long... items) {
            this.monkeyNrWhenTrue = monkeyNrWhenTrue;
            this.monkeyNrWhenFalse = monkeyNrWhenFalse;
            this.items = LongMutableList.of(items);
            this.operationLine = operationLine;
            this.divisor = divisor;
        }

        void throwItem(List<Monkey> monkeys, int commonDivisor, LongUnaryOperator operator) {
            final var value = items.removeAt(0);
            final var operation = operation(value) % commonDivisor;
            final var newWorryLevel = operator.applyAsLong(operation);
            monkeys.get(newWorryLevel % divisor == 0 ? monkeyNrWhenTrue : monkeyNrWhenFalse).items.add(newWorryLevel);
        }

        long operation(long worryLevel) {
            final var s1 = operationLine.split(" ");
            final var first = s1[2];
            final var value = "old".equals(first) ? worryLevel : Integer.parseInt(first);
            return switch (s1[1]) {
                case "+" -> worryLevel + value;
                case "*" -> worryLevel * value;
                default -> throw new IllegalStateException();
            };
        }

        private static Monkey parse(String s) {
            final var lines = s.lines().toList();
            final var operationLine = lines.get(2);
            final var operation = operationLine.substring(operationLine.indexOf("old"));

            final var divisor = StringX.of(lines.get(3)).splitX(" by ").get(1).toInt();
            final var monkeyNrWhenTrue = StringX.of(lines.get(4)).splitX("monkey ").get(1).toInt();
            final var monkeyNrWhenFalse = StringX.of(lines.get(5)).splitX("monkey ").get(1).toInt();
            final var items = StringX.of(lines.get(1)).splitToSequence(": ", ", ")
                    .skip(1)
                    .mapToLong(Long::parseLong)
                    .toArray();
            return new Monkey(operation, divisor, monkeyNrWhenTrue, monkeyNrWhenFalse, items);
        }
    }
}
