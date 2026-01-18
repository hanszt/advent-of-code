package hzt.aoc.day17;

import aoc.utils.ChallengeDay;
import org.hzt.utils.function.primitives.IntBiFunction;

import java.util.*;

public record ConwayCubes(List<String> input) implements ChallengeDay {

    @Override
    public Integer part1() {
        return solve((x, y) -> new Point3D(x, y, 0));
    }

    @Override
    public Integer part2() {
        return solve((x, y) -> new Point4D(x, y, 0, 0));
    }

    private <N extends Node<N>> int solve(IntBiFunction<N> nodeSupplier) {
        Set<N> activeCubes = new HashSet<>();
        // Initial state (z=0)
        for (var y = 0; y < input.size(); y++) {
            final var s = input.get(y);
            for (var x = 0; x < s.length(); x++) {
                if (s.charAt(x) == '#') {
                    activeCubes.add(nodeSupplier.apply(x, y));
                }
            }
        }
        // Simulate 6 cycles
        for (var i = 0; i < 6; i++) {
            activeCubes = simulateCycle(activeCubes);
        }
        return activeCubes.size();
    }

    // Record to represent a 4D coordinate (x, y, z, w)
    record Point4D(int x, int y, int z, int w) implements Node<Point4D> {
        public List<Point4D> getNeighbors() {
            List<Point4D> neighbors = new ArrayList<>();
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        for (int dw = -1; dw <= 1; dw++) {
                            if (dx == 0 && dy == 0 && dz == 0 && dw == 0) continue;
                            neighbors.add(new Point4D(x + dx, y + dy, z + dz, w + dw));
                        }
                    }
                }
            }
            return neighbors;
        }
    }

    record Point3D(int x, int y, int z) implements Node<Point3D> {
        public List<Point3D> getNeighbors() {
            List<Point3D> neighbors = new ArrayList<>();
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        if (dx == 0 && dy == 0 && dz == 0) continue;
                        neighbors.add(new Point3D(x + dx, y + dy, z + dz));
                    }
                }
            }
            return neighbors;
        }
    }

    interface Node<N extends Node<N>> {
        Iterable<N> getNeighbors();
    }

    private static <N extends Node<N>> Set<N> simulateCycle(Set<N> currentActive) {
        final var nextActive = new HashSet<N>();
        // Maps a coordinate to the number of active neighbors it has
        final var neighborCounts = new HashMap<N, Integer>();

        for (final var point : currentActive) {
            for (final var neighbor : point.getNeighbors()) {
                neighborCounts.put(neighbor, neighborCounts.getOrDefault(neighbor, 0) + 1);
            }
        }

        // Apply Conway Rules
        // 1. Active: stays active if 2 or 3 neighbors are active
        for (final var point : currentActive) {
            int count = neighborCounts.getOrDefault(point, 0);
            if (count == 2 || count == 3) {
                nextActive.add(point);
            }
        }

        // 2. Inactive: becomes active if exactly 3 neighbors are active
        for (var entry : neighborCounts.entrySet()) {
            var neighbor = entry.getKey();
            if (!currentActive.contains(neighbor) && entry.getValue() == 3) {
                nextActive.add(neighbor);
            }
        }
        return nextActive;
    }
}
