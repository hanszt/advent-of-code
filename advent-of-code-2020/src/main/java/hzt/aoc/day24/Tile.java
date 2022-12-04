package hzt.aoc.day24;

import hzt.aoc.GridPoint2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Tile {

    static final String EAST = "e";
    static final String SOUTH_EAST = "se";
    static final String SOUTH_WEST = "sw";
    static final String WEST = "w";
    static final String NORTH_WEST = "nw";
    static final String NORTH_EAST = "ne";

    private static final Map<String, String> OPPOSITE_DIR = new HashMap<>();
    private static final Map<String, GridPoint2D> INSTRUCTION_TO_DIR = new HashMap<>();

    static {
        OPPOSITE_DIR.put(EAST, WEST);
        OPPOSITE_DIR.put(WEST, EAST);
        OPPOSITE_DIR.put(SOUTH_EAST, NORTH_WEST);
        OPPOSITE_DIR.put(NORTH_WEST, SOUTH_EAST);
        OPPOSITE_DIR.put(SOUTH_WEST, NORTH_EAST);
        OPPOSITE_DIR.put(NORTH_EAST, SOUTH_WEST);
        INSTRUCTION_TO_DIR.put(EAST, new GridPoint2D(1, 0));
        INSTRUCTION_TO_DIR.put(WEST, new GridPoint2D(-1, 0));
        INSTRUCTION_TO_DIR.put(SOUTH_EAST, new GridPoint2D(1, -1));
        INSTRUCTION_TO_DIR.put(NORTH_WEST, new GridPoint2D(-1, 1));
        INSTRUCTION_TO_DIR.put(SOUTH_WEST, new GridPoint2D(0, -1));
        INSTRUCTION_TO_DIR.put(NORTH_EAST, new GridPoint2D(0, 1));
    }

    private final GridPoint2D position;
    private boolean blackUp;

    private final Map<GridPoint2D, Tile> instructionsToNeighborsMap = new HashMap<>();

    public Tile(final GridPoint2D position) {
        this.position = position;
        this.blackUp = false;
    }

    void flip() {
        blackUp = !blackUp;
    }

    public Tile getNeighborByInstruction(final String instruction, final Map<GridPoint2D, Tile> allTiles) {
        final GridPoint2D delta = INSTRUCTION_TO_DIR.get(instruction);
        final GridPoint2D newPosition = new GridPoint2D(this.position.x() + delta.x(), this.position.y() + delta.y());
        final Tile neighbor;
        if (instructionsToNeighborsMap.get(delta) != null) {
            neighbor = instructionsToNeighborsMap.get(delta);
        } else if (allTiles.containsKey(newPosition)) {
            neighbor = allTiles.get(newPosition);
        } else {
            neighbor = new Tile(newPosition);
        }
        neighbor.instructionsToNeighborsMap.put(INSTRUCTION_TO_DIR.get(OPPOSITE_DIR.get(instruction)), this);
        this.instructionsToNeighborsMap.put(INSTRUCTION_TO_DIR.get(instruction), neighbor);
        return neighbor;
    }

    List<Tile> neighbors() {
        final List<Tile> neighbors = new ArrayList<>();
        for (final GridPoint2D delta : INSTRUCTION_TO_DIR.values()) {
            neighbors.add(new Tile(new GridPoint2D(position.x() + delta.x(), position.y() + delta.y())));
        }
        return neighbors;
    }

    public boolean isBlackUp() {
        return blackUp;
    }

    public GridPoint2D getPosition() {
        return position;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Tile tile = (Tile) o;
        return Objects.equals(position, tile.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    private String neighborsAsString() {
        final StringBuilder sb = new StringBuilder();
        instructionsToNeighborsMap.entrySet().forEach(e -> sb.append(neighborAsString(e)));
        return sb.toString();
    }

    private static String neighborAsString(final Map.Entry<GridPoint2D, Tile> e) {
        final GridPoint2D p = e.getValue().position;
        final GridPoint2D delta = e.getKey();
        return String.format("delta(x=%2d, y=%2d)->(position='(x=%3d, y=%3d)', blackUp=%5b) ",
                delta.x(), delta.y(), p.x(), p.y(), e.getValue().blackUp);
    }

    @Override
    public String toString() {
        return String.format("Tile{position='(x=%3d, y=%3d)', blackUp=%-5b, Neighbors={%s}}",
                position.x(), position.y(), blackUp, neighborsAsString());
    }
}
