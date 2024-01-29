package aoc.seastories

import aoc.seastories.Day16PacketDecoder.Companion.hexToBinaryPacket
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day16PacketDecoderTest {

    private val day16PacketDecoderTestInput = Day16PacketDecoder("input/day16test.txt")
    private val day16PacketDecoder = Day16PacketDecoder("input/day16.txt")

    @Test
    fun `part 1 test input`() = assertEquals(16, day16PacketDecoderTestInput.part1())

    @Test
    fun `part 1 result`() = assertEquals(866, day16PacketDecoder.part1().also(::println))

    @Test
    fun `part 2 test input`() = assertEquals(15, day16PacketDecoderTestInput.part2())

    @Test
    fun `part 2 result`() = assertEquals(1_392_637_195_518, day16PacketDecoder.part2().also(::println))

    @Test
    fun `part 2 result other input`() = assertEquals(10_185_143_721_112, Day16PacketDecoder("input/day16-2.txt").part2())

    @Test
    fun `test from hexadecimal to binary`() =
        assertEquals("110100101111111000101000", "D2FE28".hexToBinaryPacket().binary)
}
