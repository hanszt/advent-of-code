package aoc.secret_entrance.lib.simplex;


import aoc.secret_entrance.lib.BigRational;

import java.util.*;

public class Tableau {
    final List<SortedMap<Integer, BigRational>> columns = new ArrayList<>();
    final int width;
    final int height;

    Tableau(final int width, final int height) {
        this.width = width;
        this.height = height;
        for (var i = 0; i < width; i++) {
            columns.add(new TreeMap<>());
        }
    }

    void emptyMinusOneColumn() {
        columns.get(width - 2).clear();
    }

    void minusOneColumn() {
        emptyMinusOneColumn();
        final Map<Integer, BigRational> ht = columns.get(width - 2);
        for (var row = 0; row < height; row++) {
            ht.put(row, (row == 0) ? BigRational.ONE : BigRational.MINUS_ONE);
        }
    }

    BigRational get(final int j, final int i) {
        return columns.get(i).getOrDefault(j, BigRational.ZERO);
    }

    BigRational getTopRow(final int i) {
        return get(0, i);
    }

    BigRational getTopRowOrDefault(final int i) {
        return get(0, i);
    }

    void set(final int j, final int i, final BigRational value) {
        if (value.isZero()) {
            columns.get(i).remove(j);
        } else {
            columns.get(i).put(j, value);
        }
    }

    void add(final int j, final int i, final BigRational value) {
        set(j, i, get(j, i).add(value));
    }

    int findPivotColumn(final boolean aux) {
        final var limit = aux ? width - 1 : width - 2;
        for (var i = 0; i < limit; i++) {
            final var v = getTopRow(i);
            if (v.isNegative()) {
                return i;
            }
        }
        return -1;
    }

    int findPivotRow(final int i) {
        final var ht = columns.get(i);
        var j = -1;
        var d = BigRational.ZERO;

        for (final var el : ht.entrySet()) {
            final int row = el.getKey();
            if (row == 0) {
                continue;
            }

            final var a = el.getValue();
            if (!a.isPositive()) {
                continue;
            }

            final var b = get(row, width - 1);
            final var ba = b.divide(a);

            if (j == -1 || ba.compareTo(d) < 0) {
                j = row;
                d = ba;
            }
        }

        return j;
    }

    int findSmallestB() {
        var d = BigRational.ZERO;
        var j = -1;
        final var ht = columns.get(width - 1);

        for (final var el : ht.entrySet()) {
            if (j == -1 || el.getValue().compareTo(d) < 0) {
                j = el.getKey();
                d = el.getValue();
            }
        }

        return j;
    }

    void pivot(final int j, final int i) {
        // Pivot to (j,i)
        // Divide row j by the value at (j,i) ; skip column i since we set this column to the unit vector below
        final var d = get(j, i).reciprocal();
        for (var col = 0; col < width; col++) {
            if (col == i) {
                continue;
            }

            final var value = get(j, col).multiply(d);
            set(j, col, value);

            for (final var elp : columns.get(i).entrySet()) {
                if (elp.getKey() == j) {
                    continue;
                }
                add(elp.getKey(), col, elp.getValue().multiply(value).negate());
            }
        }

        // Replace column i by the unit vector with a one at column j
        columns.get(i).clear();
        columns.get(i).put(j, BigRational.ONE);
    }

    @Override
    public String toString() {
        final var sb = new StringBuilder();
        for (var y = 0; y < height; y++) {
            for (var x = 0; x < width; x++) {
                sb.append(get(y, x)).append('\t');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
