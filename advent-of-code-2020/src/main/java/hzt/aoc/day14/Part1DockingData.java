package hzt.aoc.day14;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part1DockingData extends Day14Challenge {

    public Part1DockingData() {
        super("Docking Data part 1",
                "Execute the initialization program. What is the sum of all values left in memory after it completes?");
    }

    long count(final List<Program> programs) {
        final Map<Integer, Long> valuesInMemory =  new HashMap<>();
        for (final Program p : programs) {
            p.forEach(e -> valuesInMemory.put(e.right(), p.getValueStoredAfterBitMaskApplication(e.left())));
        }
        return valuesInMemory.values().stream().reduce(Long::sum).orElse(0L);
    }

    @Override
    String getMessage(final long global) {
        return String.format("%d", global);
    }

}
