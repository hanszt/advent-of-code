package aoc.historianhysteria;

import aoc.utils.ChallengeDay;
import org.hzt.utils.io.FileX;

import java.nio.file.Path;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public final class Day03Java implements ChallengeDay {

    private static final Pattern regexp1 = Pattern.compile("mul\\((\\d{1,3},\\d{1,3})\\)");
    private static final Pattern regexp2 = Pattern.compile("mul\\((\\d{1,3},\\d{1,3})\\)|don't\\(\\)|do\\(\\)");
    private final String input;

    public Day03Java(Path fileName) {
        input = FileX.of(fileName).readText();
    }

    @Override
    public Integer part1() {
        return regexp1.matcher(input).results()
                .mapToInt(this::toProduct)
                .sum();
    }

    @Override
    public Integer part2() {
        var result = 0;
        var process = true;
        final var matcher = regexp2.matcher(input);
        while (matcher.find()) {
            var group = matcher.group();
            if ("do()".equals(group)) {
                process = true;
                continue;
            }
            if ("don't()".equals(group)) {
                process = false;
                continue;
            }
            if (process) {
                result += toProduct(matcher.toMatchResult());
            }
        }
        return result;
    }

    private int toProduct(MatchResult mr) {
        var last = mr.group(mr.groupCount());
        var split = last.split(",");
        return Integer.parseInt(split[0]) * Integer.parseInt(split[1]);
    }
}
