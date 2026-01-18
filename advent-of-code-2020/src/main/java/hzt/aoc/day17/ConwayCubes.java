package hzt.aoc.day17;

import aoc.utils.ChallengeDay;
import org.hzt.utils.function.primitives.IntBiFunction;
import org.hzt.utils.sequences.Sequence;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record ConwayCubes(List<String> input) implements ChallengeDay {

    @Override
    public Integer part1() {
        return solve((x, y) -> new Cube3D(x, y, 0));
    }

    @Override
    public Integer part2() {
        return solve((x, y) -> new Cube4D(x, y, 0, 0));
    }

    private <C extends Cube<C>> int solve(IntBiFunction<C> cubeSupplier) {
        // Simulate 6 cycles
        return Sequence.generate(() -> simulateCycle(buildInitialState(cubeSupplier)), ConwayCubes::simulateCycle)
                .take(6)
                .last()
                .size();
    }

    private <C extends Cube<C>> Set<C> buildInitialState(final IntBiFunction<C> cubeSupplier) {
        final var activeCubes = new HashSet<C>();
        // Initial state (z=0)
        for (var y = 0; y < input.size(); y++) {
            final var s = input.get(y);
            for (var x = 0; x < s.length(); x++) {
                if (s.charAt(x) == '#') {
                    activeCubes.add(cubeSupplier.apply(x, y));
                }
            }
        }
        return activeCubes;
    }

    record Cube3D(int x, int y, int z) implements Cube<Cube3D> {
        public List<Cube3D> getNeighbors() {
            final var neighbors = new ArrayList<Cube3D>();
            for (var dx = -1; dx <= 1; dx++) {
                for (var dy = -1; dy <= 1; dy++) {
                    for (var dz = -1; dz <= 1; dz++) {
                        if (dx == 0 && dy == 0 && dz == 0) continue;
                        neighbors.add(new Cube3D(x + dx, y + dy, z + dz));
                    }
                }
            }
            return neighbors;
        }
    }

    record Cube4D(int x, int y, int z, int w) implements Cube<Cube4D> {
        public List<Cube4D> getNeighbors() {
            final var neighbors = new ArrayList<Cube4D>();
            for (var dx = -1; dx <= 1; dx++) {
                for (var dy = -1; dy <= 1; dy++) {
                    for (var dz = -1; dz <= 1; dz++) {
                        for (var dw = -1; dw <= 1; dw++) {
                            if (dx == 0 && dy == 0 && dz == 0 && dw == 0) continue;
                            neighbors.add(new Cube4D(x + dx, y + dy, z + dz, w + dw));
                        }
                    }
                }
            }
            return neighbors;
        }
    }

    interface Cube<C extends Cube<C>> {
        Iterable<C> getNeighbors();
    }

    private static <C extends Cube<C>> Set<C> simulateCycle(Set<C> active) {
        // Maps a cube to the number of active neighbors it has
        final var neighborCounts = Sequence.of(active)
                .flatMap(Cube::getNeighbors)
                .grouping()
                .eachCount();
        // Apply Conway Rules
        final var nextActive = new HashSet<C>();
        // 1. Active: stays active if 2 or 3 neighbors are active
        for (final var cube : active) {
            final var count = neighborCounts.getOrDefault(cube, 0L);
            if (count == 2 || count == 3) {
                nextActive.add(cube);
            }
        }
        // 2. Inactive: becomes active if exactly 3 neighbors are active
        for (final var entry : neighborCounts.entrySet()) {
            final var neighbor = entry.getKey();
            if (!active.contains(neighbor) && entry.getValue() == 3) {
                nextActive.add(neighbor);
            }
        }
        return nextActive;
    }
}
