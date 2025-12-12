package hzt.aoc.day20;

import aoc.utils.grid2d.GridPoint2D;

import java.util.*;

// Credits to Johan de Jong
public class Part2JurassicJigsaw extends Day20Challenge {

    private int sideLength;

    public Part2JurassicJigsaw() {
        super("part 2",
                "Determine how rough the waters are in the sea monsters' habitat by counting the number of # " +
                        "that are not part of a sea monster. How many # are not part of a sea monster?");
    }

    @Override
    protected long calculateAnswer(final Map<Integer, Tile> tileIdsToGrids) {
        sideLength = (int) Math.sqrt(tileIdsToGrids.size());
        final var pictureSideLength = (int) Math.sqrt(tileIdsToGrids.size());
        final Set<Integer> placedIds = new HashSet<>();
        final var placedTiles = new Tile[pictureSideLength][pictureSideLength];
        final var complete = placeNextTile(placedIds, placedTiles, GridPoint2D.ZERO, tileIdsToGrids);
        if (complete) {
            final var fullPicture = buildFullPicture(placedTiles, pictureSideLength);
            return countHowManyHashesNotPartOfSeeMonster(fullPicture);
        }
        return 0L;
    }

    private static long countHowManyHashesNotPartOfSeeMonster(final List<String> fullPicture) {
        final var seeMonster = createSeeMonster();
        final var fullPictureTile = new Tile(fullPicture);
        for (final var orientation : fullPictureTile.getOrientations()) {
            final List<String> markedList = new ArrayList<>(orientation);
            final var marked = markPatterns(seeMonster, orientation, markedList);
            if (marked) {
                return countHashes(markedList);
            }
        }
        throw new IllegalArgumentException("No matching pattern found...");
    }

    private static boolean markPatterns(final List<String> seeMonster, final List<String> orientation, final List<String> markedList) {
        var marked = false;
        for (var y = 0; y <= orientation.size() - seeMonster.size(); y++) {
            for (var x = 0; x <= orientation.get(0).length() - seeMonster.get(0).length(); x++) {
                if (isPatternAt(orientation, seeMonster, x, y)) {
                    markPatternAt(markedList, seeMonster, x, y);
                    marked = true;
                }
            }
        }
        return marked;
    }

    private static List<String> buildFullPicture(final Tile[][] placedTileGrid,
                                                 final int pictureSideLength) {
        final List<String> fullPicture = new ArrayList<>();
        for (var y = 0; y < pictureSideLength; y++) {
            final List<List<String>> inners = new ArrayList<>();
            for (var x = 0; x < pictureSideLength; x++) {
                inners.add(placedTileGrid[y][x].getInner());
            }
            for (var j = 0; j < inners.get(0).size(); j++) {
                final var sb = new StringBuilder();
                for (final var inner : inners) {
                    sb.append(inner.get(j));
                }
                fullPicture.add(sb.toString());
            }
        }
        return fullPicture;
    }

    private static List<String> createSeeMonster() {
        return List.of(
                "                  # ",
                "#    ##    ##    ###",
                " #  #  #  #  #  #   ");
    }

    private static boolean isPatternAt(final List<String> fullPicture,
                                       final List<String> pattern, final int x, final int y) {
        for (var dy = 0; dy < pattern.size(); dy++) {
            for (var dx = 0; dx < pattern.get(0).length(); dx++) {
                final var patternMatch = pattern.get(dy).charAt(dx) == '#';
                final var noMatchInPicture = fullPicture.get(y + dy).charAt(x + dx) != '#';
                if (patternMatch && noMatchInPicture) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void markPatternAt(final List<String> fullPicture, final List<String> pattern, final int x, final int y) {
        for (var dy = 0; dy < pattern.size(); dy++) {
            for (var dx = 0; dx < pattern.get(0).length(); dx++) {
                if (pattern.get(dy).charAt(dx) == '#') {
                    final var originalLine = fullPicture.get(y + dy);
                    final var position = x + dx;
                    final var newLine = originalLine.substring(0, position) + "O" + originalLine.substring(position + 1);
                    fullPicture.set(y + dy, newLine);
                }
            }
        }
    }

    private static long countHashes(final List<String> fullPicture) {
        return fullPicture.stream()
                .flatMapToInt(String::chars)
                .filter(c -> c == '#')
                .count();
    }

    private boolean placeNextTile(final Set<Integer> placedIds,
                                  final Tile[][] placed,
                                  final GridPoint2D curPosition,
                                  final Map<Integer, Tile> tiles) {
        for (final var entry : tiles.entrySet()) {
            if (!placedIds.contains(entry.getKey())) {
                final var curTile = entry.getValue();
                curTile.setPosition(curPosition);
                placedIds.add(entry.getKey());
                final var orientationFits = orientationFits(placed, curTile, placedIds, tiles);
                if (orientationFits) {
                    return true;
                }
                placedIds.remove(entry.getKey());
            }
        }
        return false;
    }

    private boolean orientationFits(final Tile[][] placed,
                                    final Tile curTile,
                                    final Set<Integer> placedIds,
                                    final Map<Integer, Tile> tiles) {
        for (final var orientation : curTile.getOrientations()) {
            final var cur = curTile.getPosition();
            placed[cur.getY()][cur.getX()] = new Tile(orientation);
            if (canPlaceTile(placed, cur)) {
                final var next = nextPosition(cur);
                final var allTilesPlaced = next.getY() == sideLength;
                final var nextTilePlaced = placeNextTile(placedIds, placed, next, tiles);
                if (allTilesPlaced || nextTilePlaced) {
                    return true;
                }
            }
        }
        return false;
    }

    private GridPoint2D nextPosition(final GridPoint2D gridPoint2D) {
        var nextX = gridPoint2D.getX() + 1;
        var nextY = gridPoint2D.getY();
        if (nextX == sideLength) {
            nextX = 0;
            nextY++;
        }
        return GridPoint2D.of(nextX, nextY);
    }

    private static boolean canPlaceTile(final Tile[][] placed, final GridPoint2D p) {
        if (p.getX() > 0 && !placed[p.getY()][p.getX() - 1].getRight().equals(placed[p.getY()][p.getX()].getLeft())) {
            return false;
        }
        if (p.getY() > 0) {
            return placed[p.getY() - 1][p.getX()].getBottom().equals(placed[p.getY()][p.getX()].getTop());
        }
        return true;
    }


    @Override
    String getMessage(final long global) {
        return String.format("%d", global);
    }
}
