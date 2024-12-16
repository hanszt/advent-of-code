package aoc.seastories

import java.io.File
import java.math.BigInteger as BigInt

internal class Day06LanternFish(private val inputPath: String) : ChallengeDay {

    private companion object {
        private const val INIT_TIMER_VAL_NEW_BORN = 8
        private const val TIMER_VAL_AFTER_SPAWN = 6
    }

    override fun part1(): BigInt = File(inputPath).toFishCount(days = 80)

    override fun part2(): BigInt = File(inputPath).toFishCount(days = 256)

    private fun File.toFishCount(days: Int): BigInt {
        val daysLeftTillNewSpawnCounts = toCountsArray()
        repeat (days) {
            val curDaysLeftTillNewSpawnCounts = daysLeftTillNewSpawnCounts.copyOf()
            val nrOfNewBorns = curDaysLeftTillNewSpawnCounts.first()
            daysLeftTillNewSpawnCounts[INIT_TIMER_VAL_NEW_BORN] = nrOfNewBorns

            for (daysLeft in 1 until curDaysLeftTillNewSpawnCounts.size) {
                daysLeftTillNewSpawnCounts[daysLeft - 1] = curDaysLeftTillNewSpawnCounts[daysLeft]
            }
            daysLeftTillNewSpawnCounts[TIMER_VAL_AFTER_SPAWN] += nrOfNewBorns
        }
        return daysLeftTillNewSpawnCounts.sumOf { it }
    }

    private fun File.toCountsArray(): Array<BigInt> {
        val daysLeftTillNewSpawn = Array(INIT_TIMER_VAL_NEW_BORN + 1) { BigInt.ZERO }
        readText().split(',').map(String::trim).map(String::toInt)
            .forEach { daysLeft -> daysLeftTillNewSpawn[daysLeft]++ }
        return daysLeftTillNewSpawn
    }
}
