package aoc.secret_entrance.lib.simplex;

import aoc.secret_entrance.lib.BigRational;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/// [In geometry, a simplex (plural: simplexes or simplices) is a generalization of the notion of a triangle or tetrahedron to arbitrary dimensions.](https://en.wikipedia.org/wiki/Simplex)
public class Simplex {
    private final List<Variable> variables = new ArrayList<>();
    private final List<Constraint> constraints = new ArrayList<>();
    private final List<ConstraintTerm> terms = new ArrayList<>();

    public int addVariable(final VariableType type, final BigRational goalFactor) {
        final var c = variables.size();
        variables.add(new Variable(type, goalFactor));
        if (type == VariableType.BOOLEAN) {
            addConstraint(ConstraintType.LESS_OR_EQUAL, BigRational.ONE);
        }
        return c;
    }

    public void addConstraint(final ConstraintType type, final BigRational value) {
        constraints.add(new Constraint(type, value, terms.size()));
    }

    public void addConstraintTerm(final BigRational factor, final int term) {
        terms.add(new ConstraintTerm(factor, term));
        constraints.get(constraints.size() - 1).incCountTerms();
    }

    private Solution solveInner() {
        var countPhysVars = 0;
        for (final var variable : variables) {
            variable.setPhysVar(countPhysVars);
            if (variable.getType().isNonNegative()) {
                countPhysVars++;
            } else {
                countPhysVars += 2;
            }
        }
        var countPhysConstraints = 0;
        for (final var constraint : constraints) {
            constraint.setPhysConstraint(countPhysConstraints);
            if (constraint.getType() == ConstraintType.EQUAL) {
                countPhysConstraints += 2;
            } else {
                countPhysConstraints++;
            }
        }

        final var width = countPhysVars + countPhysConstraints + 2;
        final var height = countPhysConstraints + 1;
        final var t = new Tableau(width, height);

        // Fill in b
        var aux = false;
        var rowIndex = 1;
        for (final var constraint : constraints) {
            if (constraint.getValue().isNegative() || (constraint.getType() == ConstraintType.EQUAL && !constraint.getValue().isZero())) {
                aux = true;
            }
            t.add(rowIndex++, width - 1, constraint.getValue());
            if (constraint.getType() == ConstraintType.EQUAL) {
                t.add(rowIndex++, width - 1, constraint.getValue().negate());
            }
        }

        // Fill in A
        rowIndex = 1;
        for (final var constraint : constraints) {
            for (var k = constraint.getFirstTerm(); k < constraint.getFirstTerm() + constraint.getCountTerms(); k++) {
                final var term = terms.get(k);
                final var v = variables.get(term.constraint());
                t.add(rowIndex, v.getPhysVar(), term.factor());
                if (!v.getType().isNonNegative()) {
                    t.add(rowIndex, v.getPhysVar() + 1, term.factor().negate());
                }
            }
            rowIndex++;

            if (constraint.getType() == ConstraintType.EQUAL) {
                for (var k = constraint.getFirstTerm(); k < constraint.getFirstTerm() + constraint.getCountTerms(); k++) {
                    final var term = terms.get(k);
                    final var v = variables.get(term.constraint());
                    t.add(rowIndex, v.getPhysVar(), term.factor().negate());
                    if (!v.getType().isNonNegative()) {
                        t.add(rowIndex, v.getPhysVar() + 1, term.factor());
                    }
                }
                rowIndex++;
            }
        }
        for (var i = 0; i < countPhysConstraints; i++) {
            t.add(i + 1, i + countPhysVars, BigRational.ONE);
        }

        // Determine initial base
        final var baseColumns = new int[width];
        Arrays.fill(baseColumns, -1);
        for (var i = 0; i < countPhysConstraints; i++) {
            baseColumns[countPhysVars + i] = i;
        }
        final var baseVectors = new int[countPhysConstraints];
        for (var i = 0; i < countPhysConstraints; i++) {
            baseVectors[i] = countPhysVars + i;
        }

        // Solve auxiliary problem, if needed
        if (aux) {
            t.minusOneColumn();

            // Solve aux tableau
            var first = true;
            while (true) {
                final int i;
                final int j;
                if (first) {
                    first = false;

                    // Column is the -1 column
                    i = width - 2;
                    j = t.findSmallestB();
                } else {
                    // Determine column to work on
                    i = t.findPivotColumn(true);
                    if (i == -1) {
                        break;
                    }

                    // Determine row to work on
                    j = t.findPivotRow(i);
                }

                // If now row coule be found, there is an unbounded direction
                if (j == -1) {
                    return null;
                }

                // Pivot
                t.pivot(j, i);

                // Update base
                baseColumns[baseVectors[j - 1]] = -1;
                baseVectors[j - 1] = i;
                baseColumns[i] = j - 1;
            }

            if (!t.getTopRow(width - 1).isZero()) {
                // No feasible base
                return null;
            }

            // Make sure that the -1 column is no longer in the base
            if (baseColumns[width - 2] != -1) {
                final var j = baseColumns[width - 2] + 1;
                int i;
                for (i = 0; i < width - 2; i++) {
                    if (baseColumns[i] != -1) {
                        continue;
                    }
                    if (!t.get(j, i).isZero()) {
                        break;
                    }
                }
                if (i == width - 2) {
                    // No feasible base
                    return null;
                }

                // Pivot
                t.pivot(j, i);

                // Update Base
                baseColumns[baseVectors[j - 1]] = -1;
                baseVectors[j - 1] = i;
                baseColumns[i] = j - 1;
            }
        }

        // Fill in -c
        rowIndex = 0;
        for (final var variable : variables) {
            t.set(0, rowIndex++, variable.getGoalFactor().negate());
            if (!variable.getType().isNonNegative()) {
                t.set(0, rowIndex++, variable.getGoalFactor());
            }
        }
        while (rowIndex < width) {
            t.set(0, rowIndex++, BigRational.ZERO);
        }

        if (aux) {
            for (var i = 0; i < width - 2; i++) {
                if (baseColumns[i] != -1) {
                    final var d = t.getTopRow(i);
                    if (!d.isZero()) {
                        for (var k = 0; k < width; k++) {
                            final var e = t.get(baseColumns[i] + 1, k);
                            if (!e.isZero()) {
                                t.add(0, k, d.multiply(e).negate());
                            }
                        }
                    }
                }
            }

            t.emptyMinusOneColumn();
        }

        // Solve
        while (true) {
            // Determine column to work on
            final var i = t.findPivotColumn(false);
            if (i == -1) {
                break;
            }

            // Determine row to work on
            final var j = t.findPivotRow(i);

            // If now row could be found, there is an unbounded direction
            if (j == -1) {
                return null;
            }

            // Change this row into a unit vector
            t.pivot(j, i);

            // Update Base
            baseColumns[baseVectors[j - 1]] = -1;
            baseVectors[j - 1] = i;
            baseColumns[i] = j - 1;
        }

        // Determine solution
        final var physsol = new BigRational[countPhysVars + countPhysConstraints];
        Arrays.fill(physsol, BigRational.ZERO);
        for (var i = 0; i < countPhysConstraints; i++) {
            final var v = t.get(i + 1, width - 1);
            if (v.isNegative()) {
                // Not feasible, row base_vectors[i], value v
                return null;
            }
            physsol[baseVectors[i]] = v;
        }
        final var sol = new BigRational[variables.size()];
        rowIndex = 0;
        for (var i = 0; i < variables.size(); i++) {
            final var variable = variables.get(i);
            if (variable.getType().isNonNegative()) {
                sol[i] = physsol[rowIndex++];
            } else {
                sol[i] = physsol[rowIndex].subtract(physsol[rowIndex + 1]);
                rowIndex += 2;
            }
        }
        final var value = t.getTopRowOrDefault(width - 1);
        return new Solution(value, List.of(sol));
    }

