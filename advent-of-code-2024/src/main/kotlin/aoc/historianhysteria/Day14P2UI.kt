package aoc.historianhysteria

import aoc.utils.get
import aoc.utils.model.GridPoint2D
import aoc.utils.model.dimension2D
import aoc.utils.model.mod
import aoc.utils.relativeToResources
import aoc.utils.set
import java.awt.*
import javax.swing.*
import kotlin.io.path.Path

/**
 * A small ui to visually verify the solution
 */
fun main() {
    val base = {}.relativeToResources(
        resourcePath = "/app-props.txt",
        rootFileName = "advent-of-code-2024",
        inputFileName = "input"
    )

    val robots = Day14(Path("$base/day14.txt")).robots
    val dimension = dimension2D(101, 103)
    val m = dimension.width
    val n = dimension.height

    val nrOfRobots = Array(n) { IntArray(m) }
    var positions: List<GridPoint2D> = emptyList()

    val dim = 7
    val label = JLabel("Start")
    val canvas = object : JPanel() {
        init {
            preferredSize = Dimension(dim * m, dim * n)
        }

        override fun paintComponent(g: Graphics) {
            g.color = Color.WHITE
            g.fillRect(0, 0, dim * m, dim * n)
            g.color = Color.BLACK
            dimension.forEach {
                if (nrOfRobots[it] > 0) {
                    val d = it * dim
                    g.fillRect(d.x, d.y, dim, dim)
                }
            }
        }
    }
    var k = 0
    fun update() {
        for (point in positions) {
            nrOfRobots[point] = 0
        }
        positions = robots.map { (_, pos, vel) ->
            (pos + vel * k).mod(m, n)
        }
        for ((x, y) in positions) {
            nrOfRobots[y][x]++
        }
        label.text = "Step $k"
        canvas.repaint()
    }

    val nextButton = JButton("+1").apply {
        addActionListener {
            k++
            update()
        }
    }
    val next101Button = JButton("+101").apply {
        addActionListener {
            k += 101
            update()
        }
    }
    val prevButton = JButton("-1").apply {
        addActionListener {
            k--
            update()
        }
    }
    val searchRangeField = JTextField("0..10000")
    val nrField = JTextField("0").apply {
        addActionListener {
            k = text.toInt()
            update()
        }
    }
    val christmas = JButton("Christmas!").apply {
        addActionListener {
            val (start, endInclusive) = searchRangeField.text.split("..")
            k = elizarovDay14Part2(robots = robots, start.toInt()..endInclusive.toInt())
            update()
        }
    }
    JFrame("Day14_2").apply {
        contentPane = JPanel(BorderLayout())
        contentPane.add(canvas, BorderLayout.CENTER)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        contentPane.add(canvas, BorderLayout.CENTER)
        val bottomPanel = JPanel(FlowLayout(FlowLayout.CENTER)).apply {
            add(label)
            add(prevButton)
            add(nextButton)
            add(next101Button)
            add(nrField)
            add(searchRangeField)
            add(christmas)
        }
        contentPane.add(bottomPanel, BorderLayout.SOUTH)
        pack()
        update()
        isVisible = true
    }
}