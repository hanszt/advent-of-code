package aoc.secret_entrance;

import aoc.utils.ChallengeDay;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

///
/// [Zebalu day10](https://github.com/zebalu/advent-of-code-2025/blob/master/Day10.java)
///
public record Day10Zebalu(Stream<Machine> machines) implements ChallengeDay {

    public static Day10Zebalu fromPath(Path path) {
        try {
            //noinspection resource
            return new Day10Zebalu(Files.lines(path).map(Machine::fromString));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static Day10Zebalu fromText(String text) {
        return new Day10Zebalu(text.lines().map(Machine::fromString));
    }

    @Override
    public @NotNull Integer part1() {
        try {
            return machines.mapToInt(Machine::countFewestPresses).sum();
        } finally {
            machines.close();
        }
    }

    @Override
    public @NotNull Integer part2() {
        try {
            return machines.parallel().mapToInt(Machine::countFewestJoltagePresses).sum();
        } finally {
            machines.close();
        }
    }

    record Machine(List<Boolean> lights, List<SequencedSet<Integer>> buttons, List<Integer> joltage) {
        int countFewestPresses() {
            record MachineCount(List<Boolean> lights, int count) {
            }
            var seen = new HashSet<>();
            var queue = new ArrayDeque<MachineCount>();
            List<Boolean> initial = new ArrayList<>();
            for (var i = 0; i < lights.size(); i++) {
                initial.add(false);
            }
            seen.add(initial);
            queue.add(new MachineCount(initial, 0));
            while (!queue.isEmpty()) {
                var next = queue.poll();
                if (next.lights.equals(lights)) {
                    return next.count;
                }
                for (var button : buttons) {
                    final var switched = new ArrayList<>(next.lights);
                    for (int b : button) {
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
            var solver = new IntSolver();
            solver.findSolution(joltage, 0, new HashSet<>());
            if (solver.bestFound == Integer.MAX_VALUE) {
                throw new IllegalStateException("Cheapest joltage not found");
            }
            return solver.bestFound;
        }

        class IntSolver {
            int bestFound = Integer.MAX_VALUE;

            void findSolution(List<Integer> target, int steps, Set<SequencedSet<Integer>> pressedButtons) {
                int min = target.stream().min(Comparator.naturalOrder()).orElseThrow();
                if (min < 0) {
                    return;
                }
                int max = target.stream().max(Comparator.naturalOrder()).orElseThrow();
                if (steps + max >= bestFound) {
                    return;
                }
                if (max == 0) {
                    if (steps < bestFound) {
                        bestFound = steps;
                    }
                    return;
                }
                var validContinuations = new HashSet<>(buttons);
                validContinuations.removeAll(pressedButtons);
                for (var i = 0; i < target.size(); ++i) {
                    var indexOfSmall = i;
                    for (var j = 0; j < target.size(); ++j) {
                        var indexOfBig = j;
                        if (target.get(indexOfSmall) < target.get(indexOfBig)) {
                            var validButtons = validContinuations.stream().filter(b -> b.contains(indexOfBig) && !b.contains(indexOfSmall)).toList();
                            if (validButtons.isEmpty()) {
                                return;
                            } else if (validButtons.size() == 1) {
                                var button = validButtons.getFirst();
                                List<Integer> newTarget = new ArrayList<>(target);
                                for (var idx : button) {
                                    newTarget.set(idx, newTarget.get(idx) - 1);
                                }
                                findSolution(newTarget, steps + 1, pressedButtons);
                                return;
                            }
                        }
                    }
                }
                var minNon0Index = IntStream.range(0, target.size())
                        .filter(i -> target.get(i) != 0).boxed()
                        .min(Comparator.comparingInt(target::get)).orElse(-1);
                var nonChangeable = IntStream.range(0, target.size())
                        .filter(i -> target.get(i) == 0).boxed()
                        .collect(Collectors.toSet());
                var selected = validContinuations.stream().filter(b -> b.contains(minNon0Index) && !pressedButtons.contains(b) && b.stream().noneMatch(nonChangeable::contains)).toList();
                var newPressedButtons = new HashSet<>(pressedButtons);
                for (var button : selected) {
                    var newTarget = new ArrayList<>(target);
                    for (var idx : button) {
                        newTarget.set(idx, newTarget.get(idx) - 1);
                    }
                    findSolution(newTarget, steps + 1, newPressedButtons);
                    newPressedButtons.add(button);
                }
            }
        }

        static Machine fromString(String line) {
            var spaces = line.split(" ");
            List<Boolean> lights = new ArrayList<>();
            List<SequencedSet<Integer>> buttons = new ArrayList<>();
            List<Integer> joltage = new ArrayList<>();
            for (var i = 1; i < spaces[0].length() - 1; ++i) {
                lights.add(spaces[0].charAt(i) == '#');
            }
            for (var i = 1; i < spaces.length - 1; ++i) {
                var wireing = spaces[i].substring(1, spaces[i].length() - 1).split(",");
                buttons.add(new LinkedHashSet<>(Arrays.stream(wireing).map(Integer::parseInt).toList()));
            }
            var joltageStrings = spaces[spaces.length - 1].substring(1, spaces[spaces.length - 1].length() - 1).split(",");
            Arrays.stream(joltageStrings).map(Integer::parseInt).forEach(joltage::add);
            return new Machine(lights, buttons, joltage);
        }
    }
}
