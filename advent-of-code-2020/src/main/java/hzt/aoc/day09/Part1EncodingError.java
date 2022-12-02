package hzt.aoc.day09;

import java.util.List;

public class Part1EncodingError extends Day09Challenge {

    public Part1EncodingError() {
        super("part 1",
                "The first step of attacking the weakness in the XMAS data is to find the first number in the list " +
                        "(after the preamble) which is not the sum of two of the 25 numbers before it." +
                        "What is the first number that does not have this property?");
    }


    @Override
    protected long solveByXmasList(final List<Long> longs) {
        return findFirstNumberNotSumOfTwoIntegersInPreamble(longs);
    }



    @Override
    protected String getMessage(final String number) {
        return String.format("The first number that is not the sum of two of the 25 numbers before it is: %s%n", number);
    }

}
