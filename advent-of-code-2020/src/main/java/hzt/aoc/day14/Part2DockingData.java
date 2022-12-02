package hzt.aoc.day14;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part2DockingData extends Day14Challenge {

    public Part2DockingData() {
        super("Docking Data part 2",
                "Execute the initialization program using an emulator for a version 2 decoder chip. What is the sum of all values left in memory after it completes?");
    }

    long count(final List<Program> programs) {
        final Map<Long, Long> memoryAddressesToValues =  new HashMap<>();
        for (final Program p : programs) {
            p.forEach(pair -> p.getMemoryLocationsAfterBitMaskApplication(pair.right())
                    .forEach(memAdr -> memoryAddressesToValues.put(memAdr, (long) pair.left())));
        }
        return memoryAddressesToValues.values().stream().reduce(Long::sum).orElse(0L);
    }


    @Override
    String getMessage(final long value) {
        return String.format("%d", value);
    }
}
