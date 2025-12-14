package aoc.secret_entrance.lib.simplex;

import aoc.secret_entrance.lib.BigRational;

public class Variable {
    private final VariableType type;
    private int physVar;
    private final BigRational goalFactor;

    Variable(final VariableType type, final BigRational goalFactor) {
        this.type = type;
        this.goalFactor = goalFactor;
    }

    VariableType getType() {
        return type;
    }

    int getPhysVar() {
        return physVar;
    }

    void setPhysVar(final int physVar) {
        this.physVar = physVar;
    }

    BigRational getGoalFactor() {
        return goalFactor;
    }
}
