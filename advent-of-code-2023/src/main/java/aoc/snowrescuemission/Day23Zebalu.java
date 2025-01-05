package aoc.snowrescuemission;

import aoc.utils.grid2d.GridPoint2D;

import java.util.*;
import java.util.function.Function;

import static aoc.utils.grid2d.Grid2DUtilsKt.get;
import static aoc.utils.grid2d.Grid2DUtilsKt.getOrNull;

public final class Day23Zebalu {

    private final List<String> maze;
    private final GridPoint2D start;
    private final GridPoint2D target;

    public Day23Zebalu(final List<String> maze) {
        this.maze = maze;
        start = GridPoint2D.of(1, 0);
        target = GridPoint2D.of(maze.size() - 2, maze.size() - 1);
    }

    public int part1() {
        return new WeightedGraph(this::part1Neighbours).longestPath();
    }

    public int part2() {
        return new WeightedGraph(this::part2Neighbours).longestPath();
    }

    private Map<GridPoint2D, Integer> longestPathToAny(
            final GridPoint2D start,
            final Map<GridPoint2D, Integer> idMap,
            final Function<GridPoint2D, List<GridPoint2D>> nextList
    ) {
        final var result = new HashMap<GridPoint2D, Integer>();
        final var stack = new ArrayDeque<SequencedSet<GridPoint2D>>();
        stack.add(new LinkedHashSet<>(Set.of(start)));
        while (!stack.isEmpty()) {
            final var curr = stack.pop();
            if (idMap.containsKey(curr.getLast()) && !curr.getLast().equals(start)) {
                result.compute(curr.getLast(), (_, v) -> (v == null) ? curr.size() - 1 : Math.max(v, curr.size() - 1));
            } else {
                nextList.apply(curr.getLast()).stream().filter(n -> !curr.contains(n)).forEach(n -> {
                    final var next = new LinkedHashSet<>(curr);
                    next.add(n);
                    stack.push(next);
                });
            }
        }
        return result;
    }

    List<GridPoint2D> part1Neighbours(GridPoint2D p) {
        final List<GridPoint2D> result = new ArrayList<>();
        switch (maze.get(p.getY()).charAt(p.getX())) {
            case '.' -> {
                appendIfMatch(result, p.plusY(-1), '^');
                appendIfMatch(result, p.plusY(1), 'v');
                appendIfMatch(result, p.plusX(-1), '<');
                appendIfMatch(result, p.plusX(1), '>');
            }
            case 'v' -> result.add(p.plusY(1));
            case '^' -> result.add(p.plusY(-1));
            case '>' -> result.add(p.plusX(1));
            case '<' -> result.add(p.plusX(-1));
            case '#' -> throw new IllegalStateException("can not stand here: " + this);
        }
        return result;
    }

    List<GridPoint2D> part2Neighbours(GridPoint2D p) {
        if (get(maze, p) == '#') {
            throw new IllegalStateException("can not stand here: " + this);
        } else {
            final List<GridPoint2D> result = new ArrayList<>();
            appendIfNotMatch(result, p.plusY(1), '#');
            appendIfNotMatch(result, p.plusY(-1), '#');
            appendIfNotMatch(result, p.plusX(1), '#');
            appendIfNotMatch(result, p.plusX(-1), '#');
            return result;
        }
    }

    private void appendIfMatch(final List<GridPoint2D> collector, final GridPoint2D coord, final char accepted) {
        final var at = getOrNull(maze, coord) instanceof Character c ? c : '#';
        if (at == '.' || at == accepted) {
            collector.add(coord);
        }
    }

    private void appendIfNotMatch(final List<GridPoint2D> collector, final GridPoint2D coord, final char rejected) {
        final var at = getOrNull(maze, coord) instanceof Character c ? c : '#';
        if (at == '.' || at != rejected) {
            collector.add(coord);
        }
    }

    private final class WeightedGraph {
        private final Map<GridPoint2D, Integer> forkIds = new HashMap<>();
        private final List<Integer>[] connections;
        private final int[][] costs;

        public WeightedGraph(final Function<GridPoint2D, List<GridPoint2D>> walkableNeighbourExtractor) {
            var id = 0;
            forkIds.put(start, id);
            for (var y = 1; y < maze.size() - 1; ++y) {
                final var line = maze.get(y);
                for (var x = 1; x < line.length() - 1; ++x) {
                    final var ch = line.charAt(x);
                    if (ch != '#') {
                        final var c = GridPoint2D.of(x, y);
                        if (part2Neighbours(c).size() > 2) {
                            forkIds.put(c, ++id);
                        }
                    }
                }
            }
            forkIds.put(target, ++id);
            //noinspection unchecked
            connections = new List[forkIds.size()];
            costs = new int[forkIds.size()][];
            for (var i = 0; i < forkIds.size(); ++i) {
                connections[i] = new ArrayList<>();
                costs[i] = new int[forkIds.size()];
            }
            final var entries = new ArrayList<>(forkIds.entrySet());
            for (final var iEntry : entries) {
                final var paths = longestPathToAny(iEntry.getKey(), forkIds, walkableNeighbourExtractor);
                for (final var distE : paths.entrySet()) {
                    final int distId = forkIds.get(distE.getKey());
                    connections[iEntry.getValue()].add(distId);
                    costs[iEntry.getValue()][distId] = distE.getValue();
                }
            }
        }

        int longestPath() {
            final int startId = forkIds.get(start);
            final int targetId = forkIds.get(target);
            return longestPath(startId, targetId, 0, 0L);
        }

        int longestPath(final int startId, final int targetId, final int dist, final long visited) {
            var longest = Integer.MIN_VALUE;
            for (final int c : connections[startId]) {
                final var v = 1L << c;
                if ((visited & v) == 0L) {
                    final var newCost = dist + costs[startId][c];
                    if (c == targetId) {
                        longest = Math.max(longest, newCost);
                    } else {
                        longest = Math.max(longest, longestPath(c, targetId, newCost, visited | v));
                    }
                }
            }
            return longest;
        }
    }
}
