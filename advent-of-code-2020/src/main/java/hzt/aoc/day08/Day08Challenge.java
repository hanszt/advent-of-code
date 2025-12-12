package hzt.aoc.day08;

import hzt.aoc.Challenge;

import java.util.List;

public abstract class Day08Challenge extends Challenge {

    static final String ACCUMULATOR = "acc";
    static final String JUMP = "jmp";
    static final String NO_OPERATION = "nop";

    protected Day08Challenge(final String challengeTitle, final String description) {
        super(challengeTitle, description, "20201208-input-day8.txt");
    }

    @Override
    protected String solve(final List<String> inputList) {
        Instruction.setNext(0);
        final var instructions = inputList.stream()
                .map(Instruction::fromInput)
                .toList();

        final var global = solveByInstructions(instructions);
        return String.valueOf(global);
    }

    abstract int solveByInstructions(List<Instruction> instructions);

    Result testInstructions(final List<Instruction> instructions) {
        var position = 0;
        var global = 0;
        Instruction lastInstruction = null;
        while (position < instructions.size() && !instructions.get(position).isVisited()) {
            final var instruction = instructions.get(position);
            switch (instruction.getDescriptor()) {
                case JUMP:
                    position += instruction.getArgument();
                    break;
                case ACCUMULATOR:
                    global += instruction.getArgument();
                    position++;
                    break;
                case NO_OPERATION:
                    position++;
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
            instruction.setVisited(true);
            lastInstruction = instruction;
        }
        return new Result(lastInstruction, global);
    }

    record Result(Instruction lastInstruction, int global) {
    }

}
