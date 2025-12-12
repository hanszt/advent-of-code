package aoc.secret_entrance;

import aoc.utils.ChallengeDay;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

///
/// [Zebalu day10](https://github.com/zebalu/advent-of-code-2025/blob/master/Day10.java)
///
public record Day10Zebalu(List<Machine> machines) implements ChallengeDay {

    public static Day10Zebalu fromPath(final Path path) {
        try {
            return getDay10Zebalu(Files.readAllLines(path));
        } catch (final IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static Day10Zebalu fromText(final String text) {
        return getDay10Zebalu(text.lines().toList());
    }

    private static @NotNull Day10Zebalu getDay10Zebalu(final List<String> list) {
        final var result = new ArrayList<Machine>();
        for (var i = 0; i < list.size(); i++) {
            final var line = list.get(i);
            final var machine = Machine.fromString(i + 1, line);
            result.add(machine);
        }
        return new Day10Zebalu(result);
    }

    private static void log(final String message) {
        System.out.println(message);
    }

    @Override
    public @NotNull Integer part1() {
        return machines.stream().mapToInt(Machine::countFewestPresses).sum();
    }

    @Override
    public @NotNull Integer part2() {
        return machines.parallelStream().mapToInt(Machine::countFewestJoltagePresses).sum();
    }

    record Machine(
            int id,
            List<Boolean> lights,
            List<SequencedSet<Integer>>
            buttons, List<Integer> joltage
    ) {

        int countFewestPresses() {
            record MachineCount(List<Boolean> lights, int count) {
            }
            final var seen = new HashSet<>();
            final var queue = new ArrayDeque<MachineCount>();
            final var initial = new ArrayList<Boolean>();
            for (var i = 0; i < lights.size(); i++) {
                initial.add(false);
            }
            seen.add(initial);
            queue.add(new MachineCount(initial, 0));
            while (!queue.isEmpty()) {
                final var next = queue.poll();
                if (next.lights.equals(lights)) {
                    return next.count;
                }
                for (final var button : buttons) {
                    final var switched = new ArrayList<>(next.lights);
                    for (final int b : button) {
                        switched.set(b, !switched.get(b));
                    }
                    if (switched.equals(lights)) {
                        return next.count + 1;
                    } else if (seen.add(switched)) {
                        queue.add(new MachineCount(switched, next.count + 1));
                    }
                }
            }
            throw new IllegalStateException("Could not switch all lights on!");
        }

        int countFewestJoltagePresses() {
            final var start = System.currentTimeMillis();
            final var solver = new IntSolver();
            solver.findSolution(joltage, 0, new HashSet<>());
            if (solver.bestFound == Integer.MAX_VALUE) {
                throw new IllegalStateException("Cheapest joltage not found");
            }
            final var end = System.currentTimeMillis();
            final var bestFound = solver.bestFound;
            log("Machine: " + id + ", bestFound " + bestFound + ", Solve time: " + (end - start) + " ms");
            return bestFound;
        }

        class IntSolver {
            int bestFound = Integer.MAX_VALUE;

            void findSolution(
                    final List<Integer> target,
                    final int steps,
                    final Set<SequencedSet<Integer>> pressedButtons
            ) {
                final int min = target.stream().min(Comparator.naturalOrder()).orElseThrow();
                if (min < 0) {
                    return;
                }
                final int max = target.stream().max(Comparator.naturalOrder()).orElseThrow();
                if (steps + max >= bestFound) {
                    return;
                }
                if (max == 0) {
                    if (steps < bestFound) {
                        bestFound = steps;
                    }
                    return;
                }
                final var validContinuations = new HashSet<>(buttons);
                validContinuations.removeAll(pressedButtons);
                for (var i = 0; i < target.size(); ++i) {
                    final var indexOfSmall = i;
                    for (var j = 0; j < target.size(); ++j) {
                        final var indexOfBig = j;
                        if (target.get(indexOfSmall) < target.get(indexOfBig)) {
                            final var validButtons = validContinuations.stream()
                                    .filter(b -> b.contains(indexOfBig) && !b.contains(indexOfSmall))
                                    .toList();
                            if (validButtons.isEmpty()) {
                                return;
                            } else if (validButtons.size() == 1) {
                                final var button = validButtons.getFirst();
                                final var newTarget = new ArrayList<>(target);
                                for (final var idx : button) {
                                    newTarget.set(idx, newTarget.get(idx) - 1);
                                }
                                findSolution(newTarget, steps + 1, pressedButtons);
                                return;
                            }
                        }
                    }
                }
                final var minNon0Index = IntStream.range(0, target.size())
                        .filter(i -> target.get(i) != 0).boxed()
                        .min(Comparator.comparingInt(target::get)).orElse(-1);
                final var nonChangeable = IntStream.range(0, target.size())
                        .filter(i -> target.get(i) == 0).boxed()
                        .collect(Collectors.toSet());
                final var selected = validContinuations.stream()
                        .filter(b -> b.contains(minNon0Index) && !pressedButtons.contains(b) && b.stream().noneMatch(nonChangeable::contains))
                        .toList();
                final var newPressedButtons = new HashSet<>(pressedButtons);
                for (final var button : selected) {
                    final var newTarget = new ArrayList<>(target);
                    for (final var idx : button) {
                        newTarget.set(idx, newTarget.get(idx) - 1);
                    }
                    findSolution(newTarget, steps + 1, newPressedButtons);
                    newPressedButtons.add(button);
                }
            }
        }

        static Machine fromString(final int id, final String line) {
            final var spaces = line.split(" ");
            final var lights = new ArrayList<Boolean>();
            final var buttons = new ArrayList<SequencedSet<Integer>>();
            for (var i = 1; i < spaces[0].length() - 1; ++i) {
                lights.add(spaces[0].charAt(i) == '#');
            }
            for (var i = 1; i < spaces.length - 1; ++i) {
                final var wiring = spaces[i].substring(1, spaces[i].length() - 1).split(",");
                buttons.add(new LinkedHashSet<>(Arrays.stream(wiring).map(Integer::parseInt).toList()));
            }
            final var joltageStrings = spaces[spaces.length - 1].substring(1, spaces[spaces.length - 1].length() - 1).split(",");
            final var joltage = Arrays.stream(joltageStrings).map(Integer::parseInt).toList();
            return new Machine(id, lights, buttons, joltage);
        }
    }
}
