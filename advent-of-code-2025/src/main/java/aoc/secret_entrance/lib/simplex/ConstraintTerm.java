package aoc.secret_entrance.lib.simplex;


import aoc.secret_entrance.lib.BigRational;

public record ConstraintTerm(BigRational factor, int constraint) {
}
