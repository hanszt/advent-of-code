package hzt.aoc.day23;

public class LinkedNode<T> {

    private T value;
    private LinkedNode<T> next;

    public LinkedNode() {
        this(null);
    }

    public LinkedNode(final T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(final T value) {
        this.value = value;
    }

    public LinkedNode<T> getNext() {
        return next;
    }

    public void setNext(final LinkedNode<T> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "CustomLinkedNode{" + getValue() + '}';
    }
}
