package aoc

import java.io.File
import java.math.BigInteger as BigInt

internal object Day06LanternFish : ChallengeDay {

    private const val INIT_TIMER_VAL_NEW_BORN = 8
    private const val TIMER_VAL_AFTER_SPAWN = 6

    fun part1(path: String): BigInt = File(path).toFishCount(days = 80)

    fun part2(path: String): BigInt = File(path).toFishCount(days = 256)

    private fun File.toFishCount(days: Int): BigInt {
        val daysLeftTillNewSpawnCounts = toCountsArray()
        for (day in 1..days) {
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

    override fun part1() = part1(ChallengeDay.inputDir + "/day6.txt")
    override fun part2() = part2(ChallengeDay.inputDir + "/day6.txt")
}
