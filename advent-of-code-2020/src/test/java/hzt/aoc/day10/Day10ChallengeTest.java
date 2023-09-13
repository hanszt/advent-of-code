package hzt.aoc.day10;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static hzt.aoc.day10.Day10Challenge.MAX_STEP_APART;
import static java.util.function.Predicate.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day10ChallengeTest {

    @Test
    void testPart1() {
        final var challenge = new Part1AdaptorArray();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals(2_240, Integer.parseInt(answer));
    }

    @Test
    void testPart2WithCache() {
        final var challenge = new Part2AdaptorArrayWithCachingLongs();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals( 99_214_346_656_768L, Long.parseLong(answer));
    }

    @Test
    void testPart2WithoutCaching() throws IOException {
        final var list = sortedInput();
        list.add(0, 0); // add socket jolt value
        list.add(list.get(list.size() - 1) + MAX_STEP_APART); // add built in phone adaptor jolt value
        final var challenge = new Part2AdaptorArrayWithoutCaching();
        challenge.solveChallenge();
        final var answer = challenge.getAnswer();
        assertEquals( 8L, Long.parseLong(answer));
    }

    private static List<Integer> sortedInput() throws IOException {
        final var path = Optional.ofNullable(Day10ChallengeTest.class.getResource("/input/20201210-input-day10.txt"))
                .map(URL::getFile)
                .map(File::new)
                .map(File::toPath)
                .orElseThrow();
        try (final var lines = Files.lines(path)) {
            return lines
                    .filter(not(String::isEmpty))
                    .map(Integer::parseInt)
                    .sorted()
                    .collect(Collectors.toList());
        }
    }

}
