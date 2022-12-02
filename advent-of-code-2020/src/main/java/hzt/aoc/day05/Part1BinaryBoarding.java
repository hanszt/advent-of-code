package hzt.aoc.day05;

import hzt.aoc.day05.model.Seat;

import java.util.List;
import java.util.stream.Collectors;

public class Part1BinaryBoarding extends Day05Challenge {

    public Part1BinaryBoarding() {
        super("part 1", "Find the highest seat ID on a boarding pass in the list. ");
    }

    @Override
    protected int calculateResult(final List<Seat> seats) {
        return findHighestSeatID(seats.stream().map(seat -> seat.getSeatID(NUMBER_OF_COLUMNS)).collect(Collectors.toList()));
    }

    @Override
    protected String getMessage(final String result) {
        return String.format("The highest seat ID is: %s%n", result);
    }

}
