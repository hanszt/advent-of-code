package aoc.secret_entrance;

import aoc.utils.grid2d.Grid2DUtilsKt;
import aoc.utils.grid2d.GridPoint2D;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/// [Johan de Jong Day 12](https://github.com/johandj123/adventofcode2025/blob/master/src/Day12.java)
public record Day12JohanDeJong(String text) {

    public int run() {
        final var parts = text.split("\n\n");
        final var presents = new ArrayList<CharMatrix>();
        for (var i = 0; i < parts.length - 1; i++) {
            var s = parts[i];
            s = s.substring(s.indexOf('\n') + 1);
            presents.add(new CharMatrix(s));
        }
        final var regions = Arrays.stream(parts[parts.length - 1].split("\n")).map(Region::new).toList();
        final var presentSizes = presents.stream().map(Day12JohanDeJong::countGridPoint2Ds).toList();
        final var presentsAllOrientations = presents.stream().map(Day12JohanDeJong::allOrientations).toList();

        var count = 0;
        for (final var region : regions) {
            if (region.size() < region.countGridPoint2DsNeeded(presentSizes)) {
                continue;
            }
            if (region.canPlacePresents(presentsAllOrientations)) {
                count++;
            }
        }
        return count;
    }

    private static Set<CharMatrix> allOrientations(final CharMatrix charMatrix) {
        return reachable(charMatrix, c -> List.of(c, c.mirrorHorizontal(), c.mirrorVertical(), c.transpose()));
    }

    private static int countGridPoint2Ds(final CharMatrix charMatrix) {
        return (int) charMatrix.chars().filter(c -> c == '#').count();
    }

    static class Region {
        int width;
        int height;
        List<Integer> counts;

        public Region(final String line) {
            final var ints = extractPositiveIntegers(line);
            width = ints.get(0);
            height = ints.get(1);
            counts = ints.subList(2, ints.size());
        }

        public static List<Integer> extractPositiveIntegers(final String input) {
            final var parts = input.split("\\D");
            return Arrays.stream(parts)
                    .filter(s -> !s.isBlank())
                    .map(Integer::parseInt)
                    .toList();
        }

        int size() {
            return width * height;
        }

        int countGridPoint2DsNeeded(final List<Integer> presentSizes) {
            var result = 0;
            for (var i = 0; i < counts.size(); i++) {
                result += (counts.get(i) * presentSizes.get(i));
            }
            return result;
        }

        List<Set<CharMatrix>> presentsToBePlaced(final List<Set<CharMatrix>> presentsAllOrientations) {
            final var result = new ArrayList<Set<CharMatrix>>();
            for (var i = 0; i < counts.size(); i++) {
                for (var j = 0; j < counts.get(i); j++) {
                    result.add(presentsAllOrientations.get(i));
                }
            }
            return result;
        }

        @Override
        public String toString() {
            return "Region{" +
                    "width=" + width +
                    ", height=" + height +
                    ", counts=" + counts +
                    '}';
        }

        private boolean canPlacePresents(final List<Set<CharMatrix>> presentsAllOrientations) {
            final var charMatrix = new CharMatrix(height, width, '.');
            final var presentList = presentsToBePlaced(presentsAllOrientations);
            return canPlacePresents(charMatrix, presentList);
        }

        private boolean canPlacePresents(final CharMatrix charMatrix, final List<Set<CharMatrix>> presentsAllOrientations) {
            if (presentsAllOrientations.isEmpty()) {
                return true;
            }
            final var presentOrientations = presentsAllOrientations.getFirst();
            for (final var present : presentOrientations) {
                for (var y = 0; y < charMatrix.getHeight() - present.getHeight() + 1; y++) {
                    for (var x = 0; x < charMatrix.getWidth() - present.getWidth() + 1; x++) {
                        final var position = GridPoint2D.of(x, y);
                        if (canPlace(charMatrix, present, position)) {
                            place(charMatrix, present, position);
                            final var canPlace = canPlacePresents(charMatrix, presentsAllOrientations.subList(1, presentsAllOrientations.size()));
                            if (canPlace) {
                                return true;
                            }
                            unplace(charMatrix, present, position);
                        }
                    }
                }
            }
            return false;
        }

