package aoc.snowrescuemission;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * <a href="https://github.com/zebalu/advent-of-code-2023/blob/master/aoc2023/src/main/java/io/github/zebalu/aoc2023/days/Day17.java">Day 17 java source</a>
 */
public final class Day17Java {

    private final List<List<Integer>> maze;

    public Day17Java(List<String> input) {
        maze = input.stream().map(l -> l.chars().map(i -> i - '0').boxed().toList()).toList();
    }

    public int part1() {
        return minHeatLoss(maze, 3, (_, _) -> true, _ -> true);
    }

    public int part2() {
        return minHeatLoss(maze, 10, (c, s) -> 4 <= s.straightLength() || s.pos().directionOf(c) == s.dir(), i -> 4 <= i);
    }

    private static int minHeatLoss(List<List<Integer>> maze, int maxLength, BiPredicate<Coord, State> nextFilter, Predicate<Integer> stopFilter) {
        int height = maze.size();
        int width = maze.getFirst().size();
        Predicate<Coord> isValidCoord = c -> 0 <= c.x() && 0 <= c.y() && c.x < width && c.y < height;
        Predicate<State> isValidState = s -> s.straightLength() <= maxLength;
        Coord target = new Coord(width - 1, height - 1);
        Map<Long, Integer> bestCost = HashMap.newHashMap(1_000_000);
        Queue<State> queue = new PriorityQueue<>(10_000, Comparator.comparingLong(State::heatLoss));
        queue.add(new State(new Coord(0, 0), 0, 0, Direction.EAST));
        while (!queue.isEmpty()) {
            State curr = queue.poll();
            List<Coord> nexts = curr.pos().nexts(curr.dir());
            List<State> nextStates = nexts
                    .stream()
                    .filter(c -> isValidCoord.test(c) && nextFilter.test(c, curr))
                    .map(c -> new State(c, curr.nextStraight(c), curr.heatLoss() + heatCost(c, maze), curr.pos().directionOf(c)))
                    .filter(isValidState)
                    .toList();
            for (State s : nextStates) {
                if (s.pos().equals(target) && stopFilter.test(s.straightLength())) {
                    return s.heatLoss();
                }
                long costKey = s.toKey();
                if (!bestCost.containsKey(costKey) || s.heatLoss() < bestCost.get(costKey)) {
                    bestCost.put(costKey, s.heatLoss());
                    queue.add(s);
                }
            }
        }
        throw new IllegalStateException("Can not solve maze");
    }

    private static int heatCost(Coord coord, List<List<Integer>> maze) {
        return maze.get(coord.y()).get(coord.x());
    }

    private enum Direction {
        NORTH, EAST, SOUTH, WEST
    }

    private record Coord(int x, int y) {
        List<Coord> nexts(Direction dir) {
            return switch (dir) {
                case EAST -> List.of(new Coord(x + 1, y), new Coord(x, y - 1), new Coord(x, y + 1));
                case WEST -> List.of(new Coord(x - 1, y), new Coord(x, y - 1), new Coord(x, y + 1));
                case NORTH -> List.of(new Coord(x, y - 1), new Coord(x + 1, y), new Coord(x - 1, y));
                case SOUTH -> List.of(new Coord(x, y + 1), new Coord(x + 1, y), new Coord(x - 1, y));
            };
        }

        Direction directionOf(Coord coord) {
            if (y == coord.y) {
                if (x < coord.x) {
                    return Direction.EAST;
                } else if (x > coord.x) {
                    return Direction.WEST;
                }
            } else if (x == coord.x) {
                if (y < coord.y) {
                    return Direction.SOUTH;
                } else {
                    return Direction.NORTH;
                }
            }
            throw new IllegalArgumentException(this + " is equal with " + coord + " ?");
        }
    }

    private record State(Coord pos, int straightLength, int heatLoss, Direction dir) {

        int nextStraight(Coord c) {
            if (pos.directionOf(c) == dir) {
                return straightLength + 1;
            }
            return 1;
        }

        long toKey() {
            return pos.x() * 1_000_000L + pos.y() * 1_000L + straightLength() * 10L + dir.ordinal();
        }

    }
}
