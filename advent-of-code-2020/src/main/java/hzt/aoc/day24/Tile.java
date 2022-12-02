package hzt.aoc.day24;

import hzt.aoc.Point2D;

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
    private static final Map<String, Point2D> INSTRUCTION_TO_DIR = new HashMap<>();

    static {
        OPPOSITE_DIR.put(EAST, WEST);
        OPPOSITE_DIR.put(WEST, EAST);
        OPPOSITE_DIR.put(SOUTH_EAST, NORTH_WEST);
        OPPOSITE_DIR.put(NORTH_WEST, SOUTH_EAST);
        OPPOSITE_DIR.put(SOUTH_WEST, NORTH_EAST);
        OPPOSITE_DIR.put(NORTH_EAST, SOUTH_WEST);
        INSTRUCTION_TO_DIR.put(EAST, new Point2D(1, 0));
        INSTRUCTION_TO_DIR.put(WEST, new Point2D(-1, 0));
        INSTRUCTION_TO_DIR.put(SOUTH_EAST, new Point2D(1, -1));
        INSTRUCTION_TO_DIR.put(NORTH_WEST, new Point2D(-1, 1));
        INSTRUCTION_TO_DIR.put(SOUTH_WEST, new Point2D(0, -1));
        INSTRUCTION_TO_DIR.put(NORTH_EAST, new Point2D(0, 1));
    }

    private final Point2D position;
    private boolean blackUp;
    private int nrOfBlackNeighbors;

    private final Map<Point2D, Tile> instructionsToNeighborsMap = new HashMap<>();

    public Tile(final Point2D position) {
        this.position = position;
        this.blackUp = false;
    }

    void flip() {
        blackUp = !blackUp;
    }

    public Tile getNeighborByInstruction(final String instruction, final Map<Point2D, Tile> allTiles) {
        final Point2D delta = INSTRUCTION_TO_DIR.get(instruction);
        final Point2D newPosition = new Point2D(this.position.x() + delta.x(), this.position.y() + delta.y());
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

    void countBlackNeighbors() {
        nrOfBlackNeighbors = 0;
        for (final Tile neighbor : instructionsToNeighborsMap.values()) {
            if (neighbor.isBlackUp()) {
                nrOfBlackNeighbors++;
            }
        }
    }

    void countBlackNeighbors(final Map<Point2D, Tile> allTiles) {
        nrOfBlackNeighbors = 0;
        for (final Point2D delta : INSTRUCTION_TO_DIR.values()) {
            final Point2D neighborPosition = new Point2D(this.position.x() + delta.x(), this.position.y() + delta.y());
            if (allTiles.containsKey(neighborPosition)) {
                final Tile neighbor = allTiles.get(neighborPosition);
                if (neighbor.isBlackUp()) {
                    nrOfBlackNeighbors++;
                }
            }
        }
    }

    //    Any black tile with zero or more than 2 black tiles immediately adjacent to it is flipped to white.
    //    Any white tile with exactly 2 black tiles immediately adjacent to it is flipped to black.
    void executeRule() {
        if (blackUp && (nrOfBlackNeighbors == 0 || nrOfBlackNeighbors > 2)) {
            flip();
        }
        if (!blackUp && nrOfBlackNeighbors == 2) {
            flip();
        }
    }

    List<Tile> neighbors() {
        final List<Tile> neighbors = new ArrayList<>();
        for (final Point2D delta : INSTRUCTION_TO_DIR.values()) {
            neighbors.add(new Tile(new Point2D(position.x() + delta.x(), position.y() + delta.y())));
        }
        return neighbors;
    }

    public boolean isBlackUp() {
        return blackUp;
    }

    public Point2D getPosition() {
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

    private static String neighborAsString(final Map.Entry<Point2D, Tile> e) {
        final Point2D p = e.getValue().position;
        final Point2D delta = e.getKey();
        return String.format("delta(x=%2d, y=%2d)->(position='(x=%3d, y=%3d)', blackUp=%5b) ",
                delta.x(), delta.y(), p.x(), p.y(), e.getValue().blackUp);
    }

    @Override
    public String toString() {
        return String.format("Tile{position='(x=%3d, y=%3d)', blackUp=%-5b, nrOfBlackNeighbors=%d, Neighbors={%s}}",
                position.x(), position.y(), blackUp, nrOfBlackNeighbors, neighborsAsString());
    }
}
