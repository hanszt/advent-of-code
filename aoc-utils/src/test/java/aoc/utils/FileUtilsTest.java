package aoc.utils;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileUtilsTest {

    @Test
    void testUseLines() {
        final var path = resourcePath("/test.txt");
        final var toString = FileUtils.useLines(path, s -> s.collect(Collectors.joining()));
        assertEquals("hello this is a test", toString);
    }

    @NotNull
    public static Path resourcePath(String name) {
        return Optional.ofNullable(FileUtilsTest.class.getResource(name))
                .map(URL::getFile)
                .map(File::new)
                .map(File::toPath)
                .orElseThrow();
    }
}
