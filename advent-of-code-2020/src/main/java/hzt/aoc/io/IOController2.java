package hzt.aoc.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IOController2 implements IIOController {

    public List<String> readInputFileByLine(final String fileName) {
        final URL url = getClass().getResource(RELATIVE_PATH + fileName);
        if (url != null) {
            try (final Scanner input = new Scanner(new File(url.getFile()))) {
                final List<String> inputList = new ArrayList<>();
                while (input.hasNextLine()) {
                    inputList.add(input.nextLine());
                }
                return inputList;
            } catch (final FileNotFoundException e) {
                throw new IllegalStateException("File with path " + RELATIVE_PATH + fileName + " not found...", e);
            }
        } else {
            throw new IllegalStateException("Resource url from relative path " + RELATIVE_PATH + fileName + " is null...");
        }
    }

    @Override
    public List<String> readInputFileByWord(final String fileName) {
        final URL url = getClass().getResource(RELATIVE_PATH + fileName);
        if (url != null) {
            try (final Scanner input = new Scanner(new File(url.getFile()))) {
                final List<String> inputList = new ArrayList<>();
                while (input.hasNext()) {
                    inputList.add(input.next());
                }
                return inputList;
            } catch (final FileNotFoundException e) {
                throw new IllegalStateException("File with path " + url.getFile() + " not found...", e);
            }
        } else {
            throw new IllegalStateException("Url is null...");
        }
    }
}
