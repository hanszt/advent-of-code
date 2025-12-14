package aoc.secret_entrance;

import aoc.secret_entrance.lib.BigRational;
import aoc.secret_entrance.lib.simplex.ConstraintType;
import aoc.secret_entrance.lib.simplex.Simplex;
import aoc.secret_entrance.lib.simplex.VariableType;
import aoc.utils.ChallengeDay;
import aoc.utils.FileUtils;
import org.hzt.utils.collections.primitives.IntSet;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/// [Johan de Jong day 10](https://github.com/johandj123/adventofcode2025/blob/master/src/Day10.java)
public record Day10JohanDeJong(List<Machine> machines) implements ChallengeDay {

    private static final Pattern NON_DIGITS = Pattern.compile("\\D");

    public Day10JohanDeJong(final Path path) {
        this(FileUtils.<List<Machine>>useLines(path, lines -> lines.map(Machine::new).toList()));
    }

    @Override
    public @NotNull Integer part1() {
        return machines.stream().mapToInt(Machine::presses).sum();
    }

    @Override
    public @NotNull Integer part2() {
        return machines.stream().mapToInt(Machine::pressesJoltage).sum();
    }

    static class Machine {
        private static final Pattern START = Pattern.compile("\\[[.#]+]");
        private static final Pattern END = Pattern.compile("\\([^)]+\\)");
        private static final Pattern END2 = Pattern.compile("\\{[^}]+}");

        private final int indicator;
        private final int[] buttonsMasks;
        private final int[][] buttons;
        private final int[] joltageRequirement;

        Machine(final String line) {
            final var p1 = extract(line, START).findFirst().orElseThrow();
            var indicator = 0;
            for (var i = 0; i < p1.length() - 2; i++) {
                if (p1.charAt(i + 1) == '#') {
                    indicator |= (1 << i);
                }
            }
            this.indicator = indicator;
            final Iterable<String> p2s = extract(line, END)::iterator;
            final var buttons = Stream.<int[]>builder();
            final var buttonsMasks = IntStream.builder();
            for (final var p2 : p2s) {
                final var button = extractPositiveIntegers(p2).toArray();
                buttons.add(button);
                var buttonMask = 0;
                for (final int i : button) {
                    buttonMask |= (1 << i);
                }
                buttonsMasks.add(buttonMask);
            }
            this.buttons = buttons.build().toArray(int[][]::new);
            this.buttonsMasks = buttonsMasks.build().toArray();
            final var p3 = extract(line, END2).findFirst().orElseThrow();
            joltageRequirement = extractPositiveIntegers(p3).toArray();
        }

        int presses() {
            var best = Integer.MAX_VALUE;
            for (var i = 0; i < (1 << buttonsMasks.length); i++) {
                var current = 0;
                var presses = 0;
                for (var j = 0; j < buttonsMasks.length; j++) {
                    if ((i & (1 << j)) != 0) {
                        current ^= buttonsMasks[j];
                        presses++;
                    }
                }
                if (current == indicator) {
                    best = Math.min(best, presses);
                }
            }
            return best;
        }

        int pressesJoltage() {
            final var simplex = new Simplex();
            for (var i = 0; i < buttons.length; i++) {
                simplex.addVariable(VariableType.INTEGER_NONNEGATIVE, BigRational.MINUS_ONE);
            }
            for (var i = 0; i < joltageRequirement.length; i++) {
                final int requirement = joltageRequirement[i];
                simplex.addConstraint(ConstraintType.EQUAL, new BigRational(requirement));
                for (var j = 0; j < buttons.length; j++) {
                    final var button = buttons[j];
                    if (IntSet.of(button).contains(i)) {
                        simplex.addConstraintTerm(BigRational.ONE, j);
                    }
                }
            }
            final var solution = simplex.solve();
            return (int) -solution.value().longValueExact();
        }
    }

    public static IntStream extractPositiveIntegers(final String input) {
        return NON_DIGITS.splitAsStream(input).mapMultiToInt((s, c) -> {
            if (!s.isBlank()) {
                c.accept(Integer.parseInt(s));
            }
        });
    }

    public static Stream<String> extract(final String input, Pattern pattern) {
        return pattern.matcher(input).results().map(MatchResult::group);
    }
}
