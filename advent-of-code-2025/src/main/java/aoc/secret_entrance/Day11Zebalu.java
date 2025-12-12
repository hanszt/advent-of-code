package aoc.secret_entrance;

import aoc.utils.ChallengeDay;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toUnmodifiableMap;

/// [Solution Zebalu](https://github.com/zebalu/advent-of-code-2025/blob/master/Day11.java)
public record Day11Zebalu(Map<String, Set<String>> connections) implements ChallengeDay {

    public static Day11Zebalu fromPath(final Path path) {
        try (final var lines = Files.lines(path)) {
            return fromLines(lines);
        } catch (final IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static Day11Zebalu fromText(final String text) {
        return fromLines(text.lines());
    }

    static Day11Zebalu fromLines(final Stream<String> input) {
        final var map = input
                .map(line -> line.split(": "))
                .collect(toUnmodifiableMap(kv -> kv[0], kv -> Set.of(kv[1].split(" "))));
        return new Day11Zebalu(map);
    }

    @Override
    public Long part1() {
        return countPaths("you", "out");
    }

    @Override
    public Long part2() {
        final var fftToDac = countPaths("fft", "dac");
        if (fftToDac > 0L) {
            final var svrToFft = countPaths("svr", "fft");
            final var dacToOut = countPaths("dac", "out");
            return svrToFft * fftToDac * dacToOut;
        } else {
            final var svrToDac = countPaths("svr", "dac");
            final var dacToFft = countPaths("dac", "fft");
            final var fftToOut = countPaths("fft", "out");
            return svrToDac * dacToFft * fftToOut;
        }
    }

    long countPaths(
            final String start,
            final String end
    ) {
        return countPaths(new HashMap<>(), start, end);
    }

    long countPaths(
            final Map<String, Long> memo,
            final String start,
            final String end
    ) {
        if (memo.containsKey(start)) {
            return memo.get(start);
        }
        if (start.equals(end)) {
            return 1L;
        }
        var sum = 0L;
        final var s = connections.get(start);
        if (s != null) {
            var result = 0L;
            for (final var c : s) {
                result += countPaths(memo, c, end);
            }
            sum = result;
        }
        memo.put(start, sum);
        return sum;
    }
}
