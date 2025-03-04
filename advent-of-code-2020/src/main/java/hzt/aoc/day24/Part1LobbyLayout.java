package hzt.aoc.day24;

import aoc.utils.grid2d.GridPoint2D;

import java.util.List;
import java.util.Map;

public class Part1LobbyLayout extends Day24Challenge {

    public Part1LobbyLayout() {
        super("part 1",
                "Go through the renovation crew's list and determine which tiles they need to flip. " +
                        "After all of the instructions have been followed, how many tiles are left with the black side up?");
    }


    @Override
    protected long calculateResult(final List<List<String>> instructionsList) {
        final Map<GridPoint2D, Tile> tileMap = buildFloorByInstructions(instructionsList);
        return countTilesWithBlackSideUp(tileMap.values());
    }

    @Override
    String getMessage(final long result) {
        return String.format("%d", result);
    }

}
