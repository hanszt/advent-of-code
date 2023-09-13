package hzt.aoc.day22;

import java.util.SequencedCollection;

public class Part1CrabCombat extends Day22Challenge {

    public Part1CrabCombat() {
        super("part 1",
                "Play the small crab in a game of Combat using the two decks you just dealt. " +
                        "What is the winning player's score?");
    }


    @Override
    protected long play(final SequencedCollection<Integer> player1Cards, final SequencedCollection<Integer> player2Cards) {
        while (!player1Cards.isEmpty() && !player2Cards.isEmpty()) {
            playRound(player1Cards, player2Cards);
        }
        final var winningPlayerCards = player1Cards.isEmpty() ? player2Cards : player1Cards;
        return calculateScoreWinningPlayer(winningPlayerCards);
    }



    private static void playRound(final SequencedCollection<Integer> player1Cards, final SequencedCollection<Integer> player2Cards) {
        final int player1TopCard = player1Cards.removeFirst();
        final int player2TopCard = player2Cards.removeFirst();
        if (player1TopCard > player2TopCard) {
            player1Cards.addLast(player1TopCard);
            player1Cards.addLast(player2TopCard);
        } else {
            player2Cards.addLast(player2TopCard);
            player2Cards.addLast(player1TopCard);
        }
    }

    @Override
    String getMessage(final long global) {
        return String.format("%d", global);
    }

}
