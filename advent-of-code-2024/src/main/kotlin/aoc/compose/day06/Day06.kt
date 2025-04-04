package aoc.compose.day06

//import org.jetbrains.compose.reload.DevelopmentEntryPoint
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.singleWindowApplication
import androidx.compose.ui.zIndex
import aoc.utils.grid2d.GridPoint2D
import aoc.utils.grid2d.firstPoint
import aoc.utils.grid2d.getOrNull
import aoc.utils.relativeToResources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import aoc.utils.grid2d.GridPoint2D as Vec2

/**
 * https://github.com/SebastianAigner/advent-of-code-2024-behind-the-scenes/tree/main
 */
private val day06Sample = """
    ....#.....
    .........#
    ..........
    ..#.......
    .......#..
    ..........
    .#..^.....
    ........#.
    #.........
    ......#...""".trimIndent().lines()


class Maze(private val input: List<String>) {
    val cells = mutableStateMapOf<Int, Cell>()
    val width = input[0].length
    val height = input.size

    init {
        for (y in input.indices) {
            for (x in 0 until width) {
                cells[x + y * width] = when (input[y][x]) {
                    '.' -> Cell.Empty
                    '^' -> Cell.Guard
                    '#' -> Cell.Obstruction
                    else -> Cell.Visited
                }
            }
        }
    }

    suspend fun solve(pause: Duration) {
        var guard = input.firstPoint { it == '^' }
        println("guard = ${guard}")
        var direction = Vec2.north
        while (true) {
            cells[guard.y * width + guard.x] = Cell.Visited
            val next = guard + direction
            input.getOrNull(next) ?: break
            guard = if (cells[next.y * width + next.x] == Cell.Obstruction) {
//                bumpFlow.emit(next)
                direction = direction.rot90R()
                guard + direction
            } else next
            cells[guard.y * width + guard.x] = Cell.Guard
            delay(pause)
        }
    }
}

//private val bumpFlow = MutableSharedFlow<Vec2>()

@Composable
fun Maze(maze: Maze, scale: Float) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .horizontalScroll(rememberScrollState())
            .verticalScroll(rememberScrollState())
            .semantics(mergeDescendants = true) {}) {
        for (y in 0 until maze.height) {
            Row(Modifier.zIndex(0f)) {
                for (x in 0 until maze.width) {
                    SingleCell(coord = GridPoint2D(x, y), maze = maze, scale = scale)
                }
            }
        }
    }
}

@Composable
private fun SingleCell(
    coord: Vec2,
    maze: Maze,
    scale: Float,
) {
    val bumpScale = remember { Animatable(1f) }
    // Slows down app
//    LaunchedEffect(Unit) {
//        bumpFlow
//            .filter { it == coord }
//            .collect { value ->
//                println(value)
//                launch {
//                    bumpScale.animateTo(1.5f, tween(durationMillis = 500))
//                    bumpScale.animateTo(1f, tween(durationMillis = 1000))
//                }
//            }
//    }
    maze.cells[coord.x + coord.y * maze.width]?.also { cell ->
        val color = when (cell) {
            Cell.Guard -> Color.Red
            Cell.Obstruction -> Color.Black
            Cell.Empty -> Color.White
            Cell.Visited -> Color.Black.copy(0.05f)
        }
        val coolC = if (bumpScale.value > 1.1f) {
            Color(0xFFffa500)
        } else {
            color
        }
        Box(
            modifier = Modifier
                .graphicsLayer {
                    this.transformOrigin = TransformOrigin.Center
                    this.scaleX = bumpScale.value
                    this.scaleY = bumpScale.value
                    this.clip = false
                }
                .padding(scale.dp / 6)
                .size(scale.dp).background(coolC).clearAndSetSemantics { }
                .then(if (bumpScale.value > 1.0f) Modifier.zIndex(1.0f) else Modifier),
        ) {
        }
    } ?: println("Cell not found at $coord")
}

enum class Cell(val presentation: String) {
    Guard("💂"),
    Obstruction("🎁"),
    Empty("⬜️"),
    Visited("🟩"),
}

@Composable
fun Day6() {
    var isSample by remember { mutableStateOf(true) }
    var scale by remember { mutableStateOf(8f) }
    var pause by remember { mutableStateOf(100.milliseconds) }

    val maze by remember(isSample) {
        mutableStateOf(
            Maze(
                if (isSample) day06Sample else Path(
                    {}.relativeToResources(
                        resourcePath = "/app-props.txt",
                        rootFileName = "advent-of-code-2024",
                        inputFileName = "input"
                    ) + "/day06.txt"
                ).readLines()
            )
        )
    }
    LaunchedEffect(isSample) {
        withContext(Dispatchers.Default) {
            maze.solve(pause)
        }
    }
    Row(modifier = Modifier.fillMaxSize()) {
        Maze(maze, scale)
        Column {
            IconToggleButton(isSample, onCheckedChange = { isSample = it }) {
                Text(if (isSample) "🔮" else "🌟")
            }
            Text("Size")
            Slider(scale, onValueChange = { scale = it }, valueRange = 5f..20f, steps = 16)
            Text("Delay")
            Slider(pause.inWholeMilliseconds.toFloat(), onValueChange = { pause = it.toInt().milliseconds }, valueRange = 1f..1000f, steps = 10)
            Text("Visited: ${maze.cells.count { it.value == Cell.Visited }}")
        }
    }
}

fun main() {
    singleWindowApplication(
        title = "Day 6",
        alwaysOnTop = true
    ) {
//        DevelopmentEntryPoint {
        Day6()
//        }
    }
}