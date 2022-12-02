package utils;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileUtilsTest {

    @Test
    void testUseLines() {
        final var toString = FileUtils.useLines(Path.of("input/iotest.txt"), s -> s.collect(Collectors.joining()));
        assertEquals("hallo dit is een test", toString);
    }
}
