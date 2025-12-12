package hzt.aoc.day22;

import java.util.*;

// Credits to Johan de Jong
public class Part2CrabCombat extends Day22Challenge {

    public Part2CrabCombat() {
        super("part 2",
                "Defend your honor as Raft Captain by playing the small crab in a game of " +
                        "Recursive Combat using the same two decks as before. " +
                        "What is the winning player's score?");
    }

    @Override
    protected long play(final SequencedCollection<Integer> player1Cards, final SequencedCollection<Integer> player2Cards) {
        final var player1Wins = playRecursiveGame(player1Cards, player2Cards);
        return calculateScoreWinningPlayer(player1Wins ? player1Cards : player2Cards);
    }

    private static boolean playRecursiveGame(final SequencedCollection<Integer> player1Cards, final SequencedCollection<Integer> player2Cards) {
        final Set<String> configsPlayer1 = new HashSet<>();
        final Set<String> configsPlayer2 = new HashSet<>();

        while (!player1Cards.isEmpty() && !player2Cards.isEmpty()) {
            final var curConfigPlayer1 = configurationAsString(player1Cards);
            final var curConfigPlayer2 = configurationAsString(player2Cards);
            if (configsPlayer1.contains(curConfigPlayer1) || configsPlayer2.contains(curConfigPlayer2)) {
                //player 1 wins
                return true;
            }
            configsPlayer1.add(curConfigPlayer1);
            configsPlayer2.add(curConfigPlayer2);
            final int player1TopCard = player1Cards.removeFirst();
            final int player2TopCard = player2Cards.removeFirst();

            final boolean player1Wins;
            if (player1Cards.size() >= player1TopCard && player2Cards.size() >= player2TopCard) {
                final Deque<Integer> newDequePlayer1 = new ArrayDeque<>(List.copyOf(player1Cards).subList(0, player1TopCard));
                final Deque<Integer> newDequePlayer2 = new ArrayDeque<>(List.copyOf(player2Cards).subList(0, player2TopCard));
                player1Wins = playRecursiveGame(newDequePlayer1, newDequePlayer2);
            } else {
                player1Wins = player1TopCard > player2TopCard;
            }
            if (player1Wins) {
                player1Cards.addLast(player1TopCard);
                player1Cards.addLast(player2TopCard);
            } else {
                player2Cards.addLast(player2TopCard);
                player2Cards.addLast(player1TopCard);
            }
        }
        return player2Cards.isEmpty();
    }

    private static String configurationAsString(final SequencedCollection<Integer> playerCards) {
        final var sb = new StringBuilder();
        playerCards.forEach(sb::append);
        return sb.toString();
    }

    @Override
    String getMessage(final long global) {
        return String.format("%d", global);
    }

}
