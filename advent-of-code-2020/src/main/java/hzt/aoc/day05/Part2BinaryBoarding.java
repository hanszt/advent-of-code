package hzt.aoc.day05;

import hzt.aoc.day05.model.Seat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Part2BinaryBoarding extends Day05Challenge {

    public Part2BinaryBoarding() {

        super("part 2", "What is your seat Id? See ChallengeDay5.md part 2 for the assignment. ");
    }

    @Override
    protected int calculateResult(final List<Seat> seats) {
        // flight completely full
        final Set<Integer> possibleSeatIds = new HashSet<>();
        final List<Integer> seatIds = seats.stream()
                .map(seat -> seat.getSeatID(NUMBER_OF_COLUMNS))
                .collect(Collectors.toList());

        // Every seat id must be unique
        for (final Integer seatId : seatIds) {
            // Seats with id one less and one more than myId are in my list
            if (seatIds.contains(seatId - 2)) {
                final int possibleSeatId = seatId + 1;
                // My boarding pass is the only missing boarding pass in the list
                if (!seatIds.contains(possibleSeatId)) {
                    possibleSeatIds.add(possibleSeatId);
                }
            }
        }
        final int highestSeatIdOnBoardingPass = findHighestSeatID(seatIds);
        int mySeatId = 0;
        for (final int seatId : possibleSeatIds) {
            // some seats at the very front and back of the plane don't exist on this aircraft, they'll be missing from your list.
            // My seat isn't in the very front or back of the plane
            if (seatId < highestSeatIdOnBoardingPass) {
                mySeatId = seatId;
            }
        }
        return mySeatId;
    }

    @Override
    protected String getMessage(final String mySeatId) {
        return String.format("The id of my seat is: %s%n", mySeatId);
    }
}
