package hzt.aoc.day23;

import org.hzt.utils.collections.primitives.IntList;

import java.util.HashMap;
import java.util.Map;

// Credits to TurkeyDev
public class Part2CrabCups extends Day23Challenge {

    private static final int NR_OF_MOVES = 10_000_000;
    private static final int CUP_AMOUNT = 1_000_000;
    private static final int TARGET_VAL = 1;

    public Part2CrabCups() {
        super("part 2",
                "You are quite surprised when the crab starts arranging many cups in a circle on your raft" +
                        " - one million (1000000) in total. " +
                        "The crab is going to do ten million (10000000) moves! " +
                        "Determine which two cups will end up immediately clockwise of cup 1. " +
                        "What do you get if you multiply their labels together?");
    }

    @Override
    protected long calculateAnswer(final IntList cupLabels) {
        final Map<Integer, LinkedNode> labelToNodeMap = new HashMap<>();
        final var first = firstNode(cupLabels);
        var current = first;
        var target = new LinkedNode(0);
        LinkedNode next;
        for (var i = 0; i < cupLabels.size(); i++) {
            final var label = cupLabels.get(i);
            if (i != 0) {
                next = new LinkedNode(label);
                current.setNext(next);
                current = next;
            }
            if (label == TARGET_VAL) {
                target = current;
            }
            labelToNodeMap.put(label, current);
        }
        current = fillRestOfTheMap(labelToNodeMap, current);
        current.setNext(first);
        return run(first, target, labelToNodeMap);
    }

    private static LinkedNode firstNode(final IntList cupLabels) {
        final var label = cupLabels.get(0);
        return new LinkedNode(label);
    }

    private static LinkedNode fillRestOfTheMap(
            final Map<Integer, LinkedNode> labelToNodeMap,
            final LinkedNode initial
    ) {
        var current = initial;
        for (var label = labelToNodeMap.size() + 1; label <= CUP_AMOUNT; label++) {
            final var next = new LinkedNode(label);
            labelToNodeMap.put(label, next);
            current.setNext(next);
            current = next;
        }
        return current;
    }

    public static long run(LinkedNode current, final LinkedNode target,
                    final Map<Integer, LinkedNode> indexToLabelNodeMap) {
        for (var i = 0; i < NR_OF_MOVES; i++) {
            final var moved = current.getNext();
            current.setNext(current.getNext().getNext().getNext().getNext());
            final var destinationCupLabel = determineTargetCupLabel(current, moved, indexToLabelNodeMap);

            final var nodeToBeInserted = indexToLabelNodeMap.get(destinationCupLabel);
            moved.getNext().getNext().setNext(nodeToBeInserted.getNext());
            nodeToBeInserted.setNext(moved);

            current = current.getNext();
        }
        final long num1 = target.getNext().getValue();
        final long num2 = target.getNext().getNext().getValue();
        return num1 * num2;
    }

    private static int determineTargetCupLabel(final LinkedNode current,
                                        final LinkedNode removed,
                                        final Map<Integer, LinkedNode> indexToLabelNodeMap) {
        var destinationCupLabel = current.getValue() == TARGET_VAL ? indexToLabelNodeMap.size() : (current.getValue() - 1);
        while (inRange(removed, destinationCupLabel)) {
            destinationCupLabel--;
            if (destinationCupLabel == 0) {
                destinationCupLabel = indexToLabelNodeMap.size();
            }
        }
        return destinationCupLabel;
    }

    private static boolean inRange(final LinkedNode removed, final int destination) {
        final var current = removed.getValue();
        final var next = removed.getNext().getValue();
        final var secondNext = removed.getNext().getNext().getValue();
        return current == destination || next == destination || secondNext == destination;
    }

    @Override
    String getMessage(final long global) {
        return String.format("%d", global);
    }
}
