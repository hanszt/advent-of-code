package aoc.secret_entrance.lib.simplex;

import aoc.secret_entrance.lib.BigRational;

public class Constraint {
    private final ConstraintType type;
    private final int firstTerm;
    private int countTerms;
    private final BigRational value;
    private int physConstraint;

    public Constraint(final ConstraintType type, final BigRational value, final int firstTerm) {
        this.type = type;
        this.firstTerm = firstTerm;
        this.value = value;
    }

    ConstraintType getType() {
        return type;
    }

    int getFirstTerm() {
        return firstTerm;
    }

    int getCountTerms() {
        return countTerms;
    }

    void incCountTerms() {
        countTerms++;
    }

    BigRational getValue() {
        return value;
    }

    int getPhysConstraint() {
        return physConstraint;
    }

    void setPhysConstraint(final int physConstraint) {
        this.physConstraint = physConstraint;
    }
}
