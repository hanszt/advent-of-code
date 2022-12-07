package aoc;

import aoc.utils.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Day07NoSpaceLeftOnDevice implements ChallengeDay {

    private final Map<AocFile, Integer> dirToSizes;
    private final AocFile root;


    public Day07NoSpaceLeftOnDevice(String fileName) {
        final var lines = FileUtils.useLines(Path.of(fileName), Stream::toList);
        this.root = new AocFile("/", null, -1);
        buildFileTree(lines, root);
        dirToSizes = buildDirToSizeMap(root);
    }

    @NotNull
    @Override
    public Integer part1() {
        return dirToSizes.values().stream()
                .mapToInt(size -> size)
                .filter(size -> size <= 100_000)
                .sum();
    }

    @NotNull
    @Override
    public Integer part2() {
        final int TOTAL_DISK_SPACE = 70_000_000;
        final int NEEDED_SPACE = 30_000_000;
        final var totalSize = dirToSizes.get(root);
        final int spaceToClean = NEEDED_SPACE - (TOTAL_DISK_SPACE - totalSize);
        return dirToSizes.values().stream()
                .mapToInt(size -> size)
                .filter(size -> size >= spaceToClean)
                .min()
                .orElseThrow(() -> new IllegalStateException("No minimum size found..."));
    }



    private static void buildFileTree(List<String> terminalOutput, AocFile root) {
        var current = root;
        for (int i = 1; i < terminalOutput.size(); i++) {
            String line = terminalOutput.get(i);
            if (line.contains("$ cd")) {
                if (line.contains("..")) {
                    current = current.parent;
                } else {
                    final var split = line.split(" ");
                    final var name = split[2];
                    final var newCurrent = new AocFile(name, current, -1);
                    current.children.add(newCurrent);
                    current = newCurrent;
                }
            }
            if (line.contains("$ ls")) {
                listChildren(terminalOutput, current, i);
            }
        }
    }

    private static void listChildren(List<String> terminalOutput, AocFile current, int i) {
        for (int j = i + 1; j < terminalOutput.size(); j++) {
            final var nextLine = terminalOutput.get(j);
            if (nextLine.contains("$")) {
                break;
            }
            final var split = nextLine.split(" ");
            final var sizeOrDir = split[0];
            if (!"dir".equals(sizeOrDir)) {
                current.children.add(new AocFile(split[1], current, Integer.parseInt(sizeOrDir)));
            }
        }
    }

    @NotNull
    private static Map<AocFile, Integer> buildDirToSizeMap(AocFile aocFile) {
        final var dirToSizes = new HashMap<AocFile, Integer>();
        sumSizes(aocFile, dirToSizes);
        return Map.copyOf(dirToSizes);
    }

    private static int sumSizes(AocFile aocFile, Map<AocFile, Integer> fileToSize) {
        if (aocFile.isDirectory()) {
            int sum = 0;
            for (var child : aocFile.children) {
                sum += sumSizes(child, fileToSize);
            }
            fileToSize.put(aocFile, sum);
            return sum;
        }
        return aocFile.size;
    }

    String treeAsString() {
        final var stringBuilder = new StringBuilder();
        treeAsString(root, 0, stringBuilder);
        return stringBuilder.toString();
    }

    private static void treeAsString(Day07NoSpaceLeftOnDevice.AocFile file, int level, StringBuilder stringBuilder) {
        final var parent = file.parent;
        stringBuilder.append(" ".repeat(level * 3))
                .append(file.isDirectory() ? ("dir: " + file.name) : "file: %s, (size %d)".formatted(file.name, file.size))
                .append(parent != null ? (", parent: " + parent.name) : "")
                .append("\n");
        if (file.children.isEmpty()) {
            return;
        }
        for (Day07NoSpaceLeftOnDevice.AocFile aocFile : file.children) {
            treeAsString(aocFile, level + 1, stringBuilder);
        }
    }

    private static final class AocFile {

        final String name;
        final int size;
        final AocFile parent;
        final List<AocFile> children = new ArrayList<>();

        AocFile(String name, AocFile parent, int size) {
            this.name = name;
            this.parent = parent;
            this.size = size;
        }

        boolean isDirectory() {
            return size < 0;
        }
    }

}
