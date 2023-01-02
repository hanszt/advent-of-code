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
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

/**
 * @see <a href="https://adventofcode.com/2022/day/5">Day 5: Supply stacks</a>
 */
public class Day05SupplyStacks implements ChallengeDay {

    private final List<String> stackLines;
    private final List<String> instructions;

    public Day05SupplyStacks(String fileName) {
        final var iterator = FileUtils.useLines(Path.of(fileName), Stream::toList).iterator();
        this.stackLines = Sequence.of(() -> iterator).takeWhile(not(String::isBlank)).toList();
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
        final var stacks = toStacks(stackLines);
        for (var instr : instructions) {
            final var instruction = toInstruction(instr);
            for (int i = 0; i < instruction.amount; i++) {
                final var first = stacks.get(instruction.from - 1).removeFirst();
                stacks.get(instruction.to - 1).addFirst(first);
            }
        }
        return toTopCratesAsString(stacks);
    }

    private static String toTopCratesAsString(List<Deque<Character>> stacks) {
        return Sequence.of(stacks)
                .mapNotNull(Deque::peek)
                .joinToString("");
    }

    @NotNull
    @Override
    public String part2() {
        final var stacks = toStacks(stackLines);
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
        return toTopCratesAsString(stacks);
    }

    private static Instruction toInstruction(String instr) {
        final var split = StringX.of(instr).split(" from ", " to ");
        final var amount = Integer.parseInt(split.get(0).replace("move ", ""));
        final var from = Integer.parseInt(split.get(1));
        final var to = Integer.parseInt(split.get(2));
        return new Instruction(amount, from, to);
    }

    private record Instruction(int amount, int from, int to) {
    }
}
