package aoc.snowrescuemission;

import java.util.List;

public class Day24Abnew {

    private final List<Hailstone> stones;
    private final long min;
    private final long max;

    Day24Abnew(List<String> input, long min, long max) {
        stones = input.stream().map(Hailstone::new).toList();
        this.min = min;
        this.max = max;
    }

    long part1() {
        var answer = 0;
        for (var i = 0; i < stones.size(); i++) {
            for (var j = i + 1; j < stones.size(); j++) {
                final var first = stones.get(i);
                final var second = stones.get(j);
                final var denom = (first.xvel * second.yvel) - (first.yvel * second.xvel);
                final var numer1 = ((second.x - first.x) * second.yvel) - ((second.y - first.y) * second.xvel);
                final var numer2 = ((first.x - second.x) * first.yvel) - ((first.y - second.y) * first.xvel);
                if (denom != 0 && (numer1 / denom) > 0 && (numer2 / denom) < 0) {
                    final var intersectionX = (numer1 / (double) denom) * first.xvel + first.x;
                    final var intersectionY = (numer1 / (double) denom) * first.yvel + first.y;
                    if (intersectionX >= min && intersectionX <= max && intersectionY >= min && intersectionY <= max) {
                        answer++;
                    }
                }
            }
        }
        return answer;
    }

    private static class Hailstone {
        final long x;
        final long y;
        final long z;
        final int xvel;
        final int yvel;
        final int zvel;

        private Hailstone(String line) {
            final var ends = line.split("@");
            x = Long.parseLong(ends[0].split(",")[0].trim());
            y = Long.parseLong(ends[0].split(",")[1].trim());
            z = Long.parseLong(ends[0].split(",")[2].trim());
            xvel = Integer.parseInt(ends[1].split(",")[0].trim());
            yvel = Integer.parseInt(ends[1].split(",")[1].trim());
            zvel = Integer.parseInt(ends[1].split(",")[2].trim());
        }
    }
}
