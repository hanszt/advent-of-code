package hzt.aoc.day14;

import hzt.aoc.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Program implements Iterable<Pair<Integer, Integer>> {

    private static final int BITMASK_LENGTH = 36;

    private final String bitMask;
    private final List<Pair<Integer, Integer>> integerAsBinaryStringsToMemorySpotList = new ArrayList<>();


    public Program(final String bitMask) {
        this.bitMask = bitMask;
    }

    public void put(final int value, final int memSpot) {
        integerAsBinaryStringsToMemorySpotList.add(new Pair<>(value, memSpot));
    }

    @Override
    public Iterator<Pair<Integer, Integer>> iterator() {
        return integerAsBinaryStringsToMemorySpotList.iterator();
    }

    public Long getValueStoredAfterBitMaskApplication(final int value) {
        final String binaryString36 = convertIntToBinaryString(value);
        final char[] array = binaryString36.toCharArray();
        for (int i = 0; i < binaryString36.length(); i++) {
            if (bitMask.charAt(i) != 'X') {
                array[i] = bitMask.charAt(i);
            }
        }
        return Long.parseLong(String.valueOf(array), 2);
    }

    public Set<Long> getMemoryLocationsAfterBitMaskApplication(final int memoryAddress) {
        final String binaryString = convertIntToBinaryString(memoryAddress);
        Set<char[]> possibleMemoryLocations = new HashSet<>();
        final char[] array = binaryString.toCharArray();
        possibleMemoryLocations.add(array);
        for (int i = 0; i < binaryString.length(); i++) {
            if (bitMask.charAt(i) == 'X') {
                final Set<char[]> copy = new HashSet<>(possibleMemoryLocations);
                for (final char[] charArray : possibleMemoryLocations) {
                    final char[] newArray = Arrays.copyOf(charArray, charArray.length);
                    charArray[i] = '0';
                    newArray[i] = '1';
                    copy.add(newArray);
                }
                possibleMemoryLocations = new HashSet<>(copy);
            }
            if (bitMask.charAt(i) == '1') {
                for (final char[] possibleMemoryLocation : possibleMemoryLocations) {
                    possibleMemoryLocation[i] = bitMask.charAt(i);
                }
            }
        }
        return possibleMemoryLocations.stream().map(e -> Long.parseLong(String.valueOf(e), 2)).collect(Collectors.toSet());
    }

    private static String convertIntToBinaryString(final int integer) {
        final String binaryString = Integer.toBinaryString(integer);
        return "0".repeat(BITMASK_LENGTH - binaryString.length()).concat(binaryString);
    }

    @Override
    public String toString() {
        return "Program{" +
                "bitMask='" + bitMask + '\'' +
                ", integersAsBinaryStringsToMemorySpot=" + integerAsBinaryStringsToMemorySpotList +
                '}';
    }

}
