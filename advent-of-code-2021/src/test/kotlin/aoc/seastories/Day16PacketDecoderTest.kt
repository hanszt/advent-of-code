package aoc.seastories

import aoc.seastories.Day16PacketDecoder.hexToBinaryPacket
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day16PacketDecoderTest {

    @Test
    fun `part 1 test input`() = assertEquals(16, Day16PacketDecoder.part1("input/day16test.txt"))

    @Test
    fun `part 1 result`() = assertEquals(866, Day16PacketDecoder.part1().also(::println))

    @Test
    fun `part 2 test input`() = assertEquals(15, Day16PacketDecoder.part2("input/day16test.txt"))

    @Test
    fun `part 2 result`() = assertEquals(1_392_637_195_518, Day16PacketDecoder.part2().also(::println))

    @Test
    fun `part 2 result other input`() = assertEquals(10_185_143_721_112, Day16PacketDecoder.part2("input/day16-2.txt"))

    @Test
    fun `test from hexadecimal to binary`() =
        assertEquals("110100101111111000101000", "D2FE28".hexToBinaryPacket().binary)
}
