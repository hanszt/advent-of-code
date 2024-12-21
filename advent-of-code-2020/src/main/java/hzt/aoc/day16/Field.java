package hzt.aoc.day16;

import aoc.utils.grid2d.GridPoint2D;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private static int next = 0;
    private final int nr;
    private final String fieldName;
    private final List<GridPoint2D> valueRanges = new ArrayList<>();

    public Field(final String fieldName) {
        this.nr = next++;
        this.fieldName = fieldName;
    }

    public void addRange(final GridPoint2D range) {
        if (range != null) {
            valueRanges.add(range);
        }
    }

    public boolean containsValueInRanges(final Integer integer) {
        for (final GridPoint2D p : valueRanges) {
            if (integer >= p.getX() && integer <= p.getY()) {
                return true;
            }
        }
        return false;
    }

    public int getNr() {
        return nr;
    }

    public String getFieldName() {
        return fieldName;
    }

    public static void setNext(final int next) {
        Field.next = next;
    }

    @Override
    public String toString() {
        return "Field{" +
                "nr=" + nr +
                ", fieldName='" + fieldName + '\'' +
                ", valueRanges=" + valueRanges +
                '}';
    }
}
