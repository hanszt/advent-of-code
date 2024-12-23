package hzt.aoc.day20;

import hzt.aoc.AocLogger;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TileTest {

    private static final AocLogger aocLogger = AocLogger.getLogger(TileTest.class);

    @Test
    void testOrientationsWithP() {
        aocLogger.setLevel(System.Logger.Level.TRACE);
        final List<String> list = new ArrayList<>();
        list.add("#### ");
        list.add("#   #");
        list.add("#### ");
        list.add("#    ");
        list.add("#    ");
        final Tile tile = new Tile(list);
        final var orientationsAsString = tile.orientationsAsString();
        aocLogger.trace(() -> orientationsAsString);

        final var oriCount = countNrOfHashTags(String.join("", list));
        final var resultCount = countNrOfHashTags(orientationsAsString);
        assertEquals(oriCount * 8, resultCount);
    }

    private long countNrOfHashTags(final String pattern) {
        return pattern.chars()
                .mapToObj(c -> (char) c)
                .filter(c -> c == '#')
                .count();
    }

}