    public Solution solve() {
        return solveIntegerBranchAndBound(null);
    }

    private Solution solveIntegerBranchAndBound(Solution solution) {
        // Determine the non-integral solution
        final var nonIntegralSolution = solveInner();

        // Return if there is no solution or it has a lower value than the one we already have
        if (nonIntegralSolution == null ||
                (solution != null && nonIntegralSolution.value().compareTo(solution.value()) <= 0)) {
            return solution;
        }

        var i = 0;
        while (i < variables.size()) {
            if (variables.get(i).getType().isInteger() && !nonIntegralSolution.vars().get(i).isIntegral()) {
                break;
            }
            i++;
        }

        if (i == variables.size()) {
            return nonIntegralSolution;
        }

        // Branch and bound
        addConstraint(ConstraintType.LESS_OR_EQUAL, nonIntegralSolution.vars().get(i).floor());
        addConstraintTerm(BigRational.ONE, i);
        solution = solveIntegerBranchAndBound(solution);
        constraints.remove(constraints.size() - 1);
        terms.remove(terms.size() - 1);

        addConstraint(ConstraintType.LESS_OR_EQUAL, nonIntegralSolution.vars().get(i).ceil().negate());
        addConstraintTerm(BigRational.MINUS_ONE, i);
        solution = solveIntegerBranchAndBound(solution);
        constraints.remove(constraints.size() - 1);
        terms.remove(terms.size() - 1);

        return solution;
    }
}
