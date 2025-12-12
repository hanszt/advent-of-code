package hzt.aoc.day14;

import java.util.*;
import java.util.stream.Collectors;

public class Program implements Iterable<Program.Entry> {

    private static final int BITMASK_LENGTH = 36;

    private final String bitMask;
    private final List<Entry> integerAsBinaryStringsToMemorySpotList = new ArrayList<>();


    public Program(final String bitMask) {
        this.bitMask = bitMask;
    }

    public void put(final int value, final int memSpot) {
        integerAsBinaryStringsToMemorySpotList.add(new Entry(value, memSpot));
    }

    @Override
    public Iterator<Entry> iterator() {
        return integerAsBinaryStringsToMemorySpotList.iterator();
    }

    public long getValueStoredAfterBitMaskApplication(final int value) {
        final var binaryString36 = convertIntToBinaryString(value);
        final var array = binaryString36.toCharArray();
        for (var i = 0; i < binaryString36.length(); i++) {
            if (bitMask.charAt(i) != 'X') {
                array[i] = bitMask.charAt(i);
            }
        }
        return Long.parseLong(String.valueOf(array), 2);
    }

    public Set<Long> getMemoryLocationsAfterBitMaskApplication(final int memoryAddress) {
        final var binaryString = convertIntToBinaryString(memoryAddress);
        Set<char[]> possibleMemoryLocations = new HashSet<>();
        final var array = binaryString.toCharArray();
        possibleMemoryLocations.add(array);
        for (var i = 0; i < binaryString.length(); i++) {
            if (bitMask.charAt(i) == 'X') {
                final Set<char[]> copy = new HashSet<>(possibleMemoryLocations);
                for (final var charArray : possibleMemoryLocations) {
                    final var newArray = Arrays.copyOf(charArray, charArray.length);
                    charArray[i] = '0';
                    newArray[i] = '1';
                    copy.add(newArray);
                }
                possibleMemoryLocations = new HashSet<>(copy);
            }
            if (bitMask.charAt(i) == '1') {
                for (final var possibleMemoryLocation : possibleMemoryLocations) {
                    possibleMemoryLocation[i] = bitMask.charAt(i);
                }
            }
        }
        return possibleMemoryLocations.stream().map(e -> Long.parseLong(String.valueOf(e), 2)).collect(Collectors.toSet());
    }

    private static String convertIntToBinaryString(final int integer) {
        final var binaryString = Integer.toBinaryString(integer);
        return "0".repeat(BITMASK_LENGTH - binaryString.length()).concat(binaryString);
    }

    @Override
    public String toString() {
        return "Program{" +
                "bitMask='" + bitMask + '\'' +
                ", integersAsBinaryStringsToMemorySpot=" + integerAsBinaryStringsToMemorySpotList +
                '}';
    }

    public record Entry(int binNr, int memSpot) {
    }

}
