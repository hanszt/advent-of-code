package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import aoc.utils.invoke
import aoc.utils.lcm
import kotlin.collections.all
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * https://github.com/zebalu/advent-of-code-2023/blob/master/aoc2023/src/main/java/io/github/zebalu/aoc2023/days/Day20.java
 */
class Day20(private val modules: List<String>) : ChallengeDay {

    constructor(fileName: String) : this(Path(fileName).readLines())

    /**
     * What do you get if you multiply the total number of low pulses sent by the total number of high pulses sent?
     */
    override fun part1(): Long {
        val machine = Machine.build(modules.map(Node::parse))
        repeat(1_000) { machine.push() }
        return machine.count()
    }

    /**
     * What is the fewest number of button presses required to deliver a single low pulse to the module named rx?
     */
    override fun part2(): Long {
        val machine = Machine.build(modules.map(Node::parse))
        val broadcaster = machine.setup(Node.Companion.BROADCASTER)
        val originalOutputs = broadcaster.outputs
        val toRxSink = machine.setup.values.first { "rx" in it.outputs }
        var result = 1L
        for (s in originalOutputs) {
                toRxSink.inputs.keys.forEach { toRxSink.inputs.put(it, Pulse.HIGH) }
                broadcaster.outputs = mutableListOf(s)
                var counter = 1L
                while (!machine.push()) {
                    ++counter
                }
                broadcaster.outputs = originalOutputs
                machine.reset()
                result = result.lcm(counter)
            }
        return result
    }

    private enum class Pulse {
        LOW, HIGH
    }

    @JvmRecord
    private data class PulseCounter(val lowCounter: Counter, val highCounter: Counter) {
        fun count(pulse: Pulse) {
            when (pulse) {
                Pulse.LOW -> lowCounter.value++
                Pulse.HIGH -> highCounter.value++
            }
        }
    }

    class Counter(var value: Long = 0)

    private class Node(
        val name: String,
        val type: Char,
        var outputs: MutableList<String> = ArrayList<String>(),
        val inputs: MutableMap<String, Pulse> = HashMap<String, Pulse>(),
        var onOff: Boolean = false,
    ) {

        fun markInput(input: String) {
            if (type == '&') {
                inputs.put(input, Pulse.LOW)
            }
        }

        fun receive(from: String, pulse: Pulse): List<Message> = when (type) {
            '%' -> {
                when (pulse) {
                    Pulse.LOW -> {
                        onOff = !onOff
                        forward(if (onOff) Pulse.HIGH else Pulse.LOW)
                    }

                    Pulse.HIGH -> emptyList()
                }
            }

            '&' -> {
                inputs.put(from, pulse)
                val allHigh = inputs.values.all { it == Pulse.HIGH }
                forward(if (allHigh) Pulse.LOW else Pulse.HIGH)
            }

            '?' -> forward(pulse)
            else -> error("unknown node type: $type")
        }

        fun forward(pulse: Pulse): List<Message> = outputs.map { Message(name, target = it, pulse) }

        fun reset() {
            onOff = false
            inputs.keys.forEach { inputs.put(it, Pulse.LOW) }
        }

        companion object {
            const val BROADCASTER: String = "broadcaster"
            fun parse(line: String): Node {
                val parts = line.split(" -> ")
                val name: String
                val type: Char
                if (parts[0] == BROADCASTER) {
                    type = '?'
                    name = BROADCASTER
                } else {
                    type = parts[0][0]
                    name = parts[0].substring(1)
                }
                val result = Node(name, type)
                for (dest in parts[1].split(", ")) {
                    result.outputs.add(dest)
                }
                return result
            }
        }
    }

    @JvmRecord
    private data class Machine(val setup: Map<String, Node>, val counter: PulseCounter) {
        fun push(): Boolean {
            val messageQueue = ArrayDeque<Message>()
            counter.lowCounter.value++
            messageQueue.add(Message("button", Node.BROADCASTER, Pulse.LOW))

            while (messageQueue.isNotEmpty()) {
                val curr = messageQueue.removeFirst()
                if (curr.target == STOP_NODE && curr.pulse == Pulse.LOW) {
                    return true
                }
                setup[curr.target]?.let { node ->
                    node.receive(curr.from, curr.pulse).forEach { msg ->
                        counter.count(msg.pulse)
                        messageQueue.add(msg)
                    }
                }
            }
            return false
        }

        fun count(): Long = counter.lowCounter.value * counter.highCounter.value

        fun reset() {
            counter.lowCounter.value = 0
            counter.highCounter.value = 0
            setup.values.forEach(Node::reset)
        }

        companion object {
            private const val STOP_NODE = "rx"
            fun build(nodes: List<Node>): Machine {
                val mapped = nodes.associateBy { it.name }
                mapped.values.forEach { node ->
                    node.outputs.forEach { o -> mapped[o]?.markInput(node.name) }
                }
                return Machine(
                    setup = mapped,
                    counter = PulseCounter(
                        lowCounter = Counter(0L),
                        highCounter = Counter(0L)
                    )
                )
            }
        }
    }

    @JvmRecord
    private data class Message(val from: String, val target: String, val pulse: Pulse)
}