        private boolean canPlace(final CharMatrix charMatrix, final CharMatrix present, final GridPoint2D position) {
            for (var y = 0; y < present.getHeight(); y++) {
                for (var x = 0; x < present.getWidth(); x++) {
                    if (present.get(x, y) == '#' && charMatrix.get(position.getX() + x, position.getY() + y) == '#') {
                        return false;
                    }
                }
            }
            return true;
        }

        private void place(final CharMatrix charMatrix, final CharMatrix present, final GridPoint2D position) {
            set(present, charMatrix, position, '#');
        }

        private void unplace(final CharMatrix charMatrix, final CharMatrix present, final GridPoint2D position) {
            set(present, charMatrix, position, '.');
        }

        private static void set(final CharMatrix present, final CharMatrix charMatrix, final GridPoint2D position, final char c) {
            for (var y = 0; y < present.getHeight(); y++) {
                for (var x = 0; x < present.getWidth(); x++) {
                    if (present.get(x, y) == '#') {
                        charMatrix.set(position.getX() + x, position.getY() + y, c);
                    }
                }
            }
        }
    }

    public static <T> Set<T> reachable(final T start, final Function<? super T, ? extends Iterable<T>> toNeighbours) {
        final var startList = List.of(start);
        final var seen = new HashSet<>(startList);
        final var todo = new ArrayDeque<>(startList);
        while (!todo.isEmpty()) {
            final var node = todo.remove();
            for (final var nextNode : toNeighbours.apply(node)) {
                if (seen.add(nextNode)) {
                    todo.add(nextNode);
                }
            }
        }
        return seen;
    }

    record CharMatrix(char[][] content) {

        CharMatrix(final String[] content) {
            this(new char[content.length][Arrays.stream(content).mapToInt(String::length).max().orElse(0)]);
                for (var y = 0; y < content.length; y++) {
                    for (var x = 0; x < content[y].length(); x++) {
                        this.content[y][x] = content[y].charAt(x);
                    }
                }
            }

            CharMatrix(final String content) {
                this(content.split("\n"));
            }

            CharMatrix(final int height, final int width, final char fill) {
                this(new char[height][width]);
                for (var y = 0; y < height; y++) {
                    Arrays.fill(this.content[y], fill);
                }
            }

            IntStream chars() {
                return Arrays.stream(content).flatMapToInt(s -> new String(s).chars());
            }

            int getWidth() {
                return content[0].length;
            }

            int getHeight() {
                return content.length;
            }

            char get(final int x, final int y) {
                return content[y][x];
            }

            void set(final int x, final int y, final char c) {
                content[y][x] = c;
            }

            String getRow(final int y) {
                final var stringBuilder = new StringBuilder();
                for (var x = 0; x < content[y].length; x++) {
                    stringBuilder.append(content[y][x]);
                }
                return stringBuilder.toString();
            }

            String getColumn(final int x) {
                final var stringBuilder = new StringBuilder();
                for (char[] chars : content) {
                    stringBuilder.append(chars[x]);
                }
                return stringBuilder.toString();
            }

            CharMatrix transpose() {
                final var s = new String[getWidth()];
                for (var i = 0; i < getWidth(); i++) {
                    s[i] = getColumn(i);
                }
                return new CharMatrix(s);
            }

            CharMatrix mirrorHorizontal() {
                return new CharMatrix(Grid2DUtilsKt.mirroredHorizontally(content));
            }

            CharMatrix mirrorVertical() {
                return new CharMatrix(Grid2DUtilsKt.mirroredVertically(content));
            }

            @Override
            public boolean equals(final Object o) {
                return this == o || (o instanceof CharMatrix(char[][] other) && Arrays.deepEquals(content, other));
            }

            @Override
            public int hashCode() {
                return Arrays.deepHashCode(content);
            }

            @Override
            public String toString() {
                return IntStream.range(0, content.length)
                        .mapToObj(this::getRow)
                        .collect(Collectors.joining("\n"));
            }
        }
}
