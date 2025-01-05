package aoc.jungle.adventures;

import aoc.utils.ChallengeDay;
import aoc.utils.grid2d.GridPoint2D;
import org.hzt.utils.io.FileX;
import org.hzt.utils.ranges.IntRange;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static aoc.utils.Converters.toInt;
import static aoc.utils.grid2d.GridPoint2DKt.GridPoint2D;
import static java.util.function.Predicate.not;

/**
 * Credits to Angelo Wentzler
 *
 * @see <a href="https://www.linkedin.com/in/angelowentzler/?originalSubdomain=nl"></a>
 *
 * @see <a href="https://adventofcode.com/2022/day/15">Day 15: Beacon exclusion zone</a>
 */
public class Day15BeaconExclusionZone implements ChallengeDay {

    private static final Pattern LINE_PATTERN = Pattern
            .compile("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)");
    private final Map<GridPoint2D, GridPoint2D> sensorBeaconMap;

    public Day15BeaconExclusionZone(String fileName) {
        sensorBeaconMap = FileX.of(fileName)
                .useLines(s -> s.map(LINE_PATTERN::matcher)
                        .filter(Matcher::find)
                        .toMap(m -> GridPoint2D(toInt(m.group(1)), toInt(m.group(2))),
                                m -> GridPoint2D(toInt(m.group(3)), toInt(m.group(4)))));
    }

    @NotNull
    @Override
    public Integer part1() {
        final int TARGET_Y = 2_000_000;

        final var beacons = new HashSet<>();
        final var sensors = new HashSet<>();
        final var areaChecked = new HashSet<>();

        for (var entry : sensorBeaconMap.entrySet()) {
            final var sensor = entry.getKey();
            final var beacon = entry.getValue();
            sensors.add(sensor);
            beacons.add(beacon);
            final var distance = sensor.manhattanDistance(beacon);
            if (sensor.getY() - distance <= TARGET_Y && sensor.getY() + distance >= TARGET_Y) {

                final Predicate<GridPoint2D> isRequiredDistance = p -> sensor.manhattanDistance(p) <= distance;
                IntRange.closed(sensor.getX() - distance, sensor.getX() + distance)
                        .mapToObj(x -> GridPoint2D(x, TARGET_Y))
                        .filter(isRequiredDistance.and(not(beacons::contains)).and(not(sensors::contains)))
                        .forEach(areaChecked::add);
            }
        }
        return areaChecked.size();
    }

    @NotNull
    @Override
    public Long part2() {
        final long BOUND = 4_000_000L;
        for (var entry : sensorBeaconMap.entrySet()) {
            final var sensor = entry.getKey();
            final var radius = sensor.manhattanDistance(entry.getValue()) + 1;
            final var ring = new HashSet<GridPoint2D>();
            for (var i = 0; i < radius; i++) {
                addToRingIfInLimit(ring, sensor.getX() + radius - i, sensor.getY() + i);
                addToRingIfInLimit(ring, sensor.getX() - radius + i, sensor.getY() - i);
                addToRingIfInLimit(ring, sensor.getX() + i, sensor.getY() - radius + i);
                addToRingIfInLimit(ring, sensor.getX() - i, sensor.getY() + radius - i);
            }
            removeIfInSensorRange(sensorBeaconMap, ring);
            final var onSensorsRing = ring.size() == 1;
            if (onSensorsRing) {
                final var p = ring.iterator().next();
                return BOUND * p.getX() + p.getY();
            }
        }
        throw new IllegalStateException("No solution found");
    }

    private static void addToRingIfInLimit(Set<GridPoint2D> ring, int x, int y) {
        final long UPPER_BOUND = 4_000_000;
        if (x >= 0 && y >= 0 && x <= UPPER_BOUND && y <= UPPER_BOUND) {
            ring.add(GridPoint2D(x, y));
        }
    }

    private static void removeIfInSensorRange(Map<GridPoint2D, GridPoint2D> sensorBeaconMap, Set<GridPoint2D> ring) {
        final var iterator = ring.iterator();
        while (iterator.hasNext()) {
            final var ringPoint = iterator.next();
            for (var e : sensorBeaconMap.entrySet()) {
                final var otherSensor = e.getKey();
                final var point2D = e.getValue();
                final var fallsInRange = otherSensor.manhattanDistance(ringPoint) <= otherSensor.manhattanDistance(point2D);
                if (fallsInRange) {
                    iterator.remove();
                    break;
                }
            }
        }
    }
}
