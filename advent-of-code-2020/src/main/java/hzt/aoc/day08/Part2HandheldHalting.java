package hzt.aoc.day08;

import java.util.List;

public class Part2HandheldHalting extends Day08Challenge {

    public Part2HandheldHalting() {
        super("part 2",
                "Fix the program so that it terminates normally by changing exactly one jmp (to nop) or nop (to jmp). " +
                        "What is the value of the accumulator after the program terminates?");
    }

    @Override
    int solveByInstructions(final List<Instruction> instructions) {
        for (final var instruction : instructions) {
            instructions.forEach(item -> item.setVisited(false));
            swapJumpAndNoOperation(instruction);
            final var result = testInstructions(instructions);
            if (result.lastInstruction().getNr() == instructions.size()) {
                return result.global();
            }
            swapJumpAndNoOperation(instruction);
        }
        return 0;
    }

    private void swapJumpAndNoOperation(final Instruction instruction) {
        if (instruction.getDescriptor().equals(NO_OPERATION)) {
            instruction.setDescriptor(JUMP);
        } else if (instruction.getDescriptor().equals(JUMP)) {
            instruction.setDescriptor(NO_OPERATION);
        }
    }

    @Override
    protected String getMessage(final String global) {
        return String.format("The value of the global variable after correct termination: %s%n", global);
    }
}
