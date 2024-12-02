package aoc.historianhysteria;

import aoc.utils.ChallengeDay;
import org.hzt.utils.collections.ListX;
import org.hzt.utils.collections.primitives.IntList;
import org.hzt.utils.collections.primitives.IntMutableList;
import org.hzt.utils.io.FileX;
import org.hzt.utils.sequences.Sequence;
import org.hzt.utils.strings.StringX;

import java.util.stream.Stream;

public final class Day02 implements ChallengeDay {

    private final ListX<IntList> reports;

    public Day02(String fileName) {
        reports = FileX.of(fileName).useLines(Day02::toReports);
    }

    public Day02(Stream<String> s) {
        this.reports = toReports(s::iterator);
    }

    static ListX<IntList> toReports(Sequence<String> s) {
        return s
                .map(l -> StringX.of(l).split(" ").mapToInt(Integer::parseInt))
                .toListX();
    }

    @Override
    public Long part1() {
        return reports.count(r -> r.asSequence()
                .windowed(3)
                .all(Day02::isSafe));
    }

    @Override
    public Long part2() {
        return reports.count(Day02::isSafeAfterDampening);

    }

    private static boolean isSafeAfterDampening(IntList report) {
        var unsafeIndices = IntMutableList.empty();
        int windowIndex = 0;
        for (var w : report.asSequence().windowed(3)) {
            if (!isSafe(w)) {
                unsafeIndices.addAll(windowIndex, windowIndex + 1, windowIndex + 2);
                break;
            }
            windowIndex++;
        }
        if (unsafeIndices.isEmpty()) {
            return true;
        }
        for (var i : unsafeIndices) {
            var rMut = report.toMutableList();
            rMut.removeAt(i);
            boolean isSafe = true;
            for (var w : rMut.asSequence().windowed(3)) {
                if (!isSafe(w)) {
                    isSafe = false;
                    break;
                }
            }
            if (isSafe) {
                return true;
            }
        }
        return false;
    }

    private static boolean isSafe(IntList w) {
        var a = w.get(0);
        var b = w.get(1);
        var c = w.get(2);
        var abs1 = Math.abs(a - b);
        var abs2 = Math.abs(b - c);
        return inSafeRange(abs1) && inSafeRange(abs2) && isNotChangingDirection(a - b, b - c);
    }

    private static boolean inSafeRange(int n) {
        return n >= 1 && n <= 3;
    }

    private static boolean isNotChangingDirection(int d1, int d2) {
        return d1 != 0 && d2 != 0 && (d1 / Math.abs(d1)) == (d2 / Math.abs(d2));
    }
}
