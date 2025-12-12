package hzt.aoc.day25;

import java.util.stream.LongStream;

public class Part1ComboBreaker extends Day25Challenge {

    private static final long STARTING_VALUE = 1;

    public Part1ComboBreaker() {
        super("part 1",
                "What encryption key is the handshake trying to establish?");
    }

    @Override
    protected long solveByInput(final long cardPublicKey, final long doorPublicKey) {
        final var loopSizeCard = findLoopSize(cardPublicKey);
        final var loopSizeDoor = findLoopSize(doorPublicKey);
        final var encryptionKeyDoor = calculateEncryptionKey(doorPublicKey, loopSizeCard);
        final var encryptionKeyCard = calculateEncryptionKey(cardPublicKey, loopSizeDoor);
        LOGGER.trace(() -> "Card loop size: " + loopSizeCard);
        LOGGER.trace(() -> "Door loop size: " + loopSizeDoor);
        LOGGER.trace(() -> "Card encryption key: " + encryptionKeyCard);
        LOGGER.trace(() -> "Door encryption key: " + encryptionKeyDoor);
        return encryptionKeyCard == encryptionKeyDoor ? encryptionKeyCard : 0;
    }

    private static long calculateEncryptionKey(final long publicKey, final long loopSizeOther) {
        return LongStream.range(0, loopSizeOther).reduce(STARTING_VALUE,  (acc, n) -> performStep(acc, publicKey));
    }

    private static long performStep(final long value, final long subjectNumber) {
        return (value * subjectNumber) % NUMBER_TO_DIVIDE_BY;
    }

    private static long findLoopSize(final long publicKey) {
        var newVal = STARTING_VALUE;
        var counter = 0;
        do {
            newVal = performStep(newVal, INIT_SUBJECT_NUMBER);
            counter++;
        } while (newVal != publicKey);
        return counter;
    }

    @Override
    String getMessage(final long global) {
        return String.format("%d", global);
    }

}
