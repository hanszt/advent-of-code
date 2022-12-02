package hzt.aoc.day24;

import hzt.aoc.Point2D;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Credits to Johan de Jong
public class Part2LobbyLayout extends Day24Challenge {

    private static final int DAYS_OF_EXHIBIT = 100;

    public Part2LobbyLayout() {
        super("part 2",
                "How many tiles will be black after 100 days?");
    }

    @Override
    protected long calculateResult(final List<List<String>> instructions) {
        final Map<Point2D, Tile> tileMap = buildFloorByInstructions(instructions);
        final Set<Tile> blackTiles = tileMap.values().stream().filter(Tile::isBlackUp).collect(Collectors.toSet());
        for (int day = 0; day < DAYS_OF_EXHIBIT; day++) {
            simulate(blackTiles);
        }
        return blackTiles.size();
    }

    private static void simulate(final Set<Tile> blackTiles) {
        final Set<Tile> active = determineActiveSet(blackTiles);
        final Set<Tile> originalBlack = new HashSet<>(blackTiles);
        for (final Tile position : active) {
            final long blackNeighbours = countBlackNeighbours(originalBlack, position);
            if (originalBlack.contains(position) && (blackNeighbours == 0 || blackNeighbours > 2)) {
                blackTiles.remove(position);
            }
            if (blackNeighbours == 2) {
                blackTiles.add(position);
            }
        }
    }

    private static long countBlackNeighbours(final Set<Tile> originalBlack, final Tile startTile) {
        return startTile.neighbors().stream()
                .filter(originalBlack::contains)
                .count();
    }

    private static Set<Tile> determineActiveSet(final Set<Tile> blackTiles) {
        return Stream.concat(blackTiles.stream(), blackTiles.stream()
                        .map(Tile::neighbors)
                        .flatMap(List::stream))
                .collect(Collectors.toSet());
    }

    @Override
    String getMessage(final long global) {
        return String.format("%d", global);
    }
}
