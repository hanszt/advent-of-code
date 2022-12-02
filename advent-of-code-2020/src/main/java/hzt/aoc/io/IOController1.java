package hzt.aoc.io;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

public class IOController1 implements IIOController {

    private static final Logger LOGGER = LogManager.getLogger(IOController1.class);

    public List<String> readInputFileByLine(final String fileName) {
        final URL url = getClass().getResource(RELATIVE_PATH + fileName);
        if (url != null) {
            try {
                return Files.readAllLines(new File(url.getFile()).toPath());
            } catch (final IOException io) {
                LOGGER.error("File with path " + RELATIVE_PATH + fileName + " not found...");
                io.printStackTrace();
            }
        } else {
            LOGGER.error("Resource url from relative path " + RELATIVE_PATH + fileName + " is null...");
        }
        return Collections.emptyList();
    }

    @Override
    public List<String> readInputFileByWord(final String path) {
        throw new UnsupportedOperationException();
    }
}



