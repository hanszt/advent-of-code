package hzt.aoc.day20;

import hzt.aoc.Challenge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Day20Challenge extends Challenge {

    Day20Challenge(final String challengeTitle, final String description) {
        super(challengeTitle, description, "20201220-input-day20.txt");
    }

    @Override
    protected String solve(final List<String> inputList) {
        final var tileIdsToGrids = parseInput(inputList);
        return getMessage(calculateAnswer(tileIdsToGrids));
    }

    private static Map<Integer, Tile> parseInput(final List<String> inputList) {
        final Map<Integer, Tile> tileIdsToGrids = new HashMap<>();
        List<String> tileContent = new ArrayList<>();
        var tileId = 0;
        for (final var line : inputList) {
            if (line.isBlank()) {
                tileIdsToGrids.put(tileId, new Tile(tileContent));
            }
            if (line.contains("Tile")) {
                tileId = Integer.parseInt(line.replace("Tile ", "").replace(":", "").strip());
                tileContent = new ArrayList<>();
                continue;
            }
            if (!line.isEmpty()) {
                tileContent.add(line);
            }
        }
        tileIdsToGrids.forEach((k, v) -> LOGGER.trace(() -> k + "->" + v));
        LOGGER.trace(tileIdsToGrids::size);
        return tileIdsToGrids;
    }

    protected abstract long calculateAnswer(Map<Integer, Tile> tileIdsToGrids);


    abstract String getMessage(long value);
}
