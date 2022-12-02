package aoc.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.stream.Stream;

public final class FileUtils {

    private FileUtils() {
    }

    public static <R> R useLines(Path path, Function<Stream<String>, ? extends R> resultMapper) {
        try (final var lines = Files.lines(path)) {
            return resultMapper.apply(lines);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String readToString(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
