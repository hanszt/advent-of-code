package aoc.snowrescuemission;

import aoc.utils.grid2d.Grid2DNode;
import aoc.utils.grid2d.GridPoint2D;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;

import static aoc.utils.grid2d.Grid2DUtilsKt.*;
import static aoc.utils.grid2d.GridPoint2D.orthoDirs;

public final class Day23Zebalu {

    private static final String DIRS = ">v<^";

    private final List<String> maze;
    private final GridPoint2D start;
    private final GridPoint2D target;

    public Day23Zebalu(final List<String> maze) {
        this.maze = maze;
        start = GridPoint2D.of(1, 0);
        target = getLowerRight(maze).minus(GridPoint2D.of(1, 0));
    }

    public int part1() {
        return new WeightedGraph(this::part1Neighbours).longestPath();
    }

    public int part2() {
        return new WeightedGraph(p -> neighbors(p, (_, c) -> c != '#')).longestPath();
    }

    List<GridPoint2D> part1Neighbours(GridPoint2D p) {
        final var result = new ArrayList<GridPoint2D>();
        switch (get(maze, p)) {
            case '.' -> result.addAll(neighbors(p, (i, c) -> c == '.' || DIRS.charAt(i) == c));
            case 'v' -> result.add(p.plusY(1));
            case '^' -> result.add(p.plusY(-1));
            case '>' -> result.add(p.plusX(1));
            case '<' -> result.add(p.plusX(-1));
            case '#' -> throw new IllegalStateException("can not stand here: " + this);
        }
        return result;
    }

    private List<GridPoint2D> neighbors(final GridPoint2D p, final BiPredicate<Integer, Character> isLegal) {
        final var neighbors = new ArrayList<GridPoint2D>();
        for (var i = 0; i < orthoDirs.size(); i++) {
            final var n = p.plus(orthoDirs.get(i));
            final var at = getOrNull(maze, n) instanceof Character c ? c : '#';
            if (isLegal.test(i, at)) {
                neighbors.add(n);
            }
        }
        return neighbors;
    }

    private final class WeightedGraph {
        private final Map<GridPoint2D, Integer> forkIds;
        private final List<Integer>[] connections;
        private final int[][] costs;

        public WeightedGraph(final Function<GridPoint2D, List<GridPoint2D>> walkableNeighbourExtractor) {
            var id = 0;
            final var forkIds = new HashMap<GridPoint2D, Integer>();
            forkIds.put(start, id);
            for (var y = 1; y < maze.size() - 1; ++y) {
                final var line = maze.get(y);
                for (var x = 1; x < line.length() - 1; ++x) {
                    final var ch = line.charAt(x);
                    if (ch != '#') {
                        final var c = GridPoint2D.of(x, y);
                        if (neighbors(c, (_, c1) -> c1 != '#').size() > 2) {
                            forkIds.put(c, ++id);
                        }
                    }
                }
            }
            forkIds.put(target, ++id);
            this.forkIds = Collections.unmodifiableMap(forkIds);
            //noinspection unchecked
            connections = new List[forkIds.size()];
            costs = new int[forkIds.size()][];
            for (var i = 0; i < forkIds.size(); ++i) {
                connections[i] = new ArrayList<>();
                costs[i] = new int[forkIds.size()];
            }
            final var entries = new ArrayList<>(forkIds.entrySet());
            for (final var iEntry : entries) {
                final var paths = longestPathToAny(iEntry.getKey(), walkableNeighbourExtractor);
                for (final var distE : paths.entrySet()) {
                    final int distId = forkIds.get(distE.getKey());
                    connections[iEntry.getValue()].add(distId);
                    costs[iEntry.getValue()][distId] = distE.getValue().getCost();
                }
            }
        }

        /**
         * Dfs
         */
        private Map<GridPoint2D, Grid2DNode> longestPathToAny(
                final GridPoint2D start,
                final Function<GridPoint2D, List<GridPoint2D>> toWalkableNeighbours
        ) {
            final var result = new HashMap<GridPoint2D, Grid2DNode>();
            final var stack = new ArrayDeque<SequencedSet<Grid2DNode>>();
            stack.add(new LinkedHashSet<>(Set.of(new Grid2DNode(start))));
            while (!stack.isEmpty()) {
                final var curr = stack.pop();
                final var cur = curr.getLast();
                final var p = cur.getPosition();
                if (forkIds.containsKey(p) && !p.equals(start)) {
                    result.compute(p, (_, v) -> {
                        final var lastIndex = curr.size() - 1;
                        final var newCost = (v == null) ? lastIndex : Math.max(v.getCost(), lastIndex);
                        return new Grid2DNode(p, newCost, cur.getPrev());
                    });
                } else {
                    toWalkableNeighbours.apply(p).stream()
                            .filter(n -> curr.stream().noneMatch(d -> d.getPosition().equals(n)))
                            .forEach(n -> {
                                final var next = new LinkedHashSet<>(curr);
                                next.add(new Grid2DNode(n, 0, cur));
                                stack.push(next);
                            });
                }
            }
            return result;
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
                    longest = Math.max(longest, c == targetId ? newCost : longestPath(c, targetId, newCost, visited | v));
                }
            }
            return longest;
        }
    }
}
