package aoc.secret_entrance;

import aoc.utils.ChallengeDay;
import org.hzt.utils.Patterns;
import org.hzt.utils.strings.StringX;

import java.util.List;
import java.util.Objects;
import java.util.function.LongPredicate;
import java.util.stream.LongStream;

public record Day02(List<IdRange> idRanges) implements ChallengeDay {

    public Day02(String idRanges) {
        this(Patterns.commaPattern.splitAsStream(idRanges)
                .map(IdRange::parse)
                .toList());
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
        return idRanges.stream()
                .flatMapToLong(idRange -> {
                    var min = Math.min(idRange.start, idRange.end);
                    var max = Math.max(idRange.start, idRange.end);
                    return LongStream.rangeClosed(min, max).filter(isInvalidId);
                }).sum();
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
        final var s = StringX.of(l);
        final var size = s.length();
        for (var chunkSize = 1; chunkSize <= size / 2; chunkSize++) {
            if (size % chunkSize != 0) {
                continue;
            }
            var isRepeating = s.codePointSequence()
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
