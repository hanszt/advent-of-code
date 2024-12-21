package hzt.aoc.day20;

import aoc.utils.grid2d.GridPoint2D;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

// Credits to Johan de Jong
public class Tile {

    private static final int CORNERS = 4;
    private GridPoint2D position;
    private final List<String> content;
    private final List<List<String>> orientations;


    public Tile(final List<String> content) {
        this.content = List.copyOf(content);
        this.orientations = calculateOrientations(content);
    }

    private List<String> tileSides() {
        final List<String> sidesWithoutFlip = new ArrayList<>();
        sidesWithoutFlip.add(content.get(0));
        sidesWithoutFlip.add(content.get(content.size() - 1));
        final StringBuilder left = new StringBuilder();
        final StringBuilder right = new StringBuilder();
        for (final String line : content) {
            left.append(line.charAt(0));
            right.append(line.charAt(line.length() - 1));
        }
        sidesWithoutFlip.add(left.toString());
        sidesWithoutFlip.add(right.toString());

        final List<String> sides = new ArrayList<>();
        for (final String side : sidesWithoutFlip) {
            sides.add(side);
            sides.add(reverseString(side));
        }
        return sides;
    }

    public boolean isBorder(final Map<Integer, Tile> tiles) {
        final Set<String> otherTiles = otherTileBorders(tiles);
        final Set<String> sideTiles = new HashSet<>(tileSides());
        return countCommonElements(sideTiles, otherTiles) == CORNERS;
    }

    private static long countCommonElements(final Set<String> sideTiles, final Set<String> otherTiles) {
        return sideTiles.stream()
                .filter(otherTiles::contains)
                .count();
    }

    private Set<String> otherTileBorders(final Map<Integer, Tile> tiles) {
        return tiles.values().stream()
                .filter(not(this::equals))
                .map(Tile::tileSides)
                .flatMap(List::stream)
                .collect(Collectors.toSet());
    }

    private static List<List<String>> calculateOrientations(List<String> content) {
        List<List<String>> result = new ArrayList<>();
        collectRotations(result, content);
        collectRotations(result, flip(content));
        return result;
    }

    private static void collectRotations(List<List<String>> result, List<String> input) {
        result.add(input);
        List<String> temp = rotate(input);
        result.add(temp);
        temp = rotate(temp);
        result.add(temp);
        temp = rotate(temp);
        result.add(temp);
    }

    private static List<String> flip(List<String> content) {
        return content.stream()
                .map(Tile::reverseString)
                .toList();
    }

    private static String reverseString(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    private static List<String> rotate(final List<String> original) {
        final List<String> result = new ArrayList<>();
        for (int x = 0; x < original.size(); x++) {
            final StringBuilder sb = new StringBuilder();
            for (int y = original.size() - 1; y >= 0; y--) {
                sb.append(original.get(y).charAt(x));
            }
            result.add(sb.toString());
        }
        return result;
    }

    private static String asString(final List<String> content) {
        return "\n" + String.join("\n", content);
    }

    public String orientationsAsString() {
        return orientations.stream()
                .map(Tile::asString)
                .collect(Collectors.joining("\n"));
    }

    public String getTop() {
        return content.get(0);
    }

    public String getBottom() {
        return content.get(content.size() - 1);
    }

    public String getLeft() {
        final StringBuilder sb = new StringBuilder();
        for (final String line : content) {
            sb.append(line.charAt(0));
        }
        return sb.toString();
    }

    public String getRight() {
        final StringBuilder sb = new StringBuilder();
        for (final String line : content) {
            sb.append(line.charAt(line.length() - 1));
        }
        return sb.toString();
    }

    public List<String> getInner() {
        final List<String> result = new ArrayList<>();
        for (int i = 1; i < content.size() - 1; i++) {
            final String line = content.get(i);
            result.add(line.substring(1, line.length() - 1));
        }
        return result;
    }

    public List<List<String>> getOrientations() {
        return List.copyOf(orientations);
    }

    public GridPoint2D getPosition() {
        return position;
    }

    public void setPosition(final GridPoint2D position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "content=" + asString(content) +
                '}';
    }
}
