package hzt.aoc.day22;

import hzt.aoc.Challenge;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public abstract class Day22Challenge extends Challenge {

    Day22Challenge(final String challengeTitle, final String description) {
        super(challengeTitle, description, "20201222-input-day22.txt");
    }

    @Override
    protected String solve(final List<String> inputList) {
        final Deque<Integer> player1Cards = new ArrayDeque<>();
        final Deque<Integer> player2Cards = new ArrayDeque<>();
        fillDecksByInputList(inputList, player1Cards, player2Cards);
        return getMessage(play(player1Cards, player2Cards));
    }

    private static void fillDecksByInputList(final List<String> inputList,
                                             final Deque<Integer> player1Cards,
                                             final Deque<Integer> player2Cards) {
        int player = 0;
        for (final String line : inputList) {
            if (NUMBER_LENGTH_ONE_OR_MORE.matcher(line).matches()) {
                if (player == 1) {
                    player1Cards.addLast(Integer.parseInt(line));
                }
                if (player == 2) {
                    player2Cards.addLast(Integer.parseInt(line));
                }
            }
            if ("Player 1:".equals(line)) {
                player++;
            }
            if ("Player 2:".equals(line)) {
                player++;
            }
        }
    }

    long calculateScoreWinningPlayer(final Deque<Integer> winningPlayerCards) {
        long score = 0;
        long counter = 1;
        while (!winningPlayerCards.isEmpty()) {
            score += counter * winningPlayerCards.pollLast();
            counter++;
        }
        return score;
    }

    protected abstract long play(Deque<Integer> player1Cards, Deque<Integer> player2Cards);


    abstract String getMessage(long value);
}
