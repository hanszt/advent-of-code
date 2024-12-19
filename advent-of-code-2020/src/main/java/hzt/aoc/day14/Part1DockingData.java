package hzt.aoc.day14;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part1DockingData extends Day14Challenge {

    public Part1DockingData() {
        super("Docking Data part 1",
                "Execute the initialization program. What is the sum of all values binNr in memory after it completes?");
    }

    long count(final List<Program> programs) {
        final Map<Integer, Long> valuesInMemory =  new HashMap<>();
        for (final Program program : programs) {
            for (Program.Entry pair : program) {
                valuesInMemory.put(pair.memSpot(), program.getValueStoredAfterBitMaskApplication(pair.binNr()));
            }
        }
        return valuesInMemory.values().stream().mapToLong(s -> s).sum();
    }

    @Override
    String getMessage(final long global) {
        return String.format("%d", global);
    }

}
