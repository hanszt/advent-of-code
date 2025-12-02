package aoc.secret_entrance;

import aoc.utils.ChallengeDay;
import org.hzt.utils.Patterns;
import org.hzt.utils.strings.StringX;

import java.util.List;
import java.util.Objects;
import java.util.function.LongPredicate;
import java.util.stream.LongStream;

public final class Day02 implements ChallengeDay {

    private final List<IdRange> idRanges;

    public Day02(String idRanges) {
        this.idRanges = Patterns.commaPattern.splitAsStream(idRanges)
                .map(IdRange::parse)
                .toList();
    }

    @Override
    public Long part1() {
        return invalidIdSum(Day02::isInvalidIdP1);
    }

    @Override
    public Long part2() {
        return invalidIdSum(Day02::isInvalidIdP2);
    }

    private long invalidIdSum(LongPredicate isInvalidId) {
        var invalidIds = LongStream.builder();
        for (var idRange : idRanges) {
            var min = Math.min(idRange.start, idRange.end);
            var max = Math.max(idRange.start, idRange.end);
            LongStream.rangeClosed(min, max)
                    .filter(isInvalidId)
                    .forEach(invalidIds::add);
        }
        return invalidIds.build().sum();
    }

    private static boolean isInvalidIdP1(long l) {
        var s = String.valueOf(l);
        if (s.length() % 2 == 0) {
            var start = s.substring(0, s.length() / 2);
            var end = s.substring(s.length() / 2);
            return start.equals(end);
        }
        return false;
    }

    private static boolean isInvalidIdP2(long l) {
        final var s = String.valueOf(l);
        final var size = s.length();
        for (var chunkSize = 1; chunkSize <= size / 2; chunkSize++) {
            if (size % chunkSize != 0) {
                continue;
            }
            var isRepeating = StringX.of(s).codePointSequence()
                    .chunked(chunkSize)
                    .zipWithNext(Objects::equals)
                    .all(b -> b);
            if (isRepeating) {
                return true;
            }
        }
        return false;
    }

    record IdRange(long start, long end) {
        static IdRange parse(String s) {
            final var split = s.split("-");
            return new IdRange(Long.parseLong(split[0]), Long.parseLong(split[1]));
        }
    }
}
