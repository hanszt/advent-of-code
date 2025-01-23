package aoc.utils

import aoc.utils.Colors.RESET
import kotlin.math.abs
import kotlin.math.min
import kotlin.random.Random

/**
 * A utility for coloring command line interface text
 *
 * See also [ANSI escape code](https://en.wikipedia.org/wiki/ANSI_escape_code)
 */
private const val SIXTEEN_BIT = 16

object Colors {

    val RESET = TextColor("\u001B[0m")

    internal val colorTable16Bit by lazy { to16bitAnsiColorTabel(::TextColor) }
    internal val bgColorTable16Bit by lazy { to16bitAnsiColorTabel(::BgColor) }
}

sealed interface Color {
    val ansiCode: String
}

@JvmInline
value class BgColor internal constructor(override val ansiCode: String) : Color {
    internal constructor(nr: Int) : this("\u001b[48;5;${nr}m")

    init {
        require(ansiCode.startsWith("\u001B[4") && ansiCode.endsWith('m')) { "Not a valid ansi background color code" }
    }

    companion object {
        @JvmStatic
        fun hsb(hue: Int, saturation: Double = 1.0, brightness: Double = 0.5) = BgColor(hsbToAnsiNr(hue, saturation, brightness))

        val YELLOW = BgColor("\u001B[43m")
        val BROWN = BgColor("\u001B[48;5;88m")
        val ICY = BgColor("\u001B[48;5;87m")
    }
}

@JvmInline
value class TextColor internal constructor(override val ansiCode: String) : Color {
    internal constructor(nr: Int) : this("\u001b[38;5;${nr}m")

    init {
        require(ansiCode.startsWith("\u001B[") && ansiCode.endsWith('m')) { "Not a valid ansi text color code" }
    }

    companion object {
        @JvmStatic
        fun hsb(hue: Int, saturation: Double = 1.0, brightness: Double = 0.5): TextColor = TextColor(hsbToAnsiNr(hue, saturation, brightness))
        fun rgb(r: Int, g: Int, b: Int): TextColor = TextColor(rgbToAnsiNr(r, g, b))
        fun random(random: Random) = TextColor(random.nextInt(SIXTEEN_BIT * SIXTEEN_BIT))

        val RED = TextColor("\u001B[31m")
        val GREEN = TextColor("\u001B[32m")
        val BRIGHT_GREEN = TextColor("\u001b[32;1m")
        val YELLOW = TextColor("\u001B[33m")
        val CYAN = TextColor("\u001B[36m")
        val BRIGHT_BLUE = TextColor("\u001B[94m")
        val BRIGHT_YELLOW = TextColor("\u001B[93m")
    }
}

fun Map<Int, Color>.printTable() = forEach { (nr, color) ->
    print("%4d".format(nr).withColor(color))
    if ((nr + 1) % SIXTEEN_BIT == 0) println()
}

infix fun String.withColor(color: Color) = "${color.ansiCode}$this${RESET.ansiCode}"
infix fun Char.withColor(color: Color) = "${color.ansiCode}$this${RESET.ansiCode}"
fun String.withColors(textColor: TextColor, backgroundColor: BgColor) =
    "${backgroundColor.ansiCode}${textColor.ansiCode}$this${RESET.ansiCode}"

internal fun hsbToAnsiNr(hue: Int, saturation: Double, brightness: Double): Int {
    require(saturation in 0.0..1.0) { "Saturation $saturation must be in 0.0..1.0" }
    require(brightness in 0.0..1.0) { "Brightness $brightness must be in 0.0..1.0" }
    val table = when {
        saturation <= 0.01 -> grayScaleTable
        saturation <= 0.5 -> pastelColorTable
        else -> rainbowTable
    }
    val width = table.first().size
    val height = table.size
    val modHue = hue % 360
    val x = (brightness * width).toInt()
    val y = ((modHue / 360.0) * height).toInt()
    return table[min(y, height - 1)][min(x, width - 1)].toInt()
}

/**
 * [256 ANSI rainbow colors](https://www.hackitu.de/termcolor256/)
 */
