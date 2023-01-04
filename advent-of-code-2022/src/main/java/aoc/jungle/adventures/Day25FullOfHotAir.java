package aoc.jungle.adventures;

import aoc.utils.ChallengeDay;
import org.hzt.utils.collections.ListX;
import org.hzt.utils.io.FileX;
import org.jetbrains.annotations.NotNull;

import static java.lang.Math.pow;

/**
 * Credits to Angelo
 *
 * @see <a href="https://adventofcode.com/2022/day/25">Day 25</a>
 */
public class Day25FullOfHotAir implements ChallengeDay {

    private final ListX<String> lines;

    public Day25FullOfHotAir(String fileName) {
        lines = FileX.of(fileName).readLines();
    }

    @NotNull
    @Override
    public String part1() {
        return toSnafu(lines.longSumOf(this::fromSnafu));
    }

    @NotNull
    @Override
    public String part2() {
        return "No part 2. Merry christmas! 🎄";
    }

    long fromSnafu(String line) {
        long num = 0;
        for (int place = 0; place < line.length(); place++) {
            int pos = line.length() - 1 - place;
            char c = line.charAt(pos);
            num += pow(5, place) * switch (c) {
                case '=' -> -2;
                case '-' -> -1;
                default -> (c - '0');
            };
        }
        return num;
    }

    String toSnafu(long num) {
        final var result = new StringBuilder();
        while (num > 0) {
            int digit = (int) (num % 5);
            if (digit > 2) {
                digit -= 5;
                num = num / 5 + 1;
            } else {
                num /= 5;
            }
            char symbol = new char[]{'=', '-', '0', '1', '2'}[2 + digit];
            result.insert(0, symbol);
        }
        return result.toString();
    }
}
