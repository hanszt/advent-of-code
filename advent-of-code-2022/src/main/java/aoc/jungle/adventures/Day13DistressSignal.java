package aoc.jungle.adventures;

import aoc.utils.ChallengeDay;
import aoc.utils.FileUtils;
import org.hzt.graph.TreeNode;
import org.hzt.utils.sequences.Sequence;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Credits to TheTurkeyDev
 *
 * @see <a href="https://adventofcode.com/2022/day/13">Day 13: Distress signal</a>
 */
final class Day13DistressSignal implements ChallengeDay {

    private final List<String> input;

    Day13DistressSignal(String fileName) {
        this.input = FileUtils.useLines(Path.of(fileName), Stream::toList);
    }

    @NotNull
    @Override
    public Integer part1() {
        var sum = 0;
        for (var i = 0; i < input.size(); i += 3) {
            var list1 = CompList.parse(input.get(i));
            var list2 = CompList.parse(input.get(i + 1));
            if (list1.compareTo(list2) > 0) {
                final var index = (i / 3) + 1;
                sum += index;
            }
        }
        return sum;
    }

    @NotNull
    @Override
    public Integer part2() {
        final var targets = Sequence.of("[[2]]", "[[6]]").map(CompList::parse);
        return Sequence.of(input)
                .filterIndexed((i, _) -> i % 3 != 2)
                .map(CompList::parse)
                .plus(targets)
                .sortedDescending()
                .withIndex()
                .filter(indexedValue -> targets.any(indexedValue.value()::equals))
                .mapToInt(indexedValue -> indexedValue.index() + 1)
                .reduce(1, (product, next) -> product * next);
    }

    private static final class Parser {
        private final String s;
        private int index = 0;

        private Parser(String s) {
            this.s = s;
        }

        char nextChar() {
            return s.charAt(index);
        }
    }

    sealed interface CompNode extends Comparable<CompNode>, TreeNode<CompNode, CompNode> permits CompNumber, CompList {

        @Override
        default int compareTo(@NotNull CompNode o) {
            return switch (this) {
                case CompNumber(int v1) when o instanceof CompNumber(int v2) -> v2 - v1;
                case CompList l when o instanceof CompList l2 -> compareLists(l, l2);
                case CompNumber n -> new CompList(n).compareTo(o);
                case CompList l -> l.compareTo(new CompList(o));
            };
        }

        private static int compareLists(CompList l1, CompList l2) {
            for (var i = 0; i < l1.nodes.size(); i++) {
                if (l2.nodes.size() == i) {
                    return -1;
                }
                var val = l1.nodes.get(i).compareTo(l2.nodes.get(i));
                if (val != 0) {
                    return val;
                }
            }
            return l1.nodes.size() < l2.nodes.size() ? 1 : 0;
        }
    }

    record CompList(List<CompNode> nodes) implements CompNode {

        CompList(CompNode... nodes) {
            this(new ArrayList<>(List.of(nodes)));
        }

        static CompList parse(String s) {
            return parse(new Parser(s));
        }

        private static CompList parse(Parser parser) {
            var list = new CompList();
            parser.index++;
            var c = parser.nextChar();
            while (c != ']') {
                if (c == ',') {
                    parser.index++;
                } else if (c == '[') {
                    list.nodes.add(CompList.parse(parser));
                } else {
                    list.nodes.add(CompNumber.parse(parser));
                }
                c = parser.nextChar();
            }
            parser.index++;
            return list;
        }

        @Override
        public Iterator<CompNode> childrenIterator() {
            return nodes.iterator();
        }
    }

    record CompNumber(int value) implements CompNode {

        private static CompNumber parse(Parser parser) {
            final var commaIndex = parser.s.indexOf(',', parser.index);
            final var sqrBracketIndex = parser.s.indexOf(']', parser.index);
            final var numStr = parser.s.substring(parser.index, Math.min(
                    commaIndex < 0 ? parser.s.length() : commaIndex,
                    sqrBracketIndex < 0 ? parser.s.length() : sqrBracketIndex));
            parser.index += numStr.length();
            return new CompNumber(Integer.parseInt(numStr));
        }

        @Override
        public Iterator<CompNode> childrenIterator() {
            return Collections.emptyIterator();
        }
    }
}
