package hzt.aoc.day02.model;

public class Policy {

    private final int lowerBound;
    private final int upperBound;
    private final char character;

    public Policy(final int lowerBound, final int upperBound, final char character) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.character = character;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public char getCharacter() {
        return character;
    }

    @Override
    public String toString() {
        return "Policy{" +
                "lowerBound=" + lowerBound +
                ", upperBound=" + upperBound +
                ", character=" + character +
                '}';
    }

}
