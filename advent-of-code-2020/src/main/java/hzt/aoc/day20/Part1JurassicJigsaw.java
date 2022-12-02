package hzt.aoc.day20;

import java.util.Map;

// Credits to Johan de Jong
public class Part1JurassicJigsaw extends Day20Challenge {

    public Part1JurassicJigsaw() {
        super("part 1",
                "Assemble the tiles into an image. What do you get if you multiply together the IDs of the four corner tiles?");
    }

    @Override
    protected long calculateAnswer(final Map<Integer, Tile> tiles) {
        return tiles.entrySet().stream()
                .filter(e -> e.getValue().isBorder(tiles))
                .mapToLong(Map.Entry::getKey)
                .reduce(1L, (acc, n) -> acc * n);
    }

    @Override
    String getMessage(final long global) {
        return String.format("%d", global);
    }

}
