package hzt.aoc.io;

import java.util.List;

public interface IIOController {

    String RELATIVE_PATH = "/input/";

    List<String> readInputFileByLine(String path);

    List<String> readInputFileByWord(String path);
}
