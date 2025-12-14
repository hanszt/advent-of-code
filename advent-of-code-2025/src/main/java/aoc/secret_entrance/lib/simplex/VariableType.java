package aoc.secret_entrance.lib.simplex;

public enum VariableType {
    REAL_NONNEGATIVE(true, false),
    REAL(false, false),
    INTEGER_NONNEGATIVE(true, true),
    INTEGER(false, true),
    BOOLEAN(true, true);

    private final boolean nonNegative;
    private final boolean isInteger;

    VariableType(final boolean nonNegative, final boolean isInteger) {
        this.nonNegative = nonNegative;
        this.isInteger = isInteger;
    }

    public boolean isNonNegative() {
        return nonNegative;
    }

    public boolean isInteger() {
        return isInteger;
    }
}
