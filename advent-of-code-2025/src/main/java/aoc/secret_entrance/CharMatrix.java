package aoc.secret_entrance;

import aoc.utils.grid2d.GridPoint2D;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CharMatrix implements Iterable<GridPoint2D> {

    private final char[][] content;
    private final char fill;

    public CharMatrix(final char[][] content, final char fill) {
        this.content = content;
        this.fill = fill;
    }

    public CharMatrix(final String[] content, final char fill) {
        final var width = Arrays.stream(content).mapToInt(String::length).max().orElse(0);
        this.content = new char[content.length][width];
        for (var y = 0; y < content.length; y++) {
            Arrays.fill(this.content[y], fill);
            for (var x = 0; x < content[y].length(); x++) {
                this.content[y][x] = content[y].charAt(x);
            }
        }
        this.fill = fill;
    }

    public CharMatrix(final String[] content)
    {
        this(content, ' ');
    }

    public CharMatrix(final String content) {
        this(content.split("\n"));
    }

    public CharMatrix(final int height, final int width, final char fill) {
        this.content = new char[height][width];
        for (var y = 0; y < height; y++) {
            Arrays.fill(this.content[y], fill);
        }
        this.fill = fill;
    }

    public int getWidth()
    {
        return content[0].length;
    }

    public int getHeight()
    {
        return content.length;
    }

    public boolean isValid(final int x, final int y)
    {
        return (x >= 0 && y >= 0 && x < getWidth() && y < getHeight());
    }

    public char get(final int x, final int y)
    {
        return content[y][x];
    }

    public void set(final int x, final int y, final char c)
    {
        content[y][x] = c;
    }

    public String getRow(final int y)
    {
        final var stringBuilder = new StringBuilder();
        for (var x = 0; x < content[y].length; x++) {
            stringBuilder.append(content[y][x]);
        }
        return stringBuilder.toString();
    }

    public String getColumn(final int x)
    {
        final var stringBuilder = new StringBuilder();
        for (char[] chars : content) {
            stringBuilder.append(chars[x]);
        }
        return stringBuilder.toString();
    }

    public CharMatrix transpose()
    {
        final var s = new String[getWidth()];
        for (var i = 0; i < getWidth(); i++) {
            s[i] = getColumn(i);
        }
        return new CharMatrix(s, fill);
    }

    public CharMatrix mirrorHorizontal()
    {
        final var newContent = new char[getHeight()][getWidth()];
        for (var y = 0; y < getHeight(); y++) {
            for (var x = 0; x < getWidth(); x++) {
                newContent[y][x] = content[y][getWidth() - x - 1];
            }
        }
        return new CharMatrix(newContent, fill);
    }

    public CharMatrix mirrorVertical()
    {
        final var newContent = new char[getHeight()][getWidth()];
        for (var y = 0; y < getHeight(); y++) {
            for (var x = 0; x < getWidth(); x++) {
                newContent[y][x] = content[getHeight() - y - 1][x];
            }
        }
        return new CharMatrix(newContent, fill);
    }

    @Override
    public Iterator<GridPoint2D> iterator() {
        return new GridPoint2DIterator();
    }

    public Stream<GridPoint2D> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (CharMatrix) o;
        return fill == that.fill && Arrays.deepEquals(content, that.content);
    }

    @Override
    public int hashCode() {
        var result = Objects.hash(fill);
        result = 31 * result + Arrays.deepHashCode(content);
        return result;
    }

    @Override
    public String toString()
    {
        return IntStream.range(0, content.length)
                .mapToObj(this::getRow)
                .collect(Collectors.joining("\n"));
    }

    private class GridPoint2DIterator implements Iterator<GridPoint2D>
    {
        private int x = 0;
        private int y = 0;

        @Override
        public boolean hasNext() {
            return y < getHeight();
        }

        @Override
        public GridPoint2D next() {
            final var cell = GridPoint2D.of(x, y);
            x++;
            if (x >= getWidth()) {
                x = 0;
                y++;
            }
            return cell;
        }
    }
}
