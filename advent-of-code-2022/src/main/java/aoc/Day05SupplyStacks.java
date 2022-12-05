package aoc;

import aoc.utils.FileUtils;
import org.hzt.utils.sequences.Sequence;
import org.hzt.utils.strings.StringX;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day05SupplyStacks implements ChallengeDay {

    private final List<Deque<Character>> stacks;
    private final List<String> instructions;

    public Day05SupplyStacks(String fileName) {
        final var s = FileUtils.useLines(Path.of(fileName), Stream::toList);
        final var stackLines = new ArrayList<String>();
        final var iterator = s.iterator();
        while (iterator.hasNext()) {
            final var next = iterator.next();
            if (next.isBlank()) {
                break;
            }
            stackLines.add(next);
        }
        this.stacks = toStacks(stackLines);
        this.instructions = Sequence.of(() -> iterator).toList();
    }

    private static List<Deque<Character>> toStacks(List<String> input) {
        List<Deque<Character>> stacks = new ArrayList<>();
        for (int i = 0; i < input.size() - 1; i++) {
            fillStacks(stacks, input.get(i));
        }
        return stacks;
    }

    private static void fillStacks(List<Deque<Character>> stacks, String line) {
        final var chars = line.toCharArray();
        for (int i = 0; i < line.length(); i++) {
            if (i % 4 == 1) {
                final var aChar = chars[i];
                final var index = (i + 1) / 4;
                while (index > stacks.size() - 1) {
                    stacks.add(new ArrayDeque<>());
                }
                final var stack = stacks.get(index);
                if (Character.isLetter(aChar)) {
                    stack.addLast(aChar);
                }
            }
        }
    }

    @NotNull
    @Override
    public String part1() {
        for (var instr : instructions) {
            final var instruction = toInstruction(instr);
            for (int i = 0; i < instruction.amount; i++) {
                final var first = stacks.get(instruction.from - 1).removeFirst();
                stacks.get(instruction.to - 1).addFirst(first);
            }
        }
        return toTopCratesAsString();
    }

    private String toTopCratesAsString() {
        return stacks.stream()
                .map(Deque::peek)
                .filter(Objects::nonNull)
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    @NotNull
    @Override
    public Object part2() {
        for (var instr : instructions) {
            final var instruction = toInstruction(instr);
            final var characters = new ArrayDeque<Character>();
            for (int i = 0; i < instruction.amount; i++) {
                characters.addLast(stacks.get(instruction.from - 1).removeFirst());
            }
            for (int i = 0; i < instruction.amount; i++) {
                stacks.get(instruction.to - 1).addFirst(characters.removeLast());
            }
        }
        return toTopCratesAsString();
    }

    private static Instruction toInstruction(String instr) {
        final var split = StringX.of(instr).split(" from ", " to ");
        final var amount = Integer.parseInt(split.get(0).replace("move ", ""));
        final var from = Integer.parseInt(split.get(1));
        final var to = Integer.parseInt(split.get(2));
        return new Instruction(amount, from, to);
    }

    record Instruction(int amount, int from, int to) {
    }
}
