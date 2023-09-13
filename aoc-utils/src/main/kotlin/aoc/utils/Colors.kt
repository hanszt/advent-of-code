package aoc.utils

/**
 * A utility for coloring command line interface text
 *
 * See also [ANSI escape code](https://en.wikipedia.org/wiki/ANSI_escape_code)
 */
private const val SIXTEEN_BIT = 16
val RESET = TextColor("\u001B[0m")

val RED = TextColor("\u001B[31m")
val GREEN = TextColor("\u001B[32m")
val BRIGHT_GREEN = TextColor("\u001b[32;1m")
val YELLOW = TextColor("\u001B[33m")
val CYAN = TextColor("\u001B[36m")
val BRIGHT_BLUE = TextColor("\u001B[94m")
val BRIGHT_YELLOW = TextColor("\u001B[93m")

val YELLOW_BG = BgColor("\u001B[43m")
val BROWN_BG = BgColor("\u001B[48;5;88m")
val ICY_BG = BgColor("\u001B[48;5;87m")

val colorTable16Bit by lazy { to16bitAnsiColorTabel(::TextColor) }
val bgColorTable16Bit by lazy { to16bitAnsiColorTabel(::BgColor) }

sealed interface Color {
    val ansiCode: String
}

@JvmInline
value class BgColor(override val ansiCode: String) : Color {
    constructor(nr: Int) : this("\u001b[48;5;${nr}m")
    init {
        require(ansiCode.startsWith("\u001B[4") && ansiCode.endsWith('m')) { "Not a valid ansi background color code" }
    }
}

@JvmInline
value class TextColor(override val ansiCode: String) : Color {
    constructor(nr: Int) : this("\u001b[38;5;${nr}m")
    init {
        require(ansiCode.startsWith("\u001B[") && ansiCode.endsWith('m')) { "Not a valid ansi text color code" }
    }
}

fun Map<Int, Color>.printTable() = forEach { (nr, color) ->
    print("%4d".format(nr).withColor(color))
    if ((nr + 1) % SIXTEEN_BIT == 0) println()
}

fun colorWithAnsiTableNr(nr: Int): TextColor = colorTable16Bit[nr] ?: error("max code is 255 (was: $nr)")
fun bgColorWithAnsiTableNr(nr: Int): BgColor = bgColorTable16Bit[nr] ?: error("max code is 255 (was: $nr)")

infix fun <T> T.withColor(color: Color) = "${color.ansiCode}$this${RESET.ansiCode}"
fun <T> T.withColors(textColor: TextColor, backgroundColor: BgColor) =
    "${backgroundColor.ansiCode}${textColor.ansiCode}$this${RESET.ansiCode}"

fun random16BitColor() = colorTable16Bit.values.random()

/**
 * [ANSI escape code](https://en.wikipedia.org/wiki/ANSI_escape_code)
 */
private fun <C : Color> to16bitAnsiColorTabel(toColor: (Int) -> C): Map<Int, C> =
    (0..<SIXTEEN_BIT).flatMap { row ->
        (0..<SIXTEEN_BIT).map { col -> row * SIXTEEN_BIT + col }
    }.associateWith(toColor)
