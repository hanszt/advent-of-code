package aoc.secret_entrance.lib.simplex;


import aoc.secret_entrance.lib.BigRational;

import java.util.List;

public record Solution(BigRational value, List<BigRational> vars) {
    public Solution {
        vars = List.copyOf(vars);
    }
}
