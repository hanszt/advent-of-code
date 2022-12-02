package hzt.aoc.day08;

import java.util.regex.Pattern;

class Instruction {

    private static final Pattern ONE_OR_MORE_WHITE_SPACES = Pattern.compile("\\s", Pattern.UNICODE_CHARACTER_CLASS);
    private static int next = 0;
    private final int nr;
    private final int argument;

    private boolean visited;
    private String descriptor;

    public Instruction(final String descriptor, final int argument) {
        this.nr = ++next;
        this.descriptor = descriptor;
        this.argument = argument;
    }

    static Instruction fromInput(final String string) {
        final String[] strings = ONE_OR_MORE_WHITE_SPACES.split(string);
        final String descriptor = strings[0];
        final String stringArgument = strings[1];
        final int argument = Integer.parseInt(stringArgument);
        return new Instruction(descriptor, argument);
    }

    public static void setNext(final int next) {
        Instruction.next = next;
    }

    public int getNr() {
        return nr;
    }

    public int getArgument() {
        return argument;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(final boolean visited) {
        this.visited = visited;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(final String descriptor) {
        this.descriptor = descriptor;
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "nr=" + nr +
                ", visited=" + visited +
                ", descriptor='" + descriptor + '\'' +
                ", argument=" + argument +
                '}';
    }
}
