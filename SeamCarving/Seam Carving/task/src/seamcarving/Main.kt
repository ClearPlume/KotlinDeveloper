package seamcarving

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.util.*
import javax.imageio.ImageIO
import kotlin.math.sqrt

const val IN = "in"
const val OUT = "out"

val validCommands = arrayOf(IN, OUT)

val <T> Array<Array<T>>.row: Int
    get() = size

val <T> Array<Array<T>>.col: Int
    get() = this[0].size

fun main(args: Array<String>) {
    val commands = args.parse()

    val img = ImageIO.read(File(commands[IN]!!))
    // img [After] operation
    val imgA = BufferedImage(img.width, img.height, BufferedImage.TYPE_INT_RGB)

    seam(img, imgA)

    ImageIO.write(imgA, "png", File(commands[OUT]!!))
}

fun Array<String>.parse(): Map<String, String> {
    val commands = mutableMapOf<String, String>()

    for (i in indices) {
        if (this[i].startsWith('-')) {
            if (this[i].substring(1) in validCommands) {
                if (i < lastIndex) {
                    if (this[i + 1].matches(Regex("[\\w\\-/]+\\.[a-zA-Z]{1,5}"))) {
                        commands[this[i].substring(1)] = this[i + 1]
                    }
                }
            }
        }
    }

    return commands
}

inline fun BufferedImage.exec(operation: (Int, Int, Color) -> Unit) {
    for (y in 0 until height) {
        for (x in 0 until width) {
            operation(x, y, Color(getRGB(x, y)))
        }
    }
}

fun BufferedImage.energies(): Array<Array<Double>> {
    val energies = Array(height) { Array(width) { 0.0 } }

    exec { x, y, _ ->
        val xL: Int
        val xR: Int
        val yU: Int
        val yD: Int

        when (x) {
            0 -> {
                xL = 0
                xR = 2
            }
            width - 1 -> {
                xL = x - 2
                xR = x
            }
            else -> {
                xL = x - 1
                xR = x + 1
            }
        }

        when (y) {
            0 -> {
                yU = 0
                yD = 2
            }
            height - 1 -> {
                yU = y - 2
                yD = y
            }
            else -> {
                yU = y - 1
                yD = y + 1
            }
        }

        val lColor = this[xL, y]
        val rColor = this[xR, y]
        val uColor = this[x, yU]
        val dColor = this[x, yD]

        val xGradient = lColor..rColor
        val yGradient = uColor..dColor
        val energy = sqrt(xGradient + yGradient)

        energies[y][x] = energy
    }

    return energies
}

operator fun BufferedImage.get(x: Int, y: Int): Color {
    return Color(getRGB(x, y))
}

operator fun BufferedImage.set(x: Int, y: Int, color: Color) {
    return setRGB(x, y, color.rgb)
}

fun Color.negative() = Color(255 - red, 255 - green, 255 - blue)

/**
 * calc gradient of 2 colors
 */
operator fun Color.rangeTo(other: Color): Double {
    val r = red - other.red
    val g = green - other.green
    val b = blue - other.blue

    return (r * r + g * g + b * b).toDouble()
}

/**
 * Functions for previous stages
 */
fun negative(img: BufferedImage, imgA: BufferedImage) {
    img.exec { x, y, color -> imgA[x, y] = color.negative() }
}

fun energies(img: BufferedImage, imgA: BufferedImage) {
    val energies = img.energies()

    val maxEnergy = energies.map { row -> row.maxOf { it } }.maxOf { it }

    img.exec { x, y, _ ->
        val intensity = (energies[y][x] * 255 / maxEnergy).toInt()
        imgA[x, y] = Color(intensity, intensity, intensity)
    }
}

fun seam(img: BufferedImage, imgA: BufferedImage) {
    val energies = img.energies()
    img.exec { x, y, color -> imgA[x, y] = color }
    val shortestPath = mutableListOf<Graph.Node>()

    for (pixelIndex in energies[0].indices) {
        val graph = Graph(energies)
        val pixel = graph.data[0][pixelIndex]
        pixel.distance = 0.0
        graph.dijkstra(pixel.x, pixel.y)
        graph.data.last().sortWith(compareBy({ it.distance }, { it.weight }))
        shortestPath.add(graph.data.last().first())
    }

    shortestPath.sortWith(compareBy({ it.distance }, { it.weight }))
    var e = shortestPath.first()

    do {
        imgA[e.x, e.y] = Color.RED
        e = e.prev
    } while (!e.weight.isNaN())
}

class Graph(energies: Array<Array<Double>>) {
    val none = Node(-1, -1, Double.NaN)
    val data = Array(energies.row) { row -> Array(energies.col) { col -> Node(col, row, energies[row][col]) } }

    inner class Node(val x: Int, val y: Int, var weight: Double, var explored: Boolean = false) {
        var prev = none
        var distance = Double.POSITIVE_INFINITY

        fun getNeighbor(): MutableList<Node> {
            val neighbors = mutableListOf<Node>()

            if (y + 1 < data.row && x - 1 >= 0 && !data[y + 1][x - 1].explored) {
                neighbors.add(data[y + 1][x - 1])
            }

            if (y + 1 < data.row && !data[y + 1][x].explored) {
                neighbors.add(data[y + 1][x])
            }

            if (y + 1 < data.row && x + 1 < data.col && !data[y + 1][x + 1].explored) {
                neighbors.add(data[y + 1][x + 1])
            }

            return neighbors
        }

        override fun toString(): String {
            return "x: $x, y: $y, weight: $weight, dis: $distance"
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Node

            if (x != other.x) return false
            if (y != other.y) return false

            return true
        }

        override fun hashCode(): Int {
            var result = x
            result = 31 * result + y
            return result
        }
    }

    fun dijkstra(x: Int, y: Int) {
        val u = data[y][x]

        val neighbor = u.getNeighbor().apply {
            for (node in this) {
                val distanceFromU = node.weight + u.distance

                if (node.distance > distanceFromU) {
                    node.distance = distanceFromU
                    node.prev = u
                }
            }
        }
        val waitQueue = PriorityQueue<Node> { n1, n2 -> n1.weight.compareTo(n2.weight) }.apply { addAll(neighbor) }

        while (waitQueue.isNotEmpty()) {
            val v = waitQueue.poll()
            u.explored = true
            dijkstra(v.x, v.y)
        }
    }
}