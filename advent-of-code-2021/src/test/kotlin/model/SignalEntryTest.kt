package model

import aoc.Day08SevenSegmentSearch.toSignalEntry
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class SignalEntryTest {

    private companion object {
        const val enc5353 = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"
        const val enc8394 = "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe"
        const val enc9781 = "edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc"
        const val enc1197 = "fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg"
    }

    @ParameterizedTest
    @ValueSource(strings = [enc5353, enc8394, enc9781, enc1197])
    fun `matching the patterns yields all unique Nrs`(line: String) {
        val signalEntry = toSignalEntry(line)
        val patterns = signalEntry.patternNrsRepresentedByIndex()

        val decodedNrs = patterns.indices.toSet()
        assertEquals(10, decodedNrs.size)
        assertEquals(setOf(0, 1, 2, 3, 4, 4, 5, 6, 7, 8, 9), decodedNrs)
    }

    @ParameterizedTest
    @ValueSource(strings = ["$enc5353 -> 5353", "$enc8394 -> 8394", "$enc9781 -> 9781", "$enc1197 -> 1197"])
    fun `test decoding nr yields expected result`(lineToExpectedNr: String) {
        val (line, expected) = lineToExpectedNr.split(" -> ")
        val signalEntry = toSignalEntry(line)
        val output = signalEntry.decodeNumber()
        assertEquals(expected.toInt(), output)
    }
}
