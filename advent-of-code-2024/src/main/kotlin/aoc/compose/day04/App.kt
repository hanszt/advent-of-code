package aoc.compose.day04

//import org.jetbrains.compose.reload.DevelopmentEntryPoint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.singleWindowApplication
import aoc.utils.relativeToResources
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import aoc.utils.grid2d.GridPoint2D as Vec2

/**
 * https://youtu.be/0ekhKsd_YYA?list=PLlFc5cFwUnmwHaD3-qeoLHnho_PY2g9JX&t=3817
 * https://github.com/SebastianAigner/advent-of-code-2024-behind-the-scenes/tree/main
 */
fun main() {
    singleWindowApplication(
        title = "Day 4",
        alwaysOnTop = true
    ) {
//        DevelopmentEntryPoint {
        App()
//        }
    }
}

const val CROP = 40
val t = {}.relativeToResources(
    resourcePath = "/app-props.txt",
    rootFileName = "advent-of-code-2024",
    inputFileName = "input"
)

private val grid = Grid(
    input.take(CROP).map { it.take(CROP) }
)

@Composable
fun App() {
    val colorMap = remember { mutableStateMapOf<Vec2, ColorEvent>() }
    val lastTouchedMap = remember { mutableStateMapOf<Vec2, Long>() }
    LaunchedEffect(Unit) {
        colorFlow.collect {
            println(it)
            lastTouchedMap[it.loc] = System.currentTimeMillis()
            val curr = colorMap[it.loc]
            if (curr != null) {
                if (curr.priority > it.priority) {
                    println("prios ${curr.priority} ${it.priority}")
                    colorMap[it.loc] = it
                }
            } else {
                colorMap[it.loc] = it
            }
        }
    }
    Box(
        Modifier.background(Color.LightGray).verticalScroll(rememberScrollState())
            .semantics(mergeDescendants = true) {}) {
        Box(Modifier.horizontalScroll(rememberScrollState())) {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                for (y in grid.elems.indices) {
                    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                        for (x in grid.elems[0].indices) {
                            GridCell(
                                letter = grid.elems[y][x],
                                color = colorMap[Vec2(x, y)]?.color ?: Color.White,
                                lastTouched = lastTouchedMap[Vec2(x, y)] ?: 0L,
                            )
                        }
                    }
                }
            }
        }
        ControlPanel(coroutineScope = rememberCoroutineScope())
    }
}

@Composable
private fun BoxScope.ControlPanel(coroutineScope: CoroutineScope) {
    Column(
        modifier = Modifier.Companion.align(Alignment.BottomEnd).padding(10.dp),
        horizontalAlignment = Alignment.End,
    ) {
        Button(
            onClick = {
                coroutineScope.launch {
                    grid.points.forEach {
                        grid.countXmasWordsForPosition(it)
                    }
                }
            }
        ) {
            Text("Find words")
        }
        Button(
            onClick = {
                coroutineScope.launch {
                    grid.points.drop(CROP * CROP / 2).forEach {
                        grid.countXmasWordsForPosition(it)
                    }
                }
            }
        ) {
            Text("Find words (2)")
        }
        Button(
            onClick = {
                coroutineScope.launch {
                    grid.points.forEach {
                        grid.isMASCrossAtPosition(it)
                    }
                }
            }
        ) {
            Text("Find MAS-crosses")
        }
        Button(
            onClick = {
                coroutineScope.launch {
                    grid.points.drop(CROP * CROP / 2).forEach {
                        grid.isMASCrossAtPosition(it)
                    }
                }
            }
        ) {
            Text("Find MAS-crosses (2)")
        }
    }
}


@Composable
fun GridCell(letter: Char, color: Color, lastTouched: Long = 0L) {
    val scale = remember { Animatable(1f) }
    LaunchedEffect(lastTouched) {
        if (lastTouched == 0L) return@LaunchedEffect
        scale.animateTo(1.5f, tween(durationMillis = 100))
        scale.animateTo(1f, tween(durationMillis = 30))
    }

    val animatedColor by animateColorAsState(targetValue = color, animationSpec = tween(durationMillis = 500))
    val modifier = if (scale.value > 1.25f) {
        Modifier.background(Color.Red)
    } else {
        Modifier
    }

    Box(
        Modifier.size(20.dp)
            .background(animatedColor)
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Text(
            letter.toString(),
            Modifier.clearAndSetSemantics { }.scale(scale.value),
            fontFamily = FontFamily.Monospace
        )
    }
}