internal val rainbowTable = """
    016 052 088 124 160 196 203 210 217 224 231
    016 052 088 124 160 202 209 216 223 230 231
    016 052 088 124 166 208 215 222 229 230 231
    016 052 088 130 172 214 221 228 229 230 231
    016 052 094 136 178 220 227 228 229 230 231
    016 058 100 142 184 226 227 228 229 230 231
    016 022 064 106 148 190 227 228 229 230 231
    016 022 028 070 112 154 191 228 229 230 231
    016 022 028 034 076 118 155 192 229 230 231
    016 022 028 034 040 082 119 156 193 230 231
    016 022 028 034 040 046 083 120 157 194 231
    016 022 028 034 040 047 084 121 158 195 231
    016 022 028 034 041 048 085 122 159 195 231
    016 022 028 035 042 049 086 123 159 195 231
    016 022 029 036 043 050 087 123 159 195 231
    016 023 030 037 044 051 087 123 159 195 231
    016 017 024 031 038 045 087 123 159 195 231
    016 017 018 025 032 039 081 123 159 195 231
    016 017 018 019 026 033 075 117 159 195 231
    016 017 018 019 020 027 069 111 153 195 231
    016 017 018 019 020 021 063 105 147 189 231
    016 017 018 019 020 057 099 141 183 225 231
    016 017 018 019 056 093 135 177 219 225 231
    016 017 018 055 092 129 171 213 219 225 231
    016 017 054 091 128 165 207 213 219 225 231
    016 053 090 127 164 201 207 213 219 225 231
    016 052 089 126 163 200 207 213 219 225 231
    016 052 088 125 162 199 206 213 219 225 231
    016 052 088 124 161 198 205 212 219 225 231
    016 052 088 124 160 197 204 211 218 225 231
""".trimIndent().lines().map { it.split(' ') }

/**
 * [256 ANSI pastel colors](https://www.hackitu.de/termcolor256/)
 */
internal val pastelColorTable = """
    059 095 131 167 174 181 188
    059 095 131 173 180 187 188
    059 095 137 179 186 187 188
    059 101 143 185 186 187 188
    059 065 107 149 186 187 188
    059 065 071 113 150 187 188
    059 065 071 077 114 151 188
    059 065 071 078 115 152 188
    059 065 072 079 116 152 188
    059 066 073 080 116 152 188
    059 060 067 074 116 152 188
    059 060 061 068 110 152 188
    059 060 061 062 104 146 188
    059 060 061 098 140 182 188
    059 060 097 134 176 182 188
    059 096 133 170 176 182 188
    059 095 132 169 176 182 188
    059 095 131 168 175 182 188
""".trimIndent().lines().map { it.split(' ') }

private val grayScaleTable = listOf((232..255).map { it.toString() })

/**
 * [Rgb to hsl](https://www.rapidtables.com/convert/color/rgb-to-hsl.html)
 */
internal fun rgbToAnsiNr(r: Int, g: Int, b: Int): Int {
    require(r in 0..255) { "r $r must be between 0 and 255" }
    require(g in 0..255) { "g $g must be between 0 and 255" }
    require(b in 0..255) { "b $b must be between 0 and 255" }
    val ri = r / 255.0
    val gi = g / 255.0
    val bi = b / 255.0
    val cMin = ri.coerceAtMost(gi).coerceAtMost(bi)
    val cMax = ri.coerceAtLeast(gi).coerceAtLeast(bi)
    val d = cMax - cMin
    val l = (cMin + cMax) / 2
    val sat = if (d != 0.0) d / (1 - abs(2 * l - 1)) else 0.0
    return hsbToAnsiNr(
        hue = when {
            d == 0.0 -> 0
            cMax == ri -> (60 * (((gi - bi) / d) % 6)).toInt()
            cMax == gi -> (60 * (((bi - ri) / d) + 2)).toInt()
            cMax == bi -> (60 * (((ri - gi) / d) + 4)).toInt()
            else -> error("Unexpected state")
        },
        saturation = min(sat, 1.0),
        brightness = l
    )
}

/**
 * [ANSI escape code](https://en.wikipedia.org/wiki/ANSI_escape_code)
 */
private fun <C : Color> to16bitAnsiColorTabel(toColor: (Int) -> C): Map<Int, C> =
    (0..<SIXTEEN_BIT).flatMap { row ->
        (0..<SIXTEEN_BIT).map { col -> row * SIXTEEN_BIT + col }
    }.associateWith(toColor)
