package hzt.aoc.day23;

public class LinkedNode {

    private int value;
    private LinkedNode next;

    public LinkedNode(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(final int value) {
        this.value = value;
    }

    public LinkedNode getNext() {
        return next;
    }

    public void setNext(final LinkedNode next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "LinkedNode{" + getValue() + '}';
    }
}
