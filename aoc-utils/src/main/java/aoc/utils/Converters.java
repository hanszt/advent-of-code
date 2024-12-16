package aoc.utils;

public final class Converters {

    private Converters() {
    }

    public static int toInt(String s) {
        return Integer.parseInt(s.strip());
    }

    public static long toLong(String s) {
        return Long.parseLong(s.strip());
    }
}
