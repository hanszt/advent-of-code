package aoc;

import aoc.utils.FileUtils;
import org.hzt.graph.TreeNode;
import org.hzt.utils.collections.primitives.IntList;
import org.hzt.utils.collections.primitives.IntMutableList;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @see <a href="https://adventofcode.com/2022/day/7">Day 7: No space left on device</a>
 */
public class Day07NoSpaceLeftOnDevice implements ChallengeDay {

    private final IntList dirSizes;
    private final AocFile root;


    public Day07NoSpaceLeftOnDevice(String fileName) {
        final var lines = FileUtils.useLines(Path.of(fileName), Stream::toList);
        root = new AocFile("/", null, -1);
        buildFileTree(lines, root);
        dirSizes = extractDirSizes(root);
    }

    @NotNull
    @Override
    public Integer part1() {
        return dirSizes.stream()
                .filter(size -> size <= 100_000)
                .sum();
    }

    @NotNull
    @Override
    public Integer part2() {
        final int TOTAL_DISK_SPACE = 70_000_000;
        final int NEEDED_SPACE = 30_000_000;
        final var totalSize = dirSizes.max();
        final int spaceToClean = NEEDED_SPACE - (TOTAL_DISK_SPACE - totalSize);
        return dirSizes.stream()
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
                    current = current.getParent();
                } else {
                    final var split = line.split(" ");
                    final var name = split[2];
                    final var newCurrent = new AocFile(name, current, -1);
                    current.getChildren().add(newCurrent);
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
                current.getChildren().add(new AocFile(split[1], current, Integer.parseInt(sizeOrDir)));
            }
        }
    }

    @NotNull
    private static IntList extractDirSizes(AocFile aocFile) {
        final var sizes = IntMutableList.empty();
        sumSizes(aocFile, sizes);
        return sizes;
    }

    private static int sumSizes(AocFile aocFile, IntMutableList sizes) {
        if (aocFile.isDirectory()) {
            int sum = 0;
            for (var child : aocFile.getChildren()) {
                sum += sumSizes(child, sizes);
            }
            sizes.add(sum);
            return sum;
        }
        return aocFile.size;
    }

    public String toTreeString() {
        return root.toTreeString(3);
    }

    private static final class AocFile implements TreeNode<AocFile, AocFile> {

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

        @Override
        public AocFile getParent() {
            return parent;
        }

        public List<AocFile> getChildren() {
            return children;
        }

        @Override
        public String toString() {
            final var info = isDirectory() ? ("dir: " + this.name) : "file: %s, (size %d)".formatted(this.name, size);
            final var parentName = parent != null ? (", parent: " + parent.name) : "";
            return info + parentName;
        }
    }


}
