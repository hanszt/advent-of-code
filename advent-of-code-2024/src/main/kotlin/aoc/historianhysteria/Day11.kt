package aoc.historianhysteria

import aoc.utils.ChallengeDay

class Day11(input: String) : ChallengeDay {

    private val stones = input.split(' ').map { it.toLong() }

    /**
     * How many stones will you have after blinking 25 times?
     */
    override fun part1(): Int = stoneCount()

    fun stoneCount(blinkAmount: Int = 25): Int {
        var stones = stones
        repeat(blinkAmount) {
            val size = stones.size
            stones = buildList(size) {
                for (stone in stones) {
                    when {
                        stone == 0L -> add(1L)
                        stone.toString().length % 2 == 0 -> {
                            val string = stone.toString()
                            val length = string.length
                            val half = length / 2
                            add(string.substring(0, half).toLong())
                            add(string.substring(half).toLong())
                        }
                        else -> add(stone * 2024)
                    }
                }
            }
        }
        return stones.size
    }

    /**
     * How many stones would you have after blinking a total of 75 times?
     */
    override fun part2(): Long = day11part2Elizarov(stones)
}